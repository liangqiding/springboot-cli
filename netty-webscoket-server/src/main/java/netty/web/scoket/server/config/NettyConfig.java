package netty.web.scoket.server.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import netty.web.scoket.server.handler.CustomChannelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.InetSocketAddress;

/**
 * <p>netty配置信息</p>
 */
@Configuration("nettyConf")
@PropertySource(value = "classpath:config/netty.yml", factory = YamlPropertySourceFactory.class)
@Data
public class NettyConfig {
    @Value("${netty.boss.thread.count}")
    private int bossCount;

    @Value("${netty.worker.thread.count}")
    private int workerCount;

    @Value("${netty.client.thread.count}")
    private int clientCount;

    @Value("${netty.tcp.port}")
    private int tcpPort;

    @Value("${netty.so.keepalive}")
    private boolean keepAlive;

    @Value("${netty.so.backlog}")
    private int backlog;

    /**
     * 定义数据编解码操作
     */
    @Autowired
    private CustomChannelInitializer customChannelInitializer;

    @Bean
    public ServerBootstrap bootstrap() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup(), workerGroup()).channel(NioServerSocketChannel.class)
                .childHandler(customChannelInitializer);
        bootstrap.option(ChannelOption.SO_BACKLOG, backlog); // 缓冲区
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, keepAlive); // ChannelOption对象设置TCP套接字的参数，非必须步骤
        return bootstrap;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }
}
