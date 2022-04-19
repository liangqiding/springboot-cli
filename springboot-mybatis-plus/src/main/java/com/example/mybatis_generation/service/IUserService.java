package com.example.mybatis_generation.service;

import com.example.mybatis_generation.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qiDing
 * @since 2022-04-11
 */
public interface IUserService extends IService<User> {

    /**
     * 添加用户
     *
     * @param user 用户数据
     * @return b
     */
    boolean addUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return b
     */
    boolean delUserById(Long userId);

    /**
     * 删除用户，其它条件
     *
     * @param account 用户账号
     * @return b
     */
    boolean delUser(String account);

    /**
     * 更新用户
     *
     * @param user 用户数据
     * @return b
     */
    boolean updUser(User user);


    /**
     * 查询用户
     *
     * @param user 查询用户
     * @return b
     */
    List<User> listUser(User user);

    /**
     * 分页查询用户
     *
     * @param user     查询用户的条件
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return b
     */
    PageInfo<User> listUserPage(User user, Integer pageNum, Integer pageSize);
}
