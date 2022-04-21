package com.springboot.cli.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ding
 */
@RestController
public class IndexController {

    /**
     * 基础web
     */
    @GetMapping("/")
    public String hello() {
        return "欢迎使用 springboot-cli ！";
    }
}
