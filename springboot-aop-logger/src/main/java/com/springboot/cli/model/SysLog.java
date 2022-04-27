package com.springboot.cli.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 日记实体
 *
 * @author ding
 */
@Data
@Accessors(chain = true)
public class SysLog implements Serializable {

    private static final long serialVersionUID = -6309732882044872298L;

    /**
     * 日记id
     */
    private Long logId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 执行时间
     */
    private Long executionTime;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * 说明
     */
    private String desc;

    /**
     * 创建日期
     */
    private Date createTime;
}
