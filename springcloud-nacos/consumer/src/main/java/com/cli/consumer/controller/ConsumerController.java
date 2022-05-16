package com.cli.consumer.controller;

import com.cli.consumer.openfeign.ProviderApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ConsumerController {

    private final ProviderApi providerApi;

    @GetMapping("/test")
    public String get() {
        // 从提供者中获取数据
        ResponseEntity<String> stringResponseEntity = providerApi.get();
        log.info("从提供者中获取数据：{}", stringResponseEntity);
        return stringResponseEntity.getBody();
    }
}
