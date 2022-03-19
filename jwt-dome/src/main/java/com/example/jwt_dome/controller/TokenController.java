package com.example.jwt_dome.controller;

import com.example.jwt_dome.config.PasswordEncoder;
import com.example.jwt_dome.jwt.AuthStorage;
import com.example.jwt_dome.jwt.JwtUser;
import com.example.jwt_dome.jwt.TokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

/**
 * @author liangQiDing
 */
@RestController
@Api("token测试服务器")
public class TokenController {

    /**
     * 模拟数据库数据 账号 admin  密码 123456
     */
    private final static HashMap<String, String> USER = new HashMap<>() {
        {
            put("admin", "e10adc3949ba59abbe56e057f20f883e");
        }
    };

    @GetMapping("/login")
    @ApiOperation("登陆示例（账号admin,密码123456）")
    public String login(String username, String password) {
        if (PasswordEncoder.matches(password, USER.get(username))) {
            // 模拟一个用户的数据 用户id为1  登录端为网页web  角色是admin
            return TokenProvider.createToken("1", "web", "admin");
        }
        return "error";
    }

    @GetMapping("/token/validate")
    @ApiOperation("token校验")
    public JwtUser tokenValidate(String token) {
        return TokenProvider.checkToken(token);
    }

    @GetMapping("/get/Info")
    @ApiOperation("模拟拦截")
    public String getInfo() {
        // 从全局环境中获取用户id
        JwtUser user = AuthStorage.getUser();
        return "用户："+user.getUserId() + "，请求成功";
    }
}
