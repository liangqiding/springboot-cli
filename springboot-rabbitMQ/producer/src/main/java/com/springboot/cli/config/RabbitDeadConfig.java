package com.springboot.cli.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 死信队列，当消息过期时，消息进入死信队列
 *
 * @author ding
 */
@Configuration
public class RabbitDeadConfig {

    /**
     * 定义一个死信交换机
     */
    public final static String DEAD_EXCHANGE = "dead.exchange";

    /**
     * 定义一个死信交队列
     */
    public final static String DEAD_QUEUE = "dead.queue";

    /**
     * 死信交换机
     *
     * @return FanoutExchange
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE, true, false);
    }

    /**
     * 死信队列
     *
     * @return Queue
     */
    @Bean
    public Queue deadQueue() {
        return new Queue(DEAD_QUEUE, true);
    }

    /**
     * 绑定
     *
     * @return Binding
     */
    @Bean
    public Binding deadBinding(@Qualifier("deadExchange") DirectExchange deadExchange, @Qualifier("deadQueue") Queue deadQueue) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with("dead");
    }

}
