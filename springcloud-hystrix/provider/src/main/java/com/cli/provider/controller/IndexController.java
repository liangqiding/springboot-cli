package com.cli.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IndexController {

    @GetMapping("get")
    public String get() throws InterruptedException {
         Thread.sleep(5000);
        return "喵喵喵";
    }
}
