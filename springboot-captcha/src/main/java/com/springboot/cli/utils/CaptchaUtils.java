package com.springboot.cli.utils;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 模拟redis存储验证码,生成环境建议使用redis存储验证码，设置一个有效期，如5分钟
     */
    private static final HashMap<String, String> CODE_MAP = new LinkedHashMap<>();

    /**
     * CircleCaptcha 圆圈干扰验证码
     * 定义图形验证码的长、宽、验证码字符数、干扰元素个数
     *
     * @param uuid     由web端生成一个随机uuid,登录时需将uuid和验证码一起提交,前后端分离时,可通过uuid确认验证码来源
     * @param response 响应体
     */
    public static void getCircleCaptcha(String uuid, HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 20);
        CODE_MAP.put(uuid, lineCaptcha.getCode());
        writeResp(lineCaptcha, response);
    }

    /**
     * ShearCaptcha 扭曲干扰验证码
     * 定义图形验证码的长、宽、验证码字符数、干扰线宽度
     *
     * @param uuid     由web端生成一个随机uuid,登录时需将uuid和验证码一起提交,前后端分离时,可通过uuid确认验证码来源
     * @param response 响应体
     */
    public static void getShearCaptcha(String uuid, HttpServletResponse response) throws IOException {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        CODE_MAP.put(uuid, captcha.getCode());
        writeResp(captcha, response);
    }


    /**
     * 自定义验证码
     * 加减验证码
     *
     * @param uuid     由web端生成一个随机uuid,登录时需将uuid和验证码一起提交,前后端分离时,可通过uuid确认验证码来源
     * @param response 响应体
     */
    public  static void getMathShearCaptcha(String uuid, HttpServletResponse response) throws IOException {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 45, 4, 4);
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator());
        CODE_MAP.put(uuid, captcha.getCode());
        writeResp(captcha, response);
    }

    /**
     * 验证码校验
     *
     * @param uuid 获取验证码时提交的uuid
     * @param code 验证码
     */
    public static  boolean verify(String uuid, String code) {
        boolean b = Optional
                .ofNullable(CODE_MAP.get(uuid))
                .stream()
                .anyMatch(c -> c.equals(code));
        if (b) {
            CODE_MAP.remove(uuid);
        }
        return b;
    }

    /**
     * http图片响应
     */
    private  static void writeResp(AbstractCaptcha abstractCaptcha, HttpServletResponse response) throws IOException {
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
