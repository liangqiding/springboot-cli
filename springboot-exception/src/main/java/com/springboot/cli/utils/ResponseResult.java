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

    /**
     * http默认构造
     */
    public ResponseResult() {
        super();
        this.code = RespCode.OK.code;
        this.message = RespCode.ERROR.message;
    }


    public ResponseResult(T data) {
        super();
        this.code = RespCode.OK.code;
        this.message = RespCode.OK.message;
        this.data = data;
    }

    public ResponseResult(RespCode respCode) {
        super();
        this.code = respCode.getCode();
        this.message = respCode.getMessage();
    }

    public ResponseResult(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ResponseResult(Integer code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public enum RespCode {
        /**
         * 业务码
         */
        OK(20000, "请求成功"),
        MY_ERROR(20433, "自定义异常"),
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
