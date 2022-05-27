package com.springboot.cli.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 使用 @Async开启异步执行
 *
 * @author ding
 */
@Component
@Slf4j
public class AsyncTask {

    @Async("testExecutor")
    public void run() {
        log.info("当前线程名称为：{}", Thread.currentThread().getName());
    }
}
