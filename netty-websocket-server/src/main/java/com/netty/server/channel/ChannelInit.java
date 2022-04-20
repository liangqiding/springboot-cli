package com.netty.server.channel;

import com.netty.server.handler.MessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
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
                // 心跳时间
                .addLast("idle", new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS))
                // 对http协议的支持.
                .addLast(new HttpServerCodec())
                // 对大数据流的支持
                .addLast(new ChunkedWriteHandler())
                // 聚合 Http 将多个requestLine、requestHeader、messageBody信息转化成单一的request或者response对象
                .addLast(new HttpObjectAggregator(8192))
                // 聚合 websocket 的数据帧，因为客户端可能分段向服务器端发送数据
                .addLast(new WebSocketFrameAggregator(1024 * 62))
                // 添加消息处理器
                .addLast("messageHandler", messageHandler);
    }

}


