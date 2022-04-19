package com.exel.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * @author qiding
 */
@Data
@Accessors(chain = true)
public class Course implements java.io.Serializable {

    /**
     * 主键
     */
    private Integer courseId;

    @Excel(name = "课程名", orderNum = "1", width = 30)
    private String courseName;

    @Excel(name = "类型", orderNum = "2", width = 10)
    private String courseType;

    @Excel(name = "日期", format = "yyyy-MM-dd", width = 20)
    private Date createdDate;


}
