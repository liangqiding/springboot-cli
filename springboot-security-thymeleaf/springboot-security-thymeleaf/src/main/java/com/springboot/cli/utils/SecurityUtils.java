package com.springboot.cli.utils;

import com.springboot.cli.security.AuthUser;
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
    public static AuthUser getUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取登录者的id
     */
    public static Long getUserId() {
        AuthUser principal = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUserId();
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
