package com.netty.client.handler;

import com.netty.client.procotol.MessageBuf;
import com.netty.client.utils.MessageBuilder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttMessage;
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
public class MessageHandler extends SimpleChannelInboundHandler<MessageBuf.Message> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageBuf.Message message) throws Exception {
        log.debug("\n");
        log.debug("channelId:" + ctx.channel().id());
        log.debug("消息类型:{}", message.getPackType().name());
        switch (message.getPackType()) {
            case LOGIN_RESP:
                log.debug("收到登录回复\n{}", message.getLoginResponse());
                // 回复客户端
                break;
            case MESSAGE_RESP:
                log.debug("收到消息回复\n{}", message.getMessageResponse());
                // 回复客户端
                break;
            default:
                log.error("不支持的消息类型");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug("\n");
        log.debug("开始连接");
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
        if (evt instanceof IdleStateEvent) {
           // 发送心跳包给服务器
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
