package com.springboot.cli.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 请求工具类
 *
 * @author ding
 */
public class RequestUtils {

    /**
     * 获取session
     */
    public static HttpSession getHttpSession() {
        return getHttpRequest().getSession();
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        return servletRequestAttributes.getRequest();
    }
}

