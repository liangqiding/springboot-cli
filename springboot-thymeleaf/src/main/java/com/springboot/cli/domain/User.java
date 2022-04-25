package com.springboot.cli.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户表
 *
 * @author ding
 */
@Data
@Accessors(chain = true)
public class User {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户别称
     */
    private String name;

    /**
     * 角色
     */
    private String role;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 创建日期
     */
    private Date createdDate;

}
