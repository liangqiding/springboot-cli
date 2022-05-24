package com.cli.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class ConsumerServer {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerServer.class, args);
    }

}
