package netty.web.scoket.server.config;



import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author qiding
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer code = 200;

    private boolean status = true;

    private String desc ="操作成功";

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

    public ResponseResult(Integer code, String desc) {
        super();
        this.code = code;
        this.desc = desc;
    }

    public ResponseResult(Integer code, String desc, T data) {
        super();
        this.code = code;
        this.desc = desc;
        this.data = data;
    }


}
