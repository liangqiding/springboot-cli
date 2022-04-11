package netty.web.scoket.server.config;

import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * webSocket下管理userId和channel的关系
 * 用于快速获取管道进行消息推送服务
 *
 * @author : qiDing
 * date: 2021-03-18 09:31
 */
@Slf4j
public class WebSocketSession {

    public final static HashMap<ChannelId, WebSocketServerHandshaker> CHANNEL_SHAKER = new HashMap<>();

    public static final BiConsumer<ChannelId, WebSocketServerHandshaker> ADD_C_SHAKER = CHANNEL_SHAKER::put;

    public static final Function<ChannelId, WebSocketServerHandshaker> GET_SHAKER_BY_C = CHANNEL_SHAKER::get;

}
