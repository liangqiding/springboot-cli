package com.springboot.cli.handler;


import com.springboot.cli.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 全局异常捕获
 * 若用 @RestControllerAdvice 替换 @ControllerAdvice 则可省略  @ResponseBody
 *
 * @author ding
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /**
     * 捕获我们定义的异常
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public HashMap<String, Object> handleUserNotExistsException(MyException e) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("code", e.getCode());
        map.put("message", e.getMessage());
        return map;
    }

    /**
     * 捕获其它异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HashMap<String, Object> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("code", 404);
        map.put("message", "服务器未知异常");
        return map;
    }

}
