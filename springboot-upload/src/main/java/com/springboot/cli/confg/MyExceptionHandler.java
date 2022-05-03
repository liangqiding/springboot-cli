package com.springboot.cli.confg;

import lombok.extern.slf4j.Slf4j;
import com.springboot.cli.utils.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author lqd
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseResult<String> exceptionHandler(Exception e) {
        return new ResponseResult<>(40004, "未知异常！原因是:" + e);
    }
}