package com.springboot.cli.KafkaConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    /**
     * 消费者配置，kafka.topic为监听的topic，kafka.group为消费分组，可在yml中修改
     */
    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group}")
    @Async
    public void kafkaListener(ConsumerRecord<String, String> consumerRecord) {
        String value = consumerRecord.value();
        if (log.isInfoEnabled()) {
            log.info("读取到消息：offset {}, value {}", consumerRecord.offset(), value);
        }
        if (null == value) {
            log.error("kafka消费数据为空");
        }
    }

}
