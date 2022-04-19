package com.exel.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 手机
 *
 * @author qiding
 */
@Data
@Accessors(chain = true)
public class User {
    /**
     * 主键
     */
    private Integer userId;

    @Excel(name = "用户名", orderNum = "1", width = 30, needMerge = true)
    private String username;

    @Excel(name = "年龄", orderNum = "2", width = 10, needMerge = true)
    private Integer age;

    @Excel(name = "性别", orderNum = "3", width = 30, needMerge = true)
    private String sex;

    @ExcelCollection(name = "课程", orderNum = "4")
    private List<Course> courseList;
}
