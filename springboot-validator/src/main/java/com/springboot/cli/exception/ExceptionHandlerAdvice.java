package com.springboot.cli.exception;

import cn.hutool.core.util.StrUtil;
import com.springboot.cli.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Objects;

/**
 * 描述：全局统一异常处理
 * @author ding
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 参数验证异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg =  Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        if (!StrUtil.isBlank(errorMsg)) {
            return ResponseResult.fail(errorMsg);
        }
        log.error(e.getMessage(),e);
        return ResponseResult.fail(e.getMessage());
    }

    /**
     * Validation参数异常
     */
    @ExceptionHandler(value = {ValidationException.class})
    public ResponseResult<String> constraintViolationException(ValidationException e) {
        if(e instanceof ConstraintViolationException){
            ConstraintViolationException err = (ConstraintViolationException) e;
            ConstraintViolation<?> constraintViolation = err.getConstraintViolations().stream().findFirst().get();
            String messageTemplate = constraintViolation.getMessageTemplate();
            if(!StrUtil.isBlank(messageTemplate)){
                return ResponseResult.fail(messageTemplate);
            }
        }
        log.error(e.getMessage(),e);
        return ResponseResult.fail(e.getMessage());
    }

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
