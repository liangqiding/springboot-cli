package com.exel.demo.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Exel导出封装
 *
 * @author qiding
 */
@Slf4j
public class ExelUtils {

    /**
     * 导出xlsx文件
     *
     * @param title      标题
     * @param sheetName  页名
     * @param list       数据集合
     * @param temClass   实体模板
     * @param outputPath 导出路径
     */
    public static void exportExcel(String title, String sheetName, List<?> list, Class<?> temClass, String outputPath) throws IOException {
        ExportParams params = new ExportParams(title, sheetName, ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, temClass, list);
        FileOutputStream fos = new FileOutputStream(outputPath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * 导出Excel文件（模板方式），输出到指定路径
     *
     * @param list         数据集合
     * @param xlsxTemplate 模板存放地址
     * @param outputPath   导出路径
     */
    public static void exportExcelByTem(List<?> list, XlsxTemplate xlsxTemplate, String outputPath) throws IOException {
        TemplateExportParams params = new TemplateExportParams(
                xlsxTemplate.getUrl());
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("list", list);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        FileOutputStream fos = new FileOutputStream(outputPath);
        workbook.write(fos);
        fos.close();
    }


    /**
     * 生成并下载Excel文件
     *
     * @param title     标题
     * @param sheetName 页名
     * @param list      数据集合
     * @param temClass  实体模板
     * @param response  http 响应体
     */
    public static void downloadExcel(String title, String sheetName, List<?> list, Class<?> temClass, HttpServletResponse response) throws IOException {
        ExportParams params = new ExportParams(title, sheetName, ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, temClass, list);
        responseFile(title + ".xlsx", workbook, response);
    }


    /**
     * 生成并下载Excel文件(模板方式)，直接供前端下载
     *
     * @param list         数据集合
     * @param xlsxTemplate 模板存放地址
     * @param response     http 响应体
     */
    public static void downloadExcelByTem(List<?> list, XlsxTemplate xlsxTemplate, HttpServletResponse response) throws IOException {
        TemplateExportParams params = new TemplateExportParams(
                xlsxTemplate.getUrl());
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("list", list);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        responseFile(xlsxTemplate.getTitle() + ".xlsx", workbook, response);
    }

    /**
     * 设置http响应体为文件
     */
    private static void responseFile(String fileName, Workbook workbook, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    /**
     * 模板
     */
    public enum XlsxTemplate {

        /**
         * 模板地址
         */
        USER_COURSE("exportTemplate/user_course_tem.xlsx", "课程表"),
        TEST("exportTemplate/xx.xlsx", "xx模板文件"),
        ;

        private final String url;

        private final String title;

        XlsxTemplate(String url, String title) {
            this.url = url;
            this.title = title;
        }

        public String getUrl() {
            return this.url;
        }

        public String getTitle() {
            return this.title;
        }
    }

}
