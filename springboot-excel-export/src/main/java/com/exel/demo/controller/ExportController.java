package com.exel.demo.controller;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.exel.demo.entity.User;
import com.exel.demo.utils.ExelUtils;
import com.exel.demo.utils.MockDataUtils;
import com.exel.demo.utils.WordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 附件导出下载，前端api接口
 *
 * @author qiding
 */
@RestController
@Slf4j
public class ExportController {

    /**
     * 普通导出EXCEL
     */
    @GetMapping("excel/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        // 获取模拟数据
        List<User> userListMockData = MockDataUtils.getUserListMockData();
        ExelUtils.downloadExcel("课程表", "第一页", userListMockData, User.class, response);
    }

    /**
     * 根据模板导出EXCEL
     */
    @GetMapping("excel/downloadByTem")
    public void downloadExcelByTem(HttpServletResponse response) throws IOException {
        // 获取模拟数据
        List<User> userListMockData = MockDataUtils.getUserListMockData();
        ExelUtils.downloadExcelByTem(userListMockData, ExelUtils.XlsxTemplate.USER_COURSE, response);
    }

    /**
     * 根据导出Word文档
     */
    @GetMapping("word/download")
    public void downloadWord(HttpServletResponse response) {
        WordUtils.downloadWord(response);
    }
}
