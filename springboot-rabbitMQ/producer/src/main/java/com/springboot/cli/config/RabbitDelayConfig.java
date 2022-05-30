package com.springboot.cli.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延迟队列
 *
 * @author ding
 */
@Configuration
public class RabbitDelayConfig {

    public final static String DELAY_EXCHANGE = "delay.exchange";

    public final static String DELAY_QUEUE = "delay.queue";

    /**
     * 创建延时交换机
     */
    @Bean
    public DirectExchange delayExchange() {
        return ExchangeBuilder
                .directExchange(DELAY_EXCHANGE)
                // 开启延时
                .delayed()
                // 开启持久化
                .durable(true)
                .build();
    }

    /**
     * 队列
     */
    @Bean
    public Queue delayQueue() {
        return new Queue(DELAY_QUEUE);
    }

    /**
     * 交换机绑定队列
     */
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delay");
    }

}