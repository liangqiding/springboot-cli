package com.springboot.cli.controller;

import com.springboot.cli.security.AuthUser;
import com.springboot.cli.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ding
 */
@Controller
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        AuthUser user = SecurityUtils.getUser();
        log.info("当前用户为：{}", user);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin() {
        return "index";
    }
}
