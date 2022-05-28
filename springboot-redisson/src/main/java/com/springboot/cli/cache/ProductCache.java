package com.springboot.cli.cache;

import com.springboot.cli.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 缓存操作,以产品缓存为例
 *
 * @author ding
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductCache {

    private final RedissonClient redissonClient;

    /**
     * 存储桶形式存放
     */
    public void setBucket(String key, Product product) {
        redissonClient.getBucket(key).set(product);
    }

    /**
     * 存储桶形式存放
     *
     * @param timeout 超时时间
     */
    public void setBucket(String key, Product product, long timeout) {
        redissonClient.getBucket(key).set(product, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取值
     */
    public Product getBucket(String key) {
        RBucket<Product> product = redissonClient.getBucket(key);
        return product.get();
    }

    /**
     * 以map的形式存放
     *
     * @param timeout 超时时间
     */
    public void putMapCache(Long productId, Product product, long timeout) {
        redissonClient.getMapCache("productMap").put(productId, product, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取map中的值
     */
    public Product getMapByKey(Long productId) {
        RMapCache<Long, Product> productMap = redissonClient.getMapCache("productMap");
        return productMap.get(productId);
    }


    /**
     * 以set的形式存放
     *
     * @param timeout 超时时间
     */
    public void addSetCache(Product product, long timeout) {
        redissonClient.getSetCache("productSet").add(product, timeout, TimeUnit.SECONDS);
    }

    /**
     * 打印set集合
     */
    public void printSet() {
        RSetCache<Product> product = redissonClient.getSetCache("product");
        product.iterator()
                .forEachRemaining(p -> log.info("读取到:{}", p));
    }

    /**
     * 实现分布式锁
     */
    public void lockTest() {
        RLock lock = redissonClient.getLock("productId");
        lock.lock();
        try {
            // 执行业务代码
            log.info("执行业务代码");
        } finally {
            lock.unlock();
        }
    }
}
