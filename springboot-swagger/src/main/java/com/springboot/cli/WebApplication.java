package com.springboot.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

/**
 * @author ding
 */
@SpringBootApplication
@Slf4j
public class WebApplication implements ApplicationRunner {

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        log.info("启动完成");
        log.info("swagger3访问地址：http://{}:{}/swagger-ui/index.html#/", "127.0.0.1", port);
        log.info("swagger3访问地址：http://{}:{}/swagger-ui/index.html#/", ip, port);
    }
}
