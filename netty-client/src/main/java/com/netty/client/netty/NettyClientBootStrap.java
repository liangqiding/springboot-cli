package com.netty.client.netty;

import com.netty.client.vo.ProtoMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;

/**
 * description :
 *
 * @author : qiDing
 * date: 2021-03-23 14:33
 */
@Component
@Slf4j
public class NettyClientBootStrap {

//    private static final String HOST = "support.agilex.ai";
    private static final String HOST = "localhost";
    private static final int PORT = 6060;
    private static SocketChannel socketChannel = null;
    private static EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    public static void start(EventLoopGroup loopGroup) throws InterruptedException {
        eventLoopGroup = loopGroup;
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(HOST, PORT);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        // 注入netty自带protobuf编解码
                        //   .addLast(new ProtobufVarint32FrameDecoder())
                        //   .addLast(new ProtobufDecoder(ProtoMessage.Message.getDefaultInstance()))
                        //   .addLast(new ProtobufVarint32LengthFieldPrepender())
                        //   .addLast(new ProtobufEncoder())
                        // 限制长度
                        .addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4))
                        .addLast("protobufDecoder", new ProtobufDecoder(ProtoMessage.Message.getDefaultInstance()))
                        .addLast("frameEncoder", new LengthFieldPrepender(4))
                        .addLast("protobufEncoder", new ProtobufEncoder())
                        .addLast(new NettyClientHandler());
            }
        });
        ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel) future.channel();
            log.info("connect server success");
        }
    }

    public static SocketChannel getSocketChannel() {
        return socketChannel;
    }

    @PreDestroy
    public static void destroy() {
        eventLoopGroup.shutdownGracefully();
    }

    public static void close() {
        if (socketChannel != null) {
            socketChannel.close();
        }
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
