package com.example.jwt_dome.controller;

import com.example.jwt_dome.config.PasswordEncoder;
import com.example.jwt_dome.jwt.JwtUser;
import com.example.jwt_dome.jwt.TokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author liangQiDing
 */
@RestController
@AllArgsConstructor
@Api("token测试服务器")
public class TokenController {

    private final TokenProvider tokenProvider;

    /**
     * 模拟数据库数据
     */
    private final static HashMap<String, String> USER = new HashMap<>();

    @PostConstruct
    public void init() {
        USER.put("admin","e10adc3949ba59abbe56e057f20f883e");
    }

    @GetMapping("/token/validate")
    @ApiOperation("token校验")
    public JwtUser tokenValidate(String token) {
        return tokenProvider.getAuthentication(token);
    }

    @GetMapping("/login")
    @ApiOperation("登陆示例（账号admin,密码123456）")
    public String login(String username,String password) {
        if(PasswordEncoder.matches(password, USER.get(username))){
            return tokenProvider.createToken("001", "web", "admin");
        }
        return "error";
    }

}
