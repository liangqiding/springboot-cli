package com.springboot.cli.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ding
 */
@Data
@Accessors(chain = true)

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String username;

    private String sex;
}
