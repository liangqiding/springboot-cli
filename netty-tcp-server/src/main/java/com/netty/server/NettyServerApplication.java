package com.netty.server;


import com.netty.server.server.TcpServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author ding
 */
@SpringBootApplication
@RequiredArgsConstructor
public class NettyServerApplication implements ApplicationRunner {

    private final TcpServer tcpServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        tcpServer.start();
    }
}
