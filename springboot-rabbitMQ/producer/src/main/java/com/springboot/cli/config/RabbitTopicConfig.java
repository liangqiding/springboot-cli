package com.springboot.cli.config;

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

    public final static String TOPIC_EXCHANGE = "topic.exchange";
    public final static String TOPIC_QUEUE_ONE = "topic.queue.one";
    public final static String TOPIC_QUEUE_TWO = "topic.queue.two";

    /**
     * 创建Topic Exchange交换机也叫模糊匹配交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, true, false);
    }

    /**
     * 创建队列 1
     */
    @Bean
    public Queue topicOneQueue() {
        return new Queue(TOPIC_QUEUE_ONE);
    }

    /**
     * 创建队列 2
     */
    @Bean
    public Queue topicTwoQueue() {
        return new Queue(TOPIC_QUEUE_TWO);
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