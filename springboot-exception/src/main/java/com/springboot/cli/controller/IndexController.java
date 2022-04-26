package com.springboot.cli.controller;


import com.springboot.cli.exception.MyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ding
 */
@RestController
public class IndexController {

    /**
     * 自定义异常捕获
     */
    @GetMapping("/error")
    public String error() {
        throw new MyException("模拟异常", 4004);
    }

    /**
     * 其它异常捕获
     */
    @GetMapping("/error2")
    public String error2() {
        int i = 1 / 0;
        return "ok";
    }
}
