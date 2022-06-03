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
        rabbitProducer.sendDirect("直通消息9527！");
    }

    @Test
    void sendFanout() {
        rabbitProducer.sendFanout("分裂消息6666！");
    }

    @Test
    void sendAndExpire() {
        rabbitProducer.sendAndExpire("晚上10点老地方不见不散！该消息有效期10秒");
    }

    @Test
    void sendDelay() {
        rabbitProducer.sendDelay("有内鬼，终止交易~~", 5000);
    }


    @Test
    void sendTopic() {
        rabbitProducer.sendTopic("放学别走!", "test123.topic.test456");
    }
}
