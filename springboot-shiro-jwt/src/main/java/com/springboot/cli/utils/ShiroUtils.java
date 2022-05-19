package com.springboot.cli.utils;

import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro 工具类
 * 用于快速获取登录信息
 *
 * @author ding
 */
public class ShiroUtils {


    /**
     * 获取登录信息
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取用户id
     *
     * @param <T> id类型
     */
    public static <T> T getUserId(Class<T> c) {
        Subject subject = getSubject();
        Claims claims = (Claims) subject.getPrincipal();
        return claims.get("userId", c);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        getSubject().logout();
    }

}
