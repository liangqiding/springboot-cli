package netty.web.scoket.server.webSocket;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 * description : webSocket 协议
 *
 * @author : qiDing
 * date: 2021-04-08 14:06
 */

public enum WebSocketRequestEnum {

    /**
     * app  主动请求
     */
    REQ_TASK_PUBLISH(1, "割草任务下发") {
        @Override
        public void msgHandler(Long deviceId, Channel webSocketChannel, JSONObject data) {
            log.info("####割草任务下发####{}", data);

        }
    },
    REQ_CAR_CONTROLLER(2, "割草任务控制") {
        @Override
        public void msgHandler(Long deviceId, Channel webSocketChannel, JSONObject data) {
            log.info("####割草任务控制####{}", data);

        }
    },
    REQ_SPEED(3, "速度控制") {
        @Override
        public void msgHandler(Long deviceId, Channel webSocketChannel, JSONObject data) {
            log.info("####割草速度控制####{}", data);

        }
    },
    PING(99, "ping") {
        @Override
        public void msgHandler(Long deviceId, Channel webSocketChannel, JSONObject data) {
            log.info("####ping####{}", data);
            WebSocketResponseEnum.response(WebSocketResponseEnum.PONG.getReqType(), webSocketChannel, null);
        }
    },
    PONG(100, "pong") {
        @Override
        public void msgHandler(Long deviceId, Channel webSocketChannel, JSONObject data) {
            log.info("####pong####{}", data);
        }
    };

    private static final Logger log = LoggerFactory.getLogger(WebSocketRequestEnum.class);


    private final Integer reqType;


    private final String desc;

    WebSocketRequestEnum(Integer reqType, String desc) {
        this.reqType = reqType;
        this.desc = desc;
    }

    public Integer getReqType() {
        return reqType;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * webSocket 消息处理
     */
    public abstract void msgHandler(Long deviceId, Channel webSocketChannel, JSONObject data);

    /**
     * 主动请求处理
     */
    public static void webSocketMsgHandler(Integer reqType, Channel webSocketChannel, JSONObject data) {
        Long deviceId = data.getLong("deviceId");
        Arrays.stream(values().clone())
                .filter(e -> e.reqType.equals(reqType))
                .findFirst()
                .ifPresentOrElse(webSocketRequestEnum -> webSocketRequestEnum.msgHandler(deviceId, webSocketChannel, data), webSocketChannel::close);
    }

}
