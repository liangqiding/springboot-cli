package com.cli.consumer.openfeign;

import com.cli.consumer.openfeign.impl.ProviderApiImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 利用openfeign 调用消费者的api
 *
 * @author lqd
 */
@FeignClient(value = "provider", fallback = ProviderApiImpl.class)
public interface ProviderApi {
    /**
     * @return ``
     */
    @GetMapping("/get")
    ResponseEntity<String> get();
}
