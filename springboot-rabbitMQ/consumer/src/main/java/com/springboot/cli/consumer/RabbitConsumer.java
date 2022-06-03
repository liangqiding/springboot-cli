package com.springboot.cli.consumer;

import com.rabbitmq.client.Channel;
import com.springboot.cli.mq.RabbitDefine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消费者
 *
 * @author ding
 */
@Component
@Slf4j
public class RabbitConsumer {

    /**
     * 普通消息
     */
    @RabbitListener(queuesToDeclare = @Queue(RabbitDefine.DIRECT_QUEUE))
    public void consumer1(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者1（直通消息）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    /**
     * 分裂消息
     */
    @RabbitListener(queuesToDeclare = @Queue(RabbitDefine.FANOUT_QUEUE))
    public void consumer2(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者2（分裂消息）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    /**
     * 临时消息
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = RabbitDefine.TTL_QUEUE,
                            arguments = {
                                    // 指定一下死信交换机
                                    @Argument(name = "x-dead-letter-exchange", value = RabbitDefine.DEAD_EXCHANGE),
                                    // 指定死信交换机的路由key
                                    @Argument(name = "x-dead-letter-routing-key", value = "dead"),
                                    // 指定队列的过期时间
                                    @Argument(name = "x-message-ttl", value = "10000", type = "java.lang.Long")
                            }),
                    exchange = @Exchange(name = RabbitDefine.TTL_EXCHANGE),
                    key = "test"
            )
    })
    public void consumer3(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者3（临时消息）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    /**
     * 延时消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitDefine.DELAY_QUEUE),
            exchange = @Exchange(name = RabbitDefine.DELAY_EXCHANGE, delayed = "true"),
            key = "delay"))
    public void consumer4(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者4（延时队列）-队列索引：{},接收到消息：{}", tag, message);
        try {
            // 接收成功
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("发生异常，消息签收失败");
            // 第三个参数 requeue = true为将消息重返当前消息队列,还可以重新发送给消费者
            channel.basicNack(tag, false, true);
        }
    }

    /**
     * topic消息1
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitDefine.TOPIC_QUEUE_ONE, autoDelete = "false", durable = "true"),
            exchange = @Exchange(value = RabbitDefine.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = "#.topic.#"
    ))
    public void consumer5(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者5（topic模式one）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    /**
     * topic消息2
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitDefine.TOPIC_QUEUE_TWO, autoDelete = "false", durable = "true"),
            exchange = @Exchange(value = RabbitDefine.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = "#.topic.#"
    ))
    public void consumer6(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者6（topic模式two）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    /**
     * 死信队列消息
     */
    @RabbitListener(queuesToDeclare = @Queue(RabbitDefine.DEAD_QUEUE))
    public void consumer7(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者7（死信队列)-队列索引：{},接收到过期消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

}
