package com.springboot.cli.utils;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 通用响应体
 *
 * @author qiding
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer code;

    private String message;

    private T data;

    public ResponseResult(Integer code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private static <T> ResponseResult<T> build(Integer code, String message, T data) {
        return new ResponseResult<>(code, message, data);
    }

    public static <T> ResponseResult<T> ok() {
        return new ResponseResult<>(RespCode.OK.code, RespCode.OK.message, null);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return build(RespCode.OK.code, RespCode.OK.message, data);
    }

    public static <T> ResponseResult<T> fail() {
        return fail(RespCode.ERROR.message);
    }

    public static <T> ResponseResult<T> fail(String message) {
        return fail(RespCode.ERROR, message);
    }

    public static <T> ResponseResult<T> fail(RespCode respCode) {
        return fail(respCode, respCode.message);
    }

    public static <T> ResponseResult<T> fail(RespCode respCode, String message) {
        return build(respCode.getCode(), message, null);
    }

    public enum RespCode {
        /**
         * 业务码
         */
        OK(20000, "请求成功"),
        MY_ERROR(20433, "自定义异常"),
        UNAUTHORIZED(20401, "未授权"),
        LOGIN_FAIL(20402, "账号或密码错误"),
        ERROR(20400, "未知异常");

        RespCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        private final int code;
        private final String message;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
