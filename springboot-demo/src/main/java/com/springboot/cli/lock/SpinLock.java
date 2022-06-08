package com.springboot.cli.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 *
 * @author ding
 */
public class SpinLock {

    private static final AtomicReference<Thread> CAS = new AtomicReference<>();

    public static void lock() {
        Thread current = Thread.currentThread();
        // 利用CAS，compare比较CAS中值是否为空，为空则把值更新为新的线程并返回true,否则一直循环运行
        while (!CAS.compareAndSet(null, current)) {
        }
        // DO nothing
        System.out.println(Thread.currentThread().getName() + "获得锁");
    }

    public static void unlock() {
        // 解锁也很简单，compare比较当前线程是否拥有锁，拥有则把CAS中的值重新设空即可
        Thread current = Thread.currentThread();
        CAS.compareAndSet(current, null);
    }

}
