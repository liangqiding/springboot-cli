package com.springboot.cli.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 配置我们的线程池
 *
 * @author ding
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig implements AsyncConfigurer {

    /**
     * 设置核心线程数
     */
    private static final int CORE_POOL_SIZE = 4;

    /**
     * 设置最大线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
     */
    private static final int MAX_POOL_SIZE = 8;

    /**
     * 设置缓冲队列大小
     */
    private static final int QUEUE_CAPACITY = 100;

    /**
     * 设置线程的最大空闲时间，超过了核心线程数之外的线程，在空闲时间到达之后会被销毁
     */
    private static final int KEEP_ALIVE_SECONDS = 60;

    /**
     * 设置线程名字的前缀，设置好了之后可以方便我们定位处理任务所在的线程池
     */
    private static final String NAME_PREFIX = "自定义任务线程";

    @Bean(name = "testExecutor")
    public Executor asyncServiceExecutor() {
        log.info("开启SpringBoot的线程池！");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setThreadNamePrefix(NAME_PREFIX);
        // 设置拒绝策略：当线程池达到最大线程数时，如何处理新任务
        // CALLER_RUNS：在添加到线程池失败时会由主线程自己来执行这个任务，
        // 当线程池没有处理能力的时候，该策略会直接在execute方法的调用线程中运行被拒绝的任务；如果执行程序已被关闭，则会丢弃该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return asyncServiceExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error(String.format("执行异步任务'%s'", method), ex);
    }
}


