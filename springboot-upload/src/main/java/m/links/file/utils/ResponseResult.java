package m.links.file.utils;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 通用请求响应
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
    }

    public ResponseResult(Integer code) {
        super();
        this.code = code;
    }

    public ResponseResult(T data) {
        super();
        this.data = data;
    }

    public ResponseResult(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ResponseResult(Integer code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
