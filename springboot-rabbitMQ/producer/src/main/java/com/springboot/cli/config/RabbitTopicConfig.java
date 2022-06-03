package com.springboot.cli.config;

import com.springboot.cli.mq.RabbitDefine;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topic与direct类型的交换器类似，也是将消息路由到RoutingKey与BindingKey匹配的队列中,
 * 但它不是完全匹配，而是模糊匹配。
 * 规则；#用于匹配一个单词，*用于匹配多个单词（可以是0个）；例如：order.*.current。
 *
 * @author ding
 */
@Configuration
public class RabbitTopicConfig {

    /**
     * 创建Topic Exchange交换机也叫模糊匹配交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder
                .topicExchange(RabbitDefine.TOPIC_EXCHANGE)
                // 开启持久化
                .durable(true)
                .build();
    }

    /**
     * 创建队列 1
     */
    @Bean
    public Queue topicOneQueue() {
        return QueueBuilder
                .durable(RabbitDefine.TOPIC_QUEUE_ONE)
                .build();
    }

    /**
     * 创建队列 2
     */
    @Bean
    public Queue topicTwoQueue() {
        return QueueBuilder
                .durable(RabbitDefine.TOPIC_QUEUE_TWO)
                .build();
    }

    /**
     * 确定绑定关系
     * #用于匹配一个单词 *用于匹配多个单词
     */
    @Bean
    public Binding topicOneBinding(@Qualifier("topicOneQueue") Queue topicQueue, @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("#.queue.one");
    }

    @Bean
    public Binding topicTwoBinding(@Qualifier("topicTwoQueue") Queue topicQueue, @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("#.queue.*");
    }

}