package com.springboot.cli.controller;

import com.springboot.cli.domain.User;
import com.springboot.cli.shiro.jwt.JwtProvider;
import com.springboot.cli.utils.ResponseResult;
import com.springboot.cli.utils.ShiroUtils;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;


/**
 * @author ding
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtProvider jwtProvider;

    /**
     * 模拟一个数据库用户
     * 账号admin
     * 密码123456
     */
    private final static HashMap<String, User> USER_MAP = new LinkedHashMap<>() {
        {
            put("admin", new User()
                    .setUserId(1L)
                    .setUsername("admin")
                    .setPassword(ShiroUtils.md5("123456"))
            );
        }
    };

    /**
     * 登录
     */
    @PostMapping(value = "/login")
    public ResponseResult<String> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        User user = USER_MAP.get(username);
        if (Objects.isNull(user)) {
            return ResponseResult.fail("用户不存在");
        }
        // 密码加密校验
        if (ShiroUtils.verifyPassword(password, user.getPassword())) {
            String token = jwtProvider.createToken(user.getUserId());
            return ResponseResult.ok(token);
        }
        return ResponseResult.fail("账号或密码错误");
    }

    /**
     * 注册
     */
    @PostMapping(value = "/register")
    public ResponseResult<String> register(@RequestParam("username") String username,
                                           @RequestParam("password") String password) {
        USER_MAP.put(username, new User()
                .setUserId(USER_MAP.size() + 1L)
                .setUsername(username)
                // 对密码进行加密保存
                .setPassword(ShiroUtils.md5(password)));
        return ResponseResult.ok("注册成功");
    }

    /**
     * 获取用户
     */
    @GetMapping("/getUser")
    public ResponseResult<User> getUser() {
        // 获取当前登录的用户id
        Long userId = ShiroUtils.getUserId(Long.class);
        User user = USER_MAP.values()
                .stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElseThrow();
        return ResponseResult.ok(user);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public ResponseResult<String> logout() {
        SecurityUtils.getSubject().logout();
        return ResponseResult.ok("成功退出登录");
    }

}
