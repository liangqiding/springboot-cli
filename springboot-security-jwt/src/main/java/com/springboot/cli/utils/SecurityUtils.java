package com.springboot.cli.utils;

import cn.hutool.jwt.JWT;
import com.springboot.cli.security.jwt.JwtProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全框架工具类封装
 *
 * @author ding
 */
public class SecurityUtils {

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 获取登录者的信息
     */
    public static JWT getInfo() {
        return (JWT) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取登录者的id
     */
    public static Object getUserId() {
        JWT info = getInfo();
        return info.getPayload("userId");
    }

    /**
     * 获取登录者的权限
     */
    public static String getAuths() {
        return (String) getInfo().getPayload(JwtProvider.AUTHORITY);
    }

    /**
     * 密码加密
     *
     * @param password 明文密码
     * @return 加密后的密码
     */
    public static String passwordEncoder(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    /**
     * 密码比对
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 加密后的密码
     * @return 是否通过
     */
    public static boolean passwordMatches(CharSequence rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }

}
