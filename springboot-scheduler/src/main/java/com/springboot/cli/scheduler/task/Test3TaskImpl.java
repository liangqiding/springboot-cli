package com.springboot.cli.scheduler.task;

import com.springboot.cli.scheduler.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 执行示例
 *
 * @author ding
 */
@Component
@Slf4j
public class Test3TaskImpl implements ScheduleService {

    @Override
    public void everyFiveMinute() {
        log.info("（每5分钟）定时任务执行了");
    }

}
