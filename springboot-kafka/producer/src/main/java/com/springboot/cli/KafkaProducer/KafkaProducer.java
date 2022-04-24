package com.springboot.cli.KafkaProducer;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送json消息
     *
     * @param topic 频道
     * @param message  消息
     */
    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
        log.info("send success");
    }

}
