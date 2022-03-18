package netty.web.scoket.server.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netty.web.scoket.server.config.ResponseResult;
import netty.web.scoket.server.config.WebSocketSession;
import org.springframework.stereotype.Component;

/**
 * webSocket 协议处理
 *
 * @author : qiDing
 * date: 2021-03-17 13:59
 */
@ChannelHandler.Sharable
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketServerHandler {

    public static Channel channels = null;

    /**
     * 对webSocket 首次握手进行解析
     */
    public void handleHttpRequest(Channel channel, FullHttpRequest request) {
        // 校验token并获取用户id
        String uri = request.uri();
        // 参数分别是 (ws地址,子协议,是否扩展,最大frame长度)
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(getWebSocketLocation(request), null, true, 5 * 1024 * 1024);
        WebSocketServerHandshaker handShaker = factory.newHandshaker(request);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
        } else {
            handShaker.handshake(channel, request);
        }
        WebSocketSession.ADD_C_SHAKER.accept(channel.id(), handShaker);
        channels = channel;
    }

    /**
     * webSocket过滤器
     */
    public boolean filter(Channel channel, FullHttpRequest request, String userId) {
        if (!request.decoderResult().isSuccess()) {
            log.error("非webSocket请求");
            this.sendResponse(channel, request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.BAD_REQUEST, channel.alloc().buffer()));
            return false;
        }
        if (!HttpMethod.GET.equals(request.method())) {
            log.error("非GET请求");
            this.sendResponse(channel, request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.FORBIDDEN, channel.alloc().buffer()));
            return false;
        }
        // 校验userId合法性
        if ("".equals(userId)) {
            log.error("webSocket协议 token无效 ");
            this.sendResponse(channel, request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.UNAUTHORIZED, channel.alloc().buffer()));
            return false;
        }
        return true;
    }

    /**
     * SSL支持采用wss:
     */
    private String getWebSocketLocation(FullHttpRequest request) {
        String location = request.headers().get(HttpHeaderNames.HOST) + "/websocket";
        return "ws://" + location;
    }

    /**
     * 处理消息
     * handShaker.close(channel, (CloseWebSocketFrame) frame.retain());
     */
    public void handleWebSocketRequest(Channel channel, WebSocketFrame frame) {

        // 获取webSocket 协议工厂
        WebSocketServerHandshaker handShaker = WebSocketSession.GET_SHAKER_BY_C.apply(channel.id());
        //关闭
        if (frame instanceof CloseWebSocketFrame) {
            log.info("--------------------WebSocketServerHandler收到关闭请求-----------------");
            handShaker.close(channel, (CloseWebSocketFrame) frame.retain());
            // 清理缓存
            return;
        }

        //握手 PING/PONG
        if (frame instanceof PingWebSocketFrame) {
            channel.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        //文本接收和发送
        if (frame instanceof TextWebSocketFrame) {
            log.info("WebSocketServerHandler收到消息：{}", ((TextWebSocketFrame) frame).text());
            String request = ((TextWebSocketFrame) frame).text();
            log.info("服务端收到：" + request);
            if ("ping".equals(request)) {
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(new ResponseResult<>())));
                return;
            }
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(new ResponseResult<>())));
//            this.msgHandler(channel, request);
            return;
        }

        if (frame instanceof BinaryWebSocketFrame) {
            channel.write(frame.retain());
        }
    }

    /**
     * 服务器webSocket Request消息处理
     */
    public void msgHandler(Channel channel, String request) {
        JSONObject data = JSON.parseObject(request);
        Integer reqType = data.getInteger("reqType");
        JSONObject params = data.getJSONObject("data");
        WebSocketRequestEnum.webSocketMsgHandler(reqType, channel, params);
    }

    /**
     * http 握手通用响应
     */
    private void sendResponse(Channel channel, FullHttpRequest req, FullHttpResponse resp) {
        HttpResponseStatus status = resp.status();
        if (status != HttpResponseStatus.OK) {
            ByteBufUtil.writeUtf8(resp.content(), status.toString());
            HttpUtil.setContentLength(req, resp.content().readableBytes());
        }
        boolean keepAlive = HttpUtil.isKeepAlive(req) && status == HttpResponseStatus.OK;
        HttpUtil.setKeepAlive(req, keepAlive);
        ChannelFuture future = channel.write(resp);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}

