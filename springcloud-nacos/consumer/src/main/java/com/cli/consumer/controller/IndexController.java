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
public class IndexController {

    private final ProviderApi providerApi;

    @GetMapping("/")
    public String get() {
        ResponseEntity<String> stringResponseEntity = providerApi.get();
        log.info("向提供者获取数据：{}", stringResponseEntity);
        return stringResponseEntity.getBody();
    }
}
