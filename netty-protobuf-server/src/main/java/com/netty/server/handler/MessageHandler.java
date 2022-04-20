package com.netty.server.handler;

import com.netty.server.protocol.MessageBuf;
import com.netty.server.store.ChannelStore;
import com.netty.server.utils.MessageBuilder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Protobuf 消息处理,单例启动
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
            case LOGIN_REQ:
                log.debug("收到登录消息\n{}", message.getLoginRequest());
                // 回复客户端
                ctx.writeAndFlush(MessageBuilder.newLoginResp("login successfully", 200));
                break;
            case MESSAGE_REQ:
                log.debug("收到普通消息{}", message.getMessageRequest());
                // 回复客户端
                ctx.writeAndFlush(MessageBuilder.newMessageResp(message.getMessageRequest().getMessageId(), "ok", 200));
                break;
            default:
                log.error("不支持的消息类型");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug("\n");
        log.debug("断开连接");
        ChannelStore.closeAndClean(ctx);
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
