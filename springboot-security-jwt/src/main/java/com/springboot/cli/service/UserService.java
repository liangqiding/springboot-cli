package com.springboot.cli.service;

import com.springboot.cli.domain.MyUser;

public interface UserService {

    /**
     * 获取用户
     *
     * @param username 账号
     * @return user
     */
    MyUser getUser(String username);

}
