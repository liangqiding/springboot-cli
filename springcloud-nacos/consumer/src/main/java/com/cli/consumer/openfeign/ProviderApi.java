package com.cli.consumer.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lqd
 */
@FeignClient(value = "provider")
public interface ProviderApi {
    /**
     * @return ``
     */
    @GetMapping("/get")
    ResponseEntity<String> get();
}
