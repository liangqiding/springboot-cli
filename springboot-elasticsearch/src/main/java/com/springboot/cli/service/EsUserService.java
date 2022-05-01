package com.springboot.cli.service;

import com.springboot.cli.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ding
 */
public interface EsUserService {

    /**
     * 创建索引
     *
     * @throws IOException e
     */
    void createIndex() throws IOException;

    /**
     * 索引是否存在
     *
     * @param indexName 索引名
     * @return true 存在
     * @throws IOException e
     */
    boolean indexExists(String indexName) throws IOException;

    /**
     * 获取索引
     *
     * @throws IOException e
     */
    void getIndex() throws IOException;

    /**
     * 创建用户
     *
     * @param user 用户
     * @throws IOException e
     */
    void createUser(User user) throws IOException;

    /**
     * 批量创建用户
     *
     * @param userList 用户集合
     * @throws IOException e
     */
    void createUserBulk(List<User> userList) throws IOException;

    /**
     * 获取用户
     *
     * @param userId 用户id
     * @return user
     * @throws IOException e
     */
    User getUser(long userId) throws IOException;

    /**
     * 搜索用户
     *
     * @throws IOException e
     */
    ArrayList<User> searchUser() throws IOException;

    /**
     * 更新用户
     *
     * @param user 用户
     * @throws IOException e
     */
    void updateUser(User user) throws IOException;

    /**
     * 删除用户
     *
     * @param userId id
     * @throws IOException e
     */
    void deleteUser(long userId) throws IOException;


}
