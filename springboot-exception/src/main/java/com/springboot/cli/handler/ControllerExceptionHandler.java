package com.springboot.cli.handler;


import com.springboot.cli.exception.MyException;
import com.springboot.cli.utils.ResponseResult;
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
    public ResponseResult<String> handleUserNotExistsException(MyException e) {
        log.error("捕获到自定义异常：{}", e.getMessage());
        return new ResponseResult<>(ResponseResult.RespCode.MY_ERROR.getCode(), e.getMessage());
    }

    /**
     * 捕获其它异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<String> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseResult<>(ResponseResult.RespCode.ERROR);
    }

}
