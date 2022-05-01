package com.springboot.cli.entity;

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
     * 用户别称
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建日期
     */
    private Date createdDate;

}
