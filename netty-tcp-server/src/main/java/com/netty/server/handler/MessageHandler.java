package com.netty.server.handler;

import com.netty.server.store.ChannelStore;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
        log.debug("收到消息:{}", message);
        // 判断是否未登录
        if (!ChannelStore.isAuth(ctx)) {
            // 这里登录逻辑自行实现，我这里为了演示把第一次发送的消息作为客户端ID
            String clientId = message.trim();
            ChannelStore.bind(ctx, clientId);
            log.debug("登录成功");
            ctx.writeAndFlush("login successfully");
            return;
        }
        // 回复客户端
        ctx.writeAndFlush("ok");
    }

    /**
     * 指定客户端发送
     *
     * @param clientId 其它已成功登录的客户端
     * @param message  消息
     */
    public void sendByClientId(String clientId, String message) {
        Channel channel = ChannelStore.getChannel(clientId);
        channel.writeAndFlush(message);
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
