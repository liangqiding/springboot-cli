package com.springboot.cli.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 统一执行定时任务,减少系统开销
 * {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
 * @author qiding
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleRunner {

    /**
     * 装配所有定时任务
     */
    private final List<ScheduleService> scheduleServiceList;

    /**
     * 每秒
     */
    @Scheduled(cron = "*/1 * * * * ?")
    @Async("scheduleExecutor")
    public void everySecond() {
        scheduleServiceList.forEach(ScheduleService::everySecond);
    }

    /**
     * 每分钟
     */
    @Scheduled(cron = "0 */1 * * * ?")
    @Async("scheduleExecutor")
    public void everyMinute() {
        scheduleServiceList.forEach(ScheduleService::everyMinute);
    }

    /**
     * 每五分钟
     */
    @Scheduled(cron = "0 */5 * * * ?")
    @Async("scheduleExecutor")
    public void everyFiveMinute() {
        scheduleServiceList.forEach(ScheduleService::everyFiveMinute);
    }

    /**
     * 每小时
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    @Async("scheduleExecutor")
    public void everyHour() {
        scheduleServiceList.forEach(ScheduleService::everyHour);
    }

    /**
     * 每天上午8点
     */
    @Scheduled(cron = "0 0 8 * * ?")
    @Async("scheduleExecutor")
    public void everyDayEightClock() {
        scheduleServiceList.forEach(ScheduleService::everyDayEightClock);
    }

}
