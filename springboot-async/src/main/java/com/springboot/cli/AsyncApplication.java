package com.springboot.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ding
 */
@SpringBootApplication
@Slf4j
public class AsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class, args);
    }

}
