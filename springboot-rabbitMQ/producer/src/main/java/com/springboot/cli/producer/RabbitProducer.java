package com.springboot.cli.producer;

import com.springboot.cli.config.*;
import com.springboot.cli.mq.RabbitDefine;
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
        rabbitTemplate.convertAndSend(RabbitDefine.DIRECT_QUEUE, message, this.getCorrelationData());
    }

    /**
     * 分裂模式发送消息
     *
     * @param message 发送的信息
     */
    public void sendFanout(Object message) {
        rabbitTemplate.convertAndSend(RabbitDefine.FANOUT_EXCHANGE, "", message);
    }


    /**
     * 主题模式发送消息
     *
     * @param message    发送的信息
     * @param routingKey 匹配的队列名
     */
    public void sendTopic(Object message, String routingKey) {
        rabbitTemplate.convertAndSend(RabbitDefine.TOPIC_EXCHANGE, routingKey, message);
    }


    /**
     * 发送延迟消息
     *
     * @param message 发送的信息
     * @param delay   延迟时间
     */
    public void sendDelay(String message, int delay) {
        rabbitTemplate.convertAndSend(RabbitDefine.DELAY_EXCHANGE, "delay", message, msg -> {
            msg.getMessageProperties().setDelay(delay);
            return msg;
        });
    }

    /**
     * 发送临时消息
     */
    public void sendAndExpire(Object message) {
        rabbitTemplate.convertAndSend(RabbitDefine.TTL_QUEUE, message, this.getCorrelationData());
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
