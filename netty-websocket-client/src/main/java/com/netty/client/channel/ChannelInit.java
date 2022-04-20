package com.netty.client.channel;

import com.netty.client.handler.MessageHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
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
                // 每隔60s的时间触发一次userEventTriggered的方法，并且指定IdleState的状态位是WRITER_IDLE,事件触发给服务器发送ping消息
                .addLast("idle", new IdleStateHandler(0, 60, 0, TimeUnit.SECONDS))
                // 添加解码器
                .addLast(new HttpClientCodec())
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpObjectAggregator(1024 * 1024 * 10))
                .addLast(new WebSocketFrameAggregator(1024 * 62))
                // 添加消息处理器
                .addLast("messageHandler", messageHandler);
    }

}


