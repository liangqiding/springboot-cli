package com.springboot.cli.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置
 *
 * @author ding
 */
@Configuration
public class RabbitFanoutConfig {

    public final static String FANOUT_EXCHANGE = "fanout.exchange";

    public final static String TEST_QUEUE = "fanout.queue";

    /**
     * 创建Fanout Exchange交换机也叫通配符交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
    }

    /**
     * 创建队列
     */
    @Bean
    public Queue fanoutQueue() {
        return new Queue(TEST_QUEUE);
    }

    /**
     * 确定绑定关系,队列和交换机绑定
     */
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

}