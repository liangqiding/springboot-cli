package com.netty.server.channel;

import com.netty.server.handler.MessageHandler;
import com.netty.server.protocol.MessageBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
                // 心跳时间
                .addLast("idle", new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS))
                // 添加编码、解码器
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(MessageBuf.Message.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                // 添加消息处理器
                .addLast("messageHandler", messageHandler);
    }
}
