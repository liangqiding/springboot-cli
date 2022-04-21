package com.springboot.cli.controller;


import com.springboot.cli.entity.User;
import com.springboot.cli.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class IndexController {

    private final RedisUtils redisUtils;

    /**
     * 设置缓存
     */
    @GetMapping("/set")
    public String set() {
        redisUtils.set("test", new User().setUserId(1L).setSex("男").setUsername("王小锤"));
        return "设置成功 ！";
    }

    /**
     * 获取缓存
     */
    @GetMapping("/get")
    public String get() {
        User test = redisUtils.get("test", User.class);
        System.out.println(test);
        return "获取成功 ！";
    }
}
