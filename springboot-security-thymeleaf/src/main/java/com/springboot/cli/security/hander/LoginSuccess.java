package com.springboot.cli.security.hander;

import com.alibaba.fastjson.JSONObject;
import com.springboot.cli.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功
 *
 * @author ding
 */
@Component
@Slf4j
public class LoginSuccess extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("登录认证成功");
        // 这里写你登录成功后的逻辑，可以验证其他信息，如验证码等。
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(ResponseResult.ok().setMessage("登录认证成功")));
        // 重定向
        this.getRedirectStrategy().sendRedirect(request, response, "/index");
    }
}
