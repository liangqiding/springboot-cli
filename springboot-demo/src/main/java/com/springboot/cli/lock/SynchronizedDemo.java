package com.springboot.cli.lock;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 方法加锁
 *
 * @author ding
 */
public class SynchronizedDemo {
    /**
     * 库存
     */
    static int ticketSum = 100;

    /**
     * 卖票
     */
    public synchronized static void sell() {
        for (int i = 0; i < 100; i++) {
            try {
                if (ticketSum > 0) {
                    // 模拟业务处理时间
                    Thread.sleep(10);
                    ticketSum--;
                    System.out.println(Thread.currentThread().getName() + "卖票成功，当前剩余票数:" + ticketSum);
                }
            } catch (InterruptedException ignored) {
            }

        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 模拟4个线程卖票
        executorService.execute(() -> SynchronizedDemo.sell());
        executorService.execute(() -> SynchronizedDemo.sell());
        executorService.execute(() -> SynchronizedDemo.sell());
        executorService.execute(() -> SynchronizedDemo.sell());
    }
}
