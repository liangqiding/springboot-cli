package com.springboot.cli.controller;

import com.springboot.cli.annotation.CustomLog;
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
    @GetMapping("/test")
    @CustomLog("基础web")
    public String test(String msg) {
        return "欢迎使用 springboot-cli ！";
    }

}
