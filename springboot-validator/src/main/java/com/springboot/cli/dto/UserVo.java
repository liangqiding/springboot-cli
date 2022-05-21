package com.springboot.cli.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class UserVo {

    @NotBlank(message = "用户名称不能为空")
    private String name;

    @NotNull(message = "编号不能为空")
    @Max(value = 999, message = "编号需小于1000")
    @Min(value = 0, message = "编号需大于0")
    private Integer number;

    @NotNull(message = "邮箱不能为空")
    private String email;

    @Length(message = "手机号不合法")
    private String phone;
}
