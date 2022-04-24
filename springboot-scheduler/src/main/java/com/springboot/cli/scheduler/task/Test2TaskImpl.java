package com.springboot.cli.scheduler.task;

import com.springboot.cli.scheduler.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 *
 * 执行示例
 *
 * @author ding
 */
@Component
@Slf4j
public class Test2TaskImpl implements ScheduleService {

    @Override
    public void everyMinute() {
        log.info("（每分钟）定时任务执行了");
    }

}
