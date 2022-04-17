package com.netty.server.channel;

import com.netty.server.handler.MessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * Netty 通道初始化
 *
 * @author qiding
 */
@Component
@RequiredArgsConstructor
public class ChannelInit extends ChannelInitializer<SocketChannel> {

    private final MessageHandler messageHandler;

    @Override
    protected void initChannel(SocketChannel channel) {
        channel.pipeline()
                // 每隔5s的时间触发一次userEventTriggered的方法，并且指定IdleState的状态位是WRITER_IDLE
                .addLast("idle", new IdleStateHandler(0, 60, 0, TimeUnit.SECONDS))
                // 添加解码器
                .addLast(new StringDecoder())
                // 添加编码器
                .addLast(new StringEncoder())
                // 添加消息处理器
                .addLast("messageHandler", messageHandler);
    }

}


