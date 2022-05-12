package com.cli.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/server2")
public class IndexController {

    @GetMapping("/get")
    public String get() {
        return "我是服务2";
    }
}
