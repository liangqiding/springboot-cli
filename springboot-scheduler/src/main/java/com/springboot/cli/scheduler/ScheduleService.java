package com.springboot.cli.scheduler;

/**
 * 定时任务
 *
 * @author qiding
 */
public interface ScheduleService {

    /**
     * 每秒
     */
    default void everySecond(){}

    /**
     * 每分钟
     */
    default void everyMinute(){}

    /**
     * 每五分钟
     */
    default void everyFiveMinute(){}

    /**
     * 每小时
     */
    default void everyHour(){}

    /**
     * 每天上午8点
     */
    default void everyDayEightClock(){}

}
