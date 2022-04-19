package com.example.mybatis_generation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.example.mybatis_generation.dao")
public class MybatisGenerationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisGenerationApplication.class, args);
    }

}
