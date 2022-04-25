package com.springboot.cli.controller;

import com.springboot.cli.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 登录web控制器
 *
 * @author ding
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final IUserService iUserService;

    @RequestMapping({"/", "/index"})
    public String index(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (Objects.isNull(user)) {
            log.error("用户未登录");
            return "login";
        }
        return "index";
    }

    @RequestMapping("/userInfo")
    public String table1(Model model) {
        model.addAttribute("users", iUserService.listUser());
        return "userInfo";
    }

}
