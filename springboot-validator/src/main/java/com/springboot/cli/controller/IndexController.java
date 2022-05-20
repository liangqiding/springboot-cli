package com.springboot.cli.controller;


import com.springboot.cli.utils.ResponseResult;
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
    public ResponseResult<String> hello() {
        return ResponseResult.ok("hello");
    }

    @GetMapping("/err")
    public ResponseResult<String> error() {
        int i = 1 / 0;
        return ResponseResult.ok("模拟错误");
    }
}
