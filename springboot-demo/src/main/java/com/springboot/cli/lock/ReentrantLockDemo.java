package com.springboot.cli.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 悲观锁
 * @author ding
 */
public class ReentrantLockDemo {

    /**
     * true 表示公平锁
     */
    private static final Lock LOCK = new ReentrantLock(true);

    /**
     * 库存
     */
    static int ticketSum = 100;

    public static void sell() {
        for (int i = 0; i < 100; i++) {
            LOCK.lock();
            try {
                if (ticketSum > 0) {
                    // 模拟业务处理时间
                    Thread.sleep(10);
                    ticketSum--;
                    System.out.println(Thread.currentThread().getName() + "卖票成功，当前剩余票数:" + ticketSum);
                }
            } catch (InterruptedException ignored) {
            } finally {
                LOCK.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 模拟4个线程卖票
        executorService.execute(() -> ReentrantLockDemo.sell());
        executorService.execute(() -> ReentrantLockDemo.sell());
        executorService.execute(() -> ReentrantLockDemo.sell());
        executorService.execute(() -> ReentrantLockDemo.sell());
    }
}
