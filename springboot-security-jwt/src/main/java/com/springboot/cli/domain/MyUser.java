package com.springboot.cli.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 模拟数据库实体，实际环境中应该: 1用户---->n角色---->n权限
 *
 * @author ding
 */
@Data
@Accessors(chain = true)
public class MyUser {

    /**
     * id
     */
    private Long userId;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private List<String> auths;

}
