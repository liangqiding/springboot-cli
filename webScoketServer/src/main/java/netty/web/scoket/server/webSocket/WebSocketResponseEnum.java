package netty.web.scoket.server.webSocket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import netty.web.scoket.server.config.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;


/**
 * description : webSocket 协议
 *
 * @author : qiDing
 * date: 2021-04-08 14:06
 */

public enum WebSocketResponseEnum {

    /**
     * app 消息响应
     */
    RESP_TASK_PATH(1, "1-实时路径") {
        @Override
        public void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status) {
            sendMessage(wsChannel, JSON.toJSONString(new ResponseResult<>(data)));
        }
    },
    RESP_TASK_STATUS(2, "2-任务状态") {
        @Override
        public void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status) {
            sendMessage(wsChannel, JSON.toJSONString(new ResponseResult<>(data)));
        }
    },
    RESP_REAL_LOCATION(3, "3-实时位置") {
        @Override
        public void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status) {
            sendMessage(wsChannel, JSON.toJSONString(new ResponseResult<>(data)));
        }
    },
    RESP_DEV_HEALTH(4, "4-实时健康情况") {
        @Override
        public void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status) {
            sendMessage(wsChannel, JSON.toJSONString(new ResponseResult<>(data)));
        }
    },
    PONG(100, "100-pong") {
        @Override
        public void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status) {

        }
    },
    RESP_ONLINE(10, "上下线设备通知") {
        @Override
        public void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(data));
            sendMessage(wsChannel, JSON.toJSONString(new ResponseResult<>(res)));
        }
    },
    RESP_ALL_ONLINE(102, "获取所有在线设备") {
        @Override
        public void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status) {
           }
    },
    RESP_RESPONSE(200, "小车响应") {
        @Override
        public void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status) {
         }
    };

    private static final Logger log = LoggerFactory.getLogger(WebSocketResponseEnum.class);


    private final Integer reqType;

    private final String desc;

    WebSocketResponseEnum(Integer reqType, String desc) {
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
    public abstract void sendResponseMessageEnum(Channel wsChannel, JSONObject data, boolean status);

    /**
     * webSocket 消息转发
     */
    public static void response(Integer reqType, Channel wsChannel, JSONObject data) {
        log.info("webSocket 消息处理: {}", data);
        response(reqType, wsChannel, data, true);
    }

    public static void response(Integer reqType, Channel wsChannel, JSONObject data, boolean status) {
        log.info("webSocket 消息处理: {}", data);
        Arrays.stream(values().clone())
                .filter(e -> e.reqType.equals(reqType))
                .findFirst()
                .ifPresentOrElse(webProtoEnum -> webProtoEnum.sendResponseMessageEnum(wsChannel, data, status), wsChannel::close);
    }

    /**
     * 消息发送
     */
    public static void sendMessage(Channel wsChannel, String msg) {
        log.info("webSocket 服务器 -> app：{}", msg);
        wsChannel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    /**
     * 上下线通知
     */
    public static void notifiesOnline(Long deviceId, boolean online) {
        JSONObject resp = new JSONObject();
        resp.put("online", online);
        notifies(deviceId, WebSocketResponseEnum.RESP_ONLINE.reqType, resp);
    }

    /**
     * 主动通知服务所有在线的物主
     */
    public static void notifies(Long deviceId) {
//        smUserDeviceService.listUserByDev(deviceId).forEach(uId -> notifies(deviceId, WebSocketResponseEnum.RESP_STATUS.getReqType()));
    }

    /**
     * 主动通知服务所有在线的物主
     */
    public static void notifies(Long deviceId, Integer reqType) {

    }

    /**
     * 主动通知服务所有在线的物主
     */
    public static void notifies(Long deviceId, Integer reqType, JSONObject response) {
        notifies(deviceId, reqType, response,true);
    }

    public static void notifies(Long deviceId, Integer reqType, JSONObject response,boolean status) {

    }


}
