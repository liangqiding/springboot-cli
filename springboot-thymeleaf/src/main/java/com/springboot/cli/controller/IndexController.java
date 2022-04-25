package com.springboot.cli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ding
 */
@Controller
public class IndexController {

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        return "/index";
    }

    @RequestMapping("/userInfo")
    public String table1(Model model) {
        return "userInfo";
    }

    @RequestMapping("/table2")
    public String table2(Model model) {
        return "/table2";
    }

    @RequestMapping("/table3")
    public String table3(Model model) {
        return "/table3";
    }


    @GetMapping("/unAuth")
    public String unAuth() {
        return "unAuth";
    }
}
