package com.springboot.cli.controller;


import com.springboot.cli.entity.User;
import com.springboot.cli.service.IUserService;
import com.springboot.cli.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexController {

    private final IUserService userService;

    /**
     * 注解自动实现缓存
     */
    @GetMapping("/getUser")
    public User getUser(Long id) {
        return userService.getUser(id);
    }

    /**
     * 注解自动实现缓存清理
     */
    @GetMapping("/delUser")
    public String delUser(Long id) {
        userService.delUser(id);
        return "删除成功";
    }

    /**
     * 手动设置缓存
     */
    @GetMapping("/set")
    public String set() {
        RedisUtils.save("key", new User().setUserId(999L).setSex("男").setUsername("王小锤").setRemark("手动设置缓存"), 6000);
        return "设置成功 ！";
    }

    /**
     * 手动获取缓存
     */
    @GetMapping("/get")
    public User get() {
        return RedisUtils.get("key", User.class);
    }
}
