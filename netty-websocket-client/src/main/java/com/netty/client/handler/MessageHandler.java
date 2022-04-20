package com.netty.client.handler;

import com.netty.client.server.WebsocketClient;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * 消息处理,单例启动
 *
 * @author ding
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class MessageHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * websocket会话存储
     */
    private WebSocketClientHandshaker handShaker;
    /**
     * 用于回调判断握手是否成功
     */
    private ChannelPromise handshakeFuture;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // 连接前执行
        this.handshakeFuture = ctx.newPromise();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object message) {
        log.debug("\n");
        log.debug("channelId:" + ctx.channel().id());
        // 判断是否正确握手
        if (!this.handShaker.isHandshakeComplete()) {
            try {
                this.handShaker.finishHandshake(ctx.channel(), (FullHttpResponse) message);
                log.debug("websocket Handshake 完成!");
                this.handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException e) {
                log.debug("websocket连接失败!");
                this.handshakeFuture.setFailure(e);
            }
            return;
        }
        // 握手失败响应
        if (message instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) message;
            log.error("握手失败！code:{},msg:{}", response.status(), response.content().toString(CharsetUtil.UTF_8));
        }
        WebSocketFrame frame = (WebSocketFrame) message;
        // 消息处理
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            log.debug("收到消息: " + textFrame.text());
        }
        if (frame instanceof PongWebSocketFrame) {
            log.debug("pong消息");
        }
        if (frame instanceof CloseWebSocketFrame) {
            log.debug("服务器主动关闭连接");
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug("\n");
        log.debug("连接断开");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("\n");
        log.debug("握手开始,channelId：{}", ctx.channel().id());
        handShaker = WebSocketClientHandshakerFactory.newHandshaker(new URI("ws://" + WebsocketClient.connectedIp + ":" + WebsocketClient.connectedPort + "/ws"), WebSocketVersion.V13, null, false, new DefaultHttpHeaders());
        handShaker.handshake(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.debug("超时事件时触发");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 当我们长时间没有给服务器发消息时，发送ping消息，告诉服务器我们还活跃
            if (event.state().equals(IdleState.WRITER_IDLE)) {
                log.debug("发送心跳");
                ctx.writeAndFlush(new PingWebSocketFrame());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
