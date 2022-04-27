package com.springboot.cli;

import com.springboot.cli.producer.RabbitProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ProducerTest {

    @Autowired
    RabbitProducer rabbitProducer;

    @Test
    void sendDirect() {
        rabbitProducer.sendDirect("直通消息发送测试！");
    }

    @Test
    void sendFanout() {
        rabbitProducer.sendFanout("分裂消息发送测试！");
    }

    @Test
    void sendDelay() {
        rabbitProducer.sendDelay("延迟消息发送测试！", 5000);
    }

    @Test
    void sendAndExpire() {
        rabbitProducer.sendAndExpire("消息发送测试！该消息有效期5秒", 5000);
    }

    @Test
    void sendTopic() {
        rabbitProducer.sendTopic("通配消息发送测试", "test123.topic.test456");
    }
}
