package com.springboot.cli.utils;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> T get(String key, Class<T> c) {
        Object o = redisTemplate.opsForValue().get(key);
        assert o != null;
        return JSON.parseObject(o.toString(), c);
    }

}
