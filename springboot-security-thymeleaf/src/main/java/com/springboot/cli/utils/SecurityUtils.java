package com.springboot.cli.utils;

import com.springboot.cli.security.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全框架工具类封装
 *
 * @author ding
 */
public class SecurityUtils {

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

}
