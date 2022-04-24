package com.springboot.cli.scheduler.task;

import com.springboot.cli.scheduler.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 执行示例
 *
 * @author qiding
 */
@Component
@Slf4j
public class Test4TaskImpl implements ScheduleService {

    @Override
    public void everyHour() {
        log.info("（每小时）定时任务执行了");
    }
}
