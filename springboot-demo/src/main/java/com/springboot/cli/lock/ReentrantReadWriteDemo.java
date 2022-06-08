package com.springboot.cli.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 * @author ding
 */
public class ReentrantReadWriteDemo {

    /**
     * 创建读写锁
     */
    private static final ReadWriteLock RW_LOCK = new ReentrantReadWriteLock();

    /**
     * 库存票数
     */
    static int ticketSum = 100;

    /**
     * 获取库存
     */
    public static int getSum() {
        RW_LOCK.readLock().lock();
        try {
            return ticketSum;
        } finally {
            RW_LOCK.readLock().unlock();
        }
    }

    /**
     * 设置库存
     */
    public static int setSum() {
        RW_LOCK.writeLock().lock();
        try {
            if (ticketSum > 0) {
                ticketSum--;
            }
            return ticketSum;
        } finally {
            RW_LOCK.writeLock().unlock();
        }
    }

    /**
     * 卖票
     */
    public static void sell() {
        for (int i = 0; i < 100; i++) {
            try {
                int sum = setSum();
                if (sum <= 0) {
                    System.out.println("卖完啦！");
                    break;
                }
                // 模拟业务处理时间
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + "卖票成功，当前剩余票数:" + sum);

            } catch (InterruptedException ignored) {
            }

        }
    }

    /**
     * 打印剩余票数，模拟用户查询票数
     */
    public static void print() {
        while (true) {
            try {
                System.out.println("用户查询票数，当前剩余票数:" + getSum());
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 模拟4个线程卖票
        executorService.execute(() -> ReentrantReadWriteDemo.sell());
        executorService.execute(() -> ReentrantReadWriteDemo.sell());
        executorService.execute(() -> ReentrantReadWriteDemo.sell());
        executorService.execute(() -> ReentrantReadWriteDemo.sell());
        // 模拟1个线程查询剩余票数
        executorService.execute(() -> ReentrantReadWriteDemo.print());
    }

}
