package com.springboot.cli.utils;

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
     * 获取登录信息
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        getSubject().logout();
    }

}
