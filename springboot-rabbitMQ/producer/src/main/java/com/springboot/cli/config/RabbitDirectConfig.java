package com.springboot.cli.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息队列配置
 * <p>
 * Direct 类型的交换器由路由规则很简单，它会把消息路由到那些 BindingKey 和 RoutingKey 完全匹配的队列中。
 * Direct Exchange 是 RabbitMQ 默认的交换器模式，也是最简单的模式。它根据 RoutingKey 完全匹配去寻找队列。
 * </p>
 *
 * @author ding
 */
@Configuration
public class RabbitDirectConfig {

    public final static String DIRECT_EXCHANGE = "direct.exchange";

    public final static String DIRECT_QUEUE = "direct.queue";

    public final static String DIRECT_TTL_QUEUE = "direct.ttl.queue";

    /**
     * 创建direct Exchange交换机也叫完全匹配交换机
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    /**
     * 普通队列
     */
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE);
    }


    /**
     * 会过期的队列
     */
    @Bean
    public Queue directTtlQueue() {
        Map<String, Object> args = new HashMap<>(4);
        // 过期时间ms
        args.put("x-message-ttl", 10000);
        // 最大长度5条，超过进入死信队列
        args.put("x-max-length", 5);
        // 指定死信交换机
        args.put("x-dead-letter-exchange", RabbitDeadConfig.DEAD_EXCHANGE);
        // fanout 不用配置
        args.put("x-dead-letter-routing-key", "dead");
        return new Queue(DIRECT_TTL_QUEUE, true, false, false, args);
    }

    /**
     * 交换机绑定队列
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("direct");
    }

    @Bean
    public Binding directTtlBinding() {
        return BindingBuilder.bind(directTtlQueue()).to(directExchange()).with("direct.ttl");
    }

}