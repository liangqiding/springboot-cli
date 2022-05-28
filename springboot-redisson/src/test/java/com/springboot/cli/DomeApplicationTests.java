package com.springboot.cli;

import com.springboot.cli.cache.ProductCache;
import com.springboot.cli.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
class DomeApplicationTests {

    @Autowired
    ProductCache productCache;

    @Test
    void setBucket() {
        Product product = new Product()
                .setProductId(1L)
                .setProductName("F12战斗鸡")
                .setCreatedDate(new Date());
        productCache.setBucket("key001", product);
    }

    @Test
    void getBucket() {
        Product p = productCache.getBucket("key001");
        log.info("获取到数据:{}", p);
    }

    @Test
    void putMapCache() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product()
                    .setProductId(1L + i)
                    .setProductName("F12战斗鸡")
                    .setCreatedDate(new Date());
            productCache.putMapCache(1L + i, product, 3600);
        }
    }

    @Test
    void getMapByKey() {
        Product p = productCache.getMapByKey(1L);
        log.info("获取到数据:{}", p);
    }

    @Test
    void addSet() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product()
                    .setProductId(1L + i)
                    .setProductName("F12战斗鸡")
                    .setCreatedDate(new Date());
            productCache.addSetCache(product, 3600);
        }
    }

    @Test
    void printSet() {
        productCache.printSet();
    }
}
