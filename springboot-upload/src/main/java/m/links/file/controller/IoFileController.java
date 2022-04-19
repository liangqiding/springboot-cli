package m.links.file.controller;

import lombok.extern.slf4j.Slf4j;
import m.links.file.utils.FileUtils;
import m.links.file.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author lqd
 */
@RestController
@RequestMapping("file")
@CrossOrigin(origins = "*")
@Slf4j
public class IoFileController {

    @Value("${file.path}")
    private String path;

    @PostMapping("upload")
    public ResponseResult<Object> uploadFile(@RequestParam("files") MultipartFile[] files) {
        FileUtils.saveFile(files, path);
        return new ResponseResult<>(20000, "文件上传成功！");
    }

    @GetMapping(value = "/download", consumes = MediaType.ALL_VALUE)
    public void downloadFile(HttpServletResponse response, String fileName) throws IOException {
        FileUtils.getInputStream(response, path + "/" + fileName);
    }

}
