package com.springboot.cli.handler;


import com.springboot.cli.exception.MyException;
import com.springboot.cli.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


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
     * 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult<String> defaultException(Exception e) {
        if (e instanceof NullPointerException){
            log.error("空指针异常:" + e.getMessage(), e);
            return ResponseResult.fail("空指针异常");
        }
        log.error("未知异常:" + e.getMessage(), e);
        return ResponseResult.fail();
    }

}
