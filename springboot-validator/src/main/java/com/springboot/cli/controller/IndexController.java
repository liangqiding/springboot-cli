package com.springboot.cli.controller;


import com.alibaba.fastjson.JSON;
import com.springboot.cli.dto.UserVo;
import com.springboot.cli.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ding
 */
@RestController
@Validated
@Slf4j
public class IndexController {

    @GetMapping("/test1")
    public ResponseResult<String> test1(@NotBlank(message = "用户名不能为空") String name,
                                        @NotNull(message = "用户名id不能为空") Long userId) {
        log.info("参数为:{},{}", name, userId);
        return ResponseResult.ok();
    }

    @GetMapping("/test2")
    public ResponseResult<String> test2(@Validated UserVo userVo) {
        log.info("参数为:{}", JSON.toJSONString(userVo));
        return ResponseResult.ok();
    }
}
