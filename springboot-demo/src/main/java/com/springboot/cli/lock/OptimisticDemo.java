package com.springboot.cli.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 乐观锁
 *
 * @author ding
 */
public class OptimisticDemo {

    /**
     * 用原子类定义变量
     * 库存
     */
    static AtomicInteger ticketSum = new AtomicInteger(100);

    public static void sell() {
        for (int i = 0; i < 100; i++) {
            try {
                if (ticketSum.get() > 0) {
                    // 模拟业务处理时间
                    Thread.sleep(10);
                    int now = ticketSum.addAndGet(-1);
                    System.out.println(Thread.currentThread().getName() + "卖票成功，当前剩余票数:" + now);
                }
            } catch (InterruptedException ignored) {
            }

        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 模拟4个线程卖票
        executorService.execute(() -> OptimisticDemo.sell());
        executorService.execute(() -> OptimisticDemo.sell());
        executorService.execute(() -> OptimisticDemo.sell());
        executorService.execute(() -> OptimisticDemo.sell());
    }
}
