package com.netty.client.server;


import com.netty.client.channel.ChannelInit;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;

/**
 * 启动 Broker
 *
 * @author qiding
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TcpClient implements ITcpClient {

    private final ChannelInit channelInit;

    private static final EventLoopGroup WORKER_GROUP = new NioEventLoopGroup();

    private SocketChannel socketChannel;

    private Bootstrap bootstrap;


    @Value("${tcp.client.host}")
    private String clientHost;

    @Value("${tcp.client.port}")
    private Integer clientPort;

    @Value("${tcp.server.host}")
    private String serverHost;

    @Value("${tcp.server.port}")
    private Integer serverPort;

    @Override
    public void start() throws Exception {
        log.info("初始化 MQTT Client ...");
        this.tcpClient();
    }

    @Override
    public void reconnect() throws Exception {
        if (socketChannel != null && socketChannel.isActive()) {
            socketChannel.close();
            this.connect(serverHost, serverPort);
        }

    }

    public void disconnect() {
        if (socketChannel != null && socketChannel.isActive()) {
            socketChannel.close();
        }
    }

    /**
     * Client初始化
     */
    private void tcpClient() {
        try {
            bootstrap = new Bootstrap()
                    .remoteAddress(clientHost, clientPort)
                    .handler(channelInit)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true);
            bootstrap.group(WORKER_GROUP);
            this.connect(serverHost, serverPort);
        } catch (Exception e) {
            e.printStackTrace();
            WORKER_GROUP.shutdownGracefully();
        }
    }

    /**
     * 连接服务器
     */
    public void connect(String ip, Integer port) throws InterruptedException {
        this.disconnect();
        this.serverHost = ip;
        this.serverPort = port;
        ChannelFuture future = bootstrap.connect(serverHost, serverPort).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel) future.channel();
            log.info("connect server success");
        }
    }


    /**
     * 销毁
     */
    @PreDestroy
    @Override
    public void destroy() {
        WORKER_GROUP.shutdownGracefully();
        socketChannel.closeFuture();
    }

    /**
     * 获取频道
     */
    public SocketChannel getSocketChannel() {
        return this.socketChannel;
    }
}
