package m.links.file.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * @author lqd
 */
@Slf4j
public class FileUtils {

    /**
     * 保存文件
     *
     * @param files 文件数组
     * @param path  保存路径
     */
    public static void saveFile(MultipartFile[] files, String path) {
        for (MultipartFile file : files
        ) {
            saveFile(file, path);
        }
    }

    /**
     * 保存文件
     *
     * @param file 文件
     * @param path 保存路径
     */
    public static void saveFile(MultipartFile file, String path) {
        try {
            // 文件夹不存在则创建
            if (!FileUtil.isDirectory(path)) {
                FileUtil.mkdir(path);
            }
            file.transferTo(new File(path + "/" + file.getOriginalFilename()));
            log.info("文件上传成功--{}", file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("文件上传失败--{}", e.getLocalizedMessage());
        }
    }

    /**
     * 获取文件下载
     */
    public static void getInputStream(final HttpServletResponse response, String path) throws IOException {
        File file = new File(path);
        if (!FileUtil.isFile(file)) {
            throw new FileNotFoundException();
        }
        String fileName = file.getName();
        // 清空缓冲区，状态码和响应头(headers)
        response.reset();
        // 设置ContentType，响应内容为二进制数据流，编码为utf-8，此处设定的编码是文件内容的编码
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 实现文件下载
        BufferedInputStream bis = FileUtil.getInputStream(path);
        try {
            // 往响应体中写入数据
            OutputStream os = response.getOutputStream();
            IoUtil.copy(bis, os, IoUtil.DEFAULT_BUFFER_SIZE);
            log.info("{} 文件下载成功", fileName);
        } catch (Exception e) {
            log.error("{} 文件下载失败", fileName);
        }
    }
}
