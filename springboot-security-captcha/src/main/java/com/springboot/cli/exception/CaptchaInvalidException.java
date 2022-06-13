package com.springboot.cli.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常
 *
 * @author ding
 */
public class CaptchaInvalidException extends AuthenticationException {

    public CaptchaInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaInvalidException(String msg) {
        super(msg);
    }
}
