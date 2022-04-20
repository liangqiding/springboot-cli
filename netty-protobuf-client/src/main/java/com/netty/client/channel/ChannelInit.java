package com.netty.client.channel;

import com.netty.client.handler.MessageHandler;
import com.netty.client.protocol.MessageBuf;
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
                // 每隔5s的时间触发一次userEventTriggered的方法，并且指定IdleState的状态位是WRITER_IDLE,事件触发给服务器发送ping消息
                .addLast("idle", new IdleStateHandler(0, 60, 0, TimeUnit.SECONDS))
                // 添加编码、解码器
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(MessageBuf.Message.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                // 添加消息处理器
                .addLast("messageHandler", messageHandler);
    }

}


