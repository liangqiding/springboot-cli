package com.springboot.cli.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {

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
}
