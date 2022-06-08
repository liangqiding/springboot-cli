package com.springboot.cli.security.hander;

import com.alibaba.fastjson.JSONObject;
import com.springboot.cli.utils.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 拒绝访问，无权限
 *
 * @author ding
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AccessFailure implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        log.info("拒绝访问，无权限");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(ResponseResult.fail(ResponseResult.RespCode.UNAUTHORIZED)));
    }
}
