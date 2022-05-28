package com.springboot.cli.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 * Redisson 核心配置
 * 更多配置参考 https://github.com/redisson/redisson/wiki/2.-Configuration
 * @author ding
 */
@Configuration
@ComponentScan
@EnableCaching
@RequiredArgsConstructor
public class RedissonConfig {

    @Resource
    private RedissonProperties redissonProperties;

    /**
     * 单机配置
     */
    @Bean
    @ConditionalOnExpression("'${redisson.cluster}' == 'false'")
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.setTransportMode(redissonProperties.getUseEpoll() ? TransportMode.EPOLL : TransportMode.NIO)
                .setCodec(JsonJacksonCodec.INSTANCE)
                .useSingleServer()
                .setPassword(redissonProperties.getPassword())
                .setDatabase(redissonProperties.getDatabase())
                .setAddress(redissonProperties.getAddress()[0]);
        return Redisson.create(config);
    }


    /**
     * 集群配置
     */
    @Bean
    @ConditionalOnExpression("'${redisson.cluster}' == 'true'")
    public RedissonClient getClusterRedisson() {
        Config config = new Config();
        config.setTransportMode(redissonProperties.getUseEpoll() ? TransportMode.EPOLL : TransportMode.NIO)
                .setCodec(JsonJacksonCodec.INSTANCE)
                .useClusterServers()
                // 集群状态扫描间隔时间，单位是毫秒
                .setScanInterval(3000)
                .setPassword(redissonProperties.getPassword())
                .addNodeAddress(redissonProperties.getAddress());
        return Redisson.create(config);
    }


}
