package com.springboot.cli.minio;

import cn.hutool.core.io.IoUtil;
import com.springboot.cli.config.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.in;

/**
 * minio
 *
 * @author ding
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MinioHandler {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    /**
     * 判断bucket是否存在
     *
     * @param name 存储桶名称
     */
    public boolean existBucket(String name) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建存储bucket
     *
     * @param bucketName 存储bucket名称
     */
    public boolean createdBucket(String bucketName) throws Exception {
        if (!this.existBucket(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            return true;
        }
        return false;
    }

    /**
     * 删除存储bucket
     *
     * @param bucketName 存储bucket名称
     */
    public boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * description: 上传文件
     *
     * @param file 文件
     * @return: List<String> s
     */
    public String saveFile(MultipartFile file) throws Exception {
        if (Objects.isNull(file)) {
            return "";
        }
        String fileName;
        try {
            fileName = file.getOriginalFilename();
            InputStream in = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(fileName)
                    .stream(in, in.available(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
        } finally {
            Optional.ofNullable(in).ifPresent(inputStream -> {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            });
        }
        log.info("文件保存成功,文件名：{},类型：{}", fileName, file.getContentType());
        return fileName;
    }

    /**
     * description: 上传文件
     *
     * @param files 文件
     * @return: List<String> s
     */
    public List<String> saveFile(MultipartFile[] files) throws Exception {
        List<String> names = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            names.add(this.saveFile(file));
        }
        return names;
    }

    /**
     * 获取文件
     */
    public InputStream getFile(String fileName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucketName()).object(fileName).build());
    }

    /**
     * description: 下载文件
     *
     * @param fileName 文件名
     * @return: org.springframework.http.ResponseEntity<byte [ ]>
     */
    public ResponseEntity<byte[]> download(String fileName) {
        return this.download(fileName, MediaType.APPLICATION_OCTET_STREAM);
    }

    /**
     * description: 下载文件
     *
     * @param fileName 文件名
     * @return: org.springframework.http.ResponseEntity<byte [ ]>
     */
    public ResponseEntity<byte[]> download(String fileName, MediaType mediaType) {
        ResponseEntity<byte[]> responseEntity = null;
        InputStream in = null;
        try {
            in = this.getFile(fileName);
            // 封装返回值
            byte[] bytes = IoUtil.readBytes(in);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            headers.setContentLength(bytes.length);
            headers.setContentType(mediaType);
            headers.setAccessControlExposeHeaders(List.of("*"));
            responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Optional.ofNullable(in).ifPresent(
                    i -> {
                        try {
                            i.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }

        return responseEntity;
    }

    /**
     * 查看文件对象
     *
     * @param bucketName 存储bucket名称
     */
    public void listObjects(String bucketName) {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build());
        try {
            for (Result<Item> result : results) {
                Item item = result.get();
                String s = item.objectName();
                long size = item.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除文件对象
     *
     * @param bucketName 存储bucket名称
     * @param objects    对象名称集合
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> objects) {
        List<DeleteObject> dos = objects.stream().map(DeleteObject::new).collect(Collectors.toList());
        return minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
    }


}
