package com.springboot.cli.controller;


import com.springboot.cli.utils.CaptchaUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ding
 */
@RestController
public class IndexController {


    /**
     * 获取验证码
     */
    @GetMapping("/getCaptcha")
    public void getCaptcha(String uuid, HttpServletResponse response) throws IOException {
        CaptchaUtils.getCircleCaptcha(uuid, response);
    }

    /**
     * 模拟登录校验
     */
    @GetMapping("/login")
    public boolean login(String uuid, String code, String username, String password) {
        return CaptchaUtils.verify(uuid, code);
    }
}
