package netty.web.scoket.server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * <p>tcp服务</p>
 */
@Component("tcpServer")
@Slf4j
public class TcpServer {

    @Autowired
    private ServerBootstrap bootstrap;

    @Autowired
    private InetSocketAddress tcpSocketAddress;

    private ChannelFuture serverChannelFuture;

    /**
     * 启动tcp服务监听
     */
    @PostConstruct
    public void start() {
        log.info("TCP Server start at " + tcpSocketAddress.getPort());
        new Thread(() -> {
            try {
                serverChannelFuture = bootstrap.bind(tcpSocketAddress.getPort()).sync();
            } catch (Exception e) {
                log.error("tcp server start failed.", e);
            }
        }).start();
    }

    /**
     * 关闭tcp服务
     */
    @PreDestroy
    public void stop() {
        try {
            if (serverChannelFuture != null) {
                serverChannelFuture.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            log.error("tcp server close failed.", e);
        }
    }


}
