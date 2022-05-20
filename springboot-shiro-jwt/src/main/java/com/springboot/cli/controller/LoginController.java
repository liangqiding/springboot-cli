package com.springboot.cli.controller;

import com.springboot.cli.shiro.jwt.TokenProvider;
import com.springboot.cli.utils.ResponseResult;
import com.springboot.cli.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;


/**
 * @author ding
 */
@RestController
public class LoginController {

    @PostMapping(value = "/login")
    public ResponseResult<String> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        // 校验逻辑自行实现
        if (true) {
            String token = TokenProvider.createToken(10001L);
            return new ResponseResult<>(token);
        }
        return new ResponseResult<>(ResponseResult.RespCode.LOGIN_FAIL);
    }

    @GetMapping("/getUserId")
    public ResponseResult<String> getUserId() {
        return new ResponseResult<>("用户id为：" + ShiroUtils.getUserId(Long.class));
    }

    @GetMapping("/logout")
    public ResponseResult<String> logout() {
        SecurityUtils.getSubject().logout();
        return new ResponseResult<>("退出登录成功！");
    }

}
