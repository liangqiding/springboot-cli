package com.springboot.cli.controller;

import com.springboot.cli.security.AuthUser;
import com.springboot.cli.utils.CaptchaUtils;
import com.springboot.cli.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author ding
 */
@Controller
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/getCode")
    @ResponseBody
    public void getCode(HttpSession session, HttpServletResponse response) throws IOException {
        CaptchaUtils.getCircleCaptcha(session, response);
    }

    @GetMapping("/toIndex")
    public String index(Model model) {
        AuthUser user = SecurityUtils.getUser();
        log.info("当前用户为：{}", user);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/toLogin")
    public String login() {
        return "login";
    }

}
