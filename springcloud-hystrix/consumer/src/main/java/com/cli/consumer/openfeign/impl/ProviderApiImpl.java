package com.cli.consumer.openfeign.impl;


import com.cli.consumer.openfeign.ProviderApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author lqd
 */
@Service
public class ProviderApiImpl implements ProviderApi {

    @Override
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("请求出错啦，进入熔断处理。");
    }
}
