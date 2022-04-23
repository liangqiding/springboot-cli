package com.springboot.cli.service;

import com.springboot.cli.entity.User;

import java.util.List;

public interface IUserService {

    /**
     * 添加用户
     *
     * @param user 用户数据
     */
    void insertUser(User user);

    /**
     * 批量添加用户
     *
     * @param users 用户数据
     */
    void insertUser(List<User> users);

    /**
     * 更新用户
     *
     * @param user 用户数据
     */
    void updateUser(User user);

    /**
     * 查询用户
     *
     * @param user 用户数据
     * @return list
     */
    List<User> listUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    void deleteUser(Long userId);
}
