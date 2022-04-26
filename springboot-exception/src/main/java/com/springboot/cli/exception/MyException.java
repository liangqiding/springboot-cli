package com.springboot.cli.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author ding
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;

    private Integer code = 200;

    public MyException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MyException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MyException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MyException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}

