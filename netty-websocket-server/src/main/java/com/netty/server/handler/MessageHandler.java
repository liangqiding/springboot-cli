package com.netty.server.handler;

import com.netty.server.store.ChannelStore;
import com.netty.server.store.WebSocketSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 消息处理,单例启动
 *
 * @author qiding
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class MessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private final WebsocketMessageHandler websocketHandler;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            websocketHandler.handleHttpRequest(ctx, (FullHttpRequest) msg);
            log.debug("\n");
            log.debug("客户端{}握手成功!", ctx.channel().id());
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        log.debug("\n");
        log.debug("channelId:" + ctx.channel().id());
        websocketHandler.handleWebSocketFrame(ctx, frame);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug("\n");
        log.debug("断开连接");
        // 释放缓存
        ChannelStore.closeAndClean(ctx);
        WebSocketSession.clear(ctx.channel().id());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("\n");
        log.debug("成功建立连接,channelId：{}", ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.debug("心跳事件时触发");
    }
}
