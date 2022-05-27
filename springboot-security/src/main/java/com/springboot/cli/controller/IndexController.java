package com.springboot.cli.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ding
 */
@RestController
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "请求成功";
    }

    @GetMapping("/get")
    public String get() {
        return "神秘代码9527";
    }
}
