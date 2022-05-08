package com.cli.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProviderServer {

    public static void main(String[] args) {
        SpringApplication.run(ProviderServer.class, args);
    }

}
