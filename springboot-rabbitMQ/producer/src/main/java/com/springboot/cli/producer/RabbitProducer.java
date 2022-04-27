package com.springboot.cli.producer;

import com.springboot.cli.config.RabbitDeadConfig;
import com.springboot.cli.config.RabbitDirectConfig;
import com.springboot.cli.config.RabbitFanoutConfig;
import com.springboot.cli.config.RabbitTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
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
    public void sendDelay(Object message, int delay) {
        rabbitTemplate.convertAndSend(RabbitDeadConfig.DEAD_EXCHANGE, RabbitDeadConfig.DEAD_QUEUE, message, msg -> {
            msg.getMessageProperties().setHeader("x-delay", delay);
            return msg;
        });
    }

    /**
     * 发送消息并设置过期时间
     *
     * @param expire ms 过期时间
     */
    public void sendAndExpire(Object message, int expire) {
        // 设置消息过期时间
        MessagePostProcessor messagePostProcessor = msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(expire));
            msg.getMessageProperties().setContentEncoding("UTF-8");
            return msg;
        };
        rabbitTemplate.convertAndSend(RabbitDirectConfig.DIRECT_EXCHANGE, RabbitDirectConfig.DIRECT_TTL_QUEUE, message, messagePostProcessor);
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
