package com.springboot.cli.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 *
 * @author ding
 */
public class SpinLockSellDemo {

    private static final AtomicReference<Thread> CAS = new AtomicReference<>();

    /**
     * 库存
     */
    static int ticketSum = 100;

    public static void lock() {
        Thread current = Thread.currentThread();
        // 利用CAS，compare比较CAS中值是否为空，为空则把值更新为新的线程并返回true,否则一直循环运行
        while (!CAS.compareAndSet(null, current)) {
        }
    }

    public static void unlock() {
        // 解锁也很简单，compare比较当前线程是否拥有锁，拥有则把CAS中的值重新设空即可
        Thread current = Thread.currentThread();
        CAS.compareAndSet(current, null);
    }

    /**
     * 卖票
     */
    public static void sell() {
        for (int i = 0; i < 100; i++) {
            try {
                lock();
                try {
                    if (ticketSum > 0) {
                        // 模拟业务处理时间
                        Thread.sleep(10);
                        ticketSum--;
                        System.out.println(Thread.currentThread().getName() + "卖票成功，当前剩余票数:" + ticketSum);
                    }
                } catch (InterruptedException ignored) {
                }
            } finally {
                unlock();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 模拟4个线程卖票
        executorService.execute(() -> SpinLockSellDemo.sell());
        executorService.execute(() -> SpinLockSellDemo.sell());
        executorService.execute(() -> SpinLockSellDemo.sell());
        executorService.execute(() -> SpinLockSellDemo.sell());
    }


}
