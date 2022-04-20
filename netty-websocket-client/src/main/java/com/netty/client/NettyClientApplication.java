package com.netty.client;


import com.netty.client.server.WebsocketClient;
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
public class NettyClientApplication implements ApplicationRunner {

    private final WebsocketClient tcpClient;

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args){
        tcpClient.start();
    }
}
