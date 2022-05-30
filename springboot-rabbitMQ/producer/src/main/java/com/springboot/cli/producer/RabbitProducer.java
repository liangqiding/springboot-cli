package com.springboot.cli.producer;

import com.springboot.cli.config.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


/**
 * 消息提供者
 *
 * @author ding
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitProducer {


    private final RabbitTemplate rabbitTemplate;

    /**
     * 直接模式发送消息
     *
     * @param message 发送的信息
     */
    public void sendDirect(Object message) {
        CorrelationData correlationData = this.getCorrelationData();
        rabbitTemplate.convertAndSend(RabbitDirectConfig.DIRECT_QUEUE, message, correlationData);
    }

    /**
     * 分裂模式发送消息
     *
     * @param message 发送的信息
     */
    public void sendFanout(Object message) {
        rabbitTemplate.convertAndSend(RabbitFanoutConfig.FANOUT_EXCHANGE, "", message);
    }


    /**
     * 主题模式发送消息
     *
     * @param message    发送的信息
     * @param routingKey 匹配的队列名
     */
    public void sendTopic(Object message, String routingKey) {
        rabbitTemplate.convertAndSend(RabbitTopicConfig.TOPIC_EXCHANGE, routingKey, message);
    }


    /**
     * 发送延迟消息
     *
     * @param message 发送的信息
     * @param delay   延迟时间
     */
    public void sendDelay(String message, int delay) {
        Message messageBuild = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setHeader("x-delay", delay)
                .build();
        rabbitTemplate.convertAndSend(RabbitDelayConfig.DELAY_EXCHANGE, RabbitDelayConfig.DELAY_QUEUE, messageBuild, this.getCorrelationData());
    }

    /**
     * 发送消息并设置过期时间
     */
    public void sendAndExpire(String message) {
        // 设置消息过期时间
        Message messageBuild = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setExpiration("10000")
                .build();
        rabbitTemplate.convertAndSend(RabbitTtlDirectConfig.DIRECT_TTL_EXCHANGE, RabbitTtlDirectConfig.DIRECT_TTL_QUEUE, messageBuild, this.getCorrelationData());
    }

    /**
     * 生成消息标识
     */
    private CorrelationData getCorrelationData() {
        String messageId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(messageId);
        return correlationData;
    }

}
