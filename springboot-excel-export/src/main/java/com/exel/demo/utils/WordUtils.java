package com.exel.demo.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * doc 文件导出
 *
 * @author qiding
 */
public class WordUtils {

    public static void exportWord() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("time", "2022-04-19");
        map.put("content", "国内地址：https://gitee.com/liangqiding/springboot-cli 欢迎参与开源！");
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(
                    "exportTemplate/simple.docx", map);
            FileOutputStream fos = new FileOutputStream("d://test/simple.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadWord(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("time", "2022-04-19");
        map.put("content", "国内地址：https://gitee.com/liangqiding/springboot-cli 欢迎参与开源！");
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(
                    "exportTemplate/simple.docx", map);
            response.setCharacterEncoding("utf8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode("work文档生成测试.docx", StandardCharsets.UTF_8));
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
