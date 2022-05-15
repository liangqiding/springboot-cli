package com.springboot.cli.controller;


import com.springboot.cli.exception.MyException;
import com.springboot.cli.utils.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ding
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseResult<String> get() {
        return new ResponseResult<>("模拟成功获取数据");
    }

    /**
     * 自定义异常捕获
     */
    @GetMapping("/err")
    public ResponseResult<String> error() {
        throw new MyException("模拟异常", 4004);
    }

    /**
     * 其它异常捕获
     */
    @GetMapping("/err2")
    public ResponseResult<String> error2() {
        int i = 1 / 0;
        return new ResponseResult<>("OK");
    }
}
