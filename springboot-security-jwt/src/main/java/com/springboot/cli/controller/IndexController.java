package com.springboot.cli.controller;

import com.springboot.cli.domain.MyUser;
import com.springboot.cli.security.jwt.JwtProvider;
import com.springboot.cli.service.UserService;
import com.springboot.cli.utils.ResponseResult;
import com.springboot.cli.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 *
 * @author ding
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class IndexController {

    private final JwtProvider jwtProvider;

    private final UserService userService;

    /**
     * 登录
     */
    @PostMapping(value = "/login")
    public ResponseResult<String> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        MyUser user = userService.getUser(username);
        if (Objects.nonNull(user) && SecurityUtils.passwordMatches(password, user.getPassword())) {
            String token = jwtProvider.createToken(user.getUserId(), user.getRoles(), user.getAuths());
            return ResponseResult.ok(token);
        }
        return ResponseResult.fail("账号或密码错误");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('read')")
    public ResponseResult<HashMap<String, Object>> info() {
        return ResponseResult.ok(new HashMap<>(1) {
            {
                put("userId", SecurityUtils.getUserId());
                put("auths", SecurityUtils.getAuths());
            }
        });
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('xxx') or hasAuthority('aaa')")
    public ResponseResult<String> test() {
        return ResponseResult.ok();
    }

    @GetMapping("/test1")
    @PreAuthorize("hasAuthority('write')")
    public ResponseResult<String> test1() {
        return ResponseResult.ok();
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('read')")
    public ResponseResult<String> test2() {
        return ResponseResult.ok();
    }


}
