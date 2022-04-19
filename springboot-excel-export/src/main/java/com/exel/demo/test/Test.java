package com.exel.demo.test;

import com.exel.demo.entity.User;
import com.exel.demo.utils.ExelUtils;
import com.exel.demo.utils.MockDataUtils;
import com.exel.demo.utils.WordUtils;

import java.util.List;

/**
 * 本类测试类
 *
 * @author qiding
 */
public class Test {

    public static void main(String[] args) throws Exception {
        // 获取模拟数据
        List<User> userListMockData1 = MockDataUtils.getUserListMockData();
        List<User> userListMockData2 = MockDataUtils.getUserListMockData();

        // 1. 普通导出xlsx
           ExelUtils.exportExcel("课程表", "第一页", userListMockData1, User.class, "D://test/基础课程表.xlsx");

        // 2. 通过模板导出xlsx
         ExelUtils.exportExcelByTem(userListMockData2, ExelUtils.XlsxTemplate.USER_COURSE, "D://test/模板课程表.xlsx");

        // 3. Word文档导出
         WordUtils.exportWord();
    }
}
