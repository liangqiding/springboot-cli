package com.springboot.cli.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ding
 */
@Data
@Accessors(chain = true)
@ApiModel("用户信息表")
public class User {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户性别")
    private String sex;
}
