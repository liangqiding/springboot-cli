package com.springboot.cli.service;

import com.springboot.cli.entity.User;

public interface IUserService {

    /**
     * 缓存测试
     *
     * @param id 用户id 随便输入
     * @return 用户数据
     */
    User getUser(Long id);

    /**
     * 删除缓存测试
     *
     * @param id 用户id 随便输入
     */
    void delUser(Long id);
}
