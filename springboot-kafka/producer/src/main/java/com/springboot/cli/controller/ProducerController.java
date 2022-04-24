package com.springboot.cli.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.cli.KafkaProducer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaProducer kafkaProducer;

    /**
     * 发送消息
     */
    @RequestMapping("/send")
    public String send(String topic, String message) {
        kafkaProducer.send(topic, message);
        return "success";
    }

    /**
     * 发送消息
     */
    @PostMapping("/send")
    public String sendJson(@RequestBody JSONObject data) {
        String topic = data.getString("topic");
        JSONObject message = data.getJSONObject("message");
        kafkaProducer.send(topic, message.toJSONString());
        return "success";
    }
}
