package com.springboot.cli.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 临时消息队列配置
 *
 * @author ding
 */
@Configuration
public class RabbitTtlDirectConfig {

    public final static String DIRECT_TTL_EXCHANGE = "directTtl.exchange";

    public final static String DIRECT_TTL_QUEUE = "direct.ttl.queue";

    /**
     * 创建direct Exchange交换机也叫完全匹配交换机
     */
    @Bean
    public DirectExchange directTtlExchange() {
        return new DirectExchange(DIRECT_TTL_EXCHANGE, true, false);
    }

    /**
     * 会过期的队列
     */
    @Bean
    public Queue directTtlQueue() {
        // .ttl()设置队列的过期时间为10秒
        return QueueBuilder.durable(DIRECT_TTL_QUEUE).ttl(10000).build();
    }

    @Bean
    public Binding directTtlBinding() {
        return BindingBuilder.bind(directTtlQueue()).to(directTtlExchange()).with("direct.ttl");
    }

}