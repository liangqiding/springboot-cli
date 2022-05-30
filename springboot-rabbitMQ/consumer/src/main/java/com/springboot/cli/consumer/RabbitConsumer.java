package com.springboot.cli.consumer;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @RabbitListener(queuesToDeclare = @Queue("direct.queue"))
    public void consumer1(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者1（直通消息）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queuesToDeclare = @Queue("fanout.queue"))
    public void consumer2(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者2（分裂消息）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.ttl.queue"),declare = "true",
            exchange = @Exchange(name = "directTtl.exchange",delayed = "true"),
            key = "true"))
    public void consumer222(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者2（临时消息）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delay.queue"),declare = "true",
            exchange = @Exchange(name = "delay.exchange",delayed = "true"),
            key = "true"))
    public void consumer22(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者3（延时队列）-队列索引：{},接收到消息：{}", tag, message);
        try {
            // 接收成功
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("发生异常，消息签收失败");
            // 第三个参数 requeue = true为将消息重返当前消息队列,还可以重新发送给消费者
            channel.basicNack(tag, false, true);
        }
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic.queue.one",autoDelete = "false",durable = "true"),
            exchange = @Exchange(value = "topic.exchange",type = ExchangeTypes.TOPIC),
            key = "#.topic.#"
    ))
    public void consumer4(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者4（topic模式）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic.queue.two",autoDelete = "false",durable = "true"),
            exchange = @Exchange(value = "topic.exchange",type = ExchangeTypes.TOPIC),
            key = "#.topic.#"
    ))
    public void consumer5(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者5（topic模式）-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queuesToDeclare = @Queue("dead.queue"))
    public void consumer6(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者6-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }


}
