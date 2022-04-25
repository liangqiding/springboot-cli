package com.springboot.cli.service;

import com.springboot.cli.domain.User;

import javax.servlet.http.HttpSession;

public interface IUserService {

    /**
     * 模拟登录
     *
     * @param username 用户名
     * @param password 密码
     * @param session  会话
     * @return true
     */
    boolean login(String username, String password, HttpSession session);

    /**
     * 模拟用户信息获取
     *
     * @return 用户信息
     */
    User getUser();
}
