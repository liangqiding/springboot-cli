package com.springboot.cli.security.hander;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 退出登录
 *
 * @author DING
 */
@Slf4j
public class LogoutSuccess implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("注销成功!");
        //这里写你登录成功后的逻辑
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("注销成功!");
    }
}
