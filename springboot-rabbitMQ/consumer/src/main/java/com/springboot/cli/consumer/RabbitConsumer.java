package com.springboot.cli.consumer;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
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

    @RabbitListener(queues = "delay.queue")
    public void consumer1(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者1-队列索引：{},接收到消息：{}", tag, message);
        try {
            // 接收成功
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("发生异常，消息签收失败");
            // 第三个参数 requeue = true为将消息重返当前消息队列,还可以重新发送给消费者
            channel.basicNack(tag, false, true);
        }
    }

    @RabbitListener(queuesToDeclare = @Queue("direct.queue"))
    public void consumer2(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者2-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queuesToDeclare = @Queue("fanout.queue"))
    public void consumer3(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者3-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queuesToDeclare = @Queue("topic.queue.one"))
    public void consumer4(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者4-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queuesToDeclare = @Queue("topic.queue.two"))
    public void consumer5(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者5-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queuesToDeclare = @Queue("dead.queue"))
    public void consumer6(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("消费者6-队列索引：{},接收到消息：{}", tag, message);
        channel.basicAck(tag, false);
    }


}
