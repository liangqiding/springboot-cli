package com.netty.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.timeout.IdleState;
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
public class MessageHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        log.debug("\n");
        log.debug("channelId:" + ctx.channel().id());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug("\n");
        log.debug("开始连接");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("\n");
        log.info("成功建立连接,channelId：{}", ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.debug("心跳事件时触发");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 当我们长时间没有给服务器发消息时，发送ping消息，告诉服务器我们还活跃
            if (event.state().equals(IdleState.WRITER_IDLE)) {
                log.debug("发送心跳");
                ctx.writeAndFlush("ping");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
