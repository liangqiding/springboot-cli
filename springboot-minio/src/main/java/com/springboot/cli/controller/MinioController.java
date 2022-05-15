package com.springboot.cli.controller;


import com.springboot.cli.minio.MinioHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ding
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("minio")
public class MinioController {

    private final MinioHandler minioHandler;

    /**
     * 创建存储桶
     */
    @GetMapping("/createdBucket")
    public boolean createdBucket(String bucketName) throws Exception {
        return minioHandler.createdBucket(bucketName);
    }

    /**
     * 创建存储桶
     */
    @GetMapping("/removeBucket")
    public boolean removeBucket(String bucketName) {
        return minioHandler.removeBucket(bucketName);
    }

    /**
     * 上传文件
     */
    @PostMapping("upload")
    public List<String> saveFile(MultipartFile[] files) throws Exception {
        return minioHandler.saveFile(files);
    }

    /**
     * 下载文件
     */
    @GetMapping(value = "download")
    public ResponseEntity<byte[]> download(String filename) {
        return minioHandler.download(filename);
    }

    /**
     * 以图片形式返回
     */
    @GetMapping("get/image")
    public ResponseEntity<byte[]> getImage(String filename) {
        return minioHandler.download(filename, MediaType.IMAGE_JPEG);
    }
}
