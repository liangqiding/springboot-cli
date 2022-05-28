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
public class Product {

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 用户名
     */
    private String productName;

    /**
     * 创建日期
     */
    private Date createdDate;

}
