package com.springboot.cli.controller;

import com.springboot.cli.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final IUserService iUserService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        if (!iUserService.login(username, password, session)) {
            model.addAttribute("errorMsg", "账号密码错误");
            return "index";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "index";
    }
}
