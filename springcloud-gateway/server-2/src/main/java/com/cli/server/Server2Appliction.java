package com.cli.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Server2Appliction {

    public static void main(String[] args) {
        SpringApplication.run(Server2Appliction.class, args);
    }

}
