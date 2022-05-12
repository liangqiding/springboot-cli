package com.cli.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GatewayAppliction {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAppliction.class, args);
    }

}
