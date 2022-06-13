package com.springboot.cli.utils;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * 生成及校验图片验证码
 *
 * @author ding
 */
public class CaptchaUtils {

    private static final String CAPTCHA = "captcha";

    /**
     * CircleCaptcha 圆圈干扰验证码
     * 定义图形验证码的长、宽、验证码字符数、干扰元素个数
     */
    public static void getCircleCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 20);
        session.setAttribute(CAPTCHA, lineCaptcha.getCode());
        writeResp(lineCaptcha, response);
    }

    /**
     * 验证码校验
     *
     * @param code 验证码
     */
    public static boolean verify(String code) {
        HttpSession session = RequestUtils.getHttpSession();
        String captcha = (String) session.getAttribute(CAPTCHA);
        return code.equals(captcha);
    }

    /**
     * http图片响应
     */
    private static void writeResp(AbstractCaptcha abstractCaptcha, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            abstractCaptcha.write(out);
        } finally {
            if (Objects.nonNull(out)) {
                out.close();
            }
        }
    }
}
