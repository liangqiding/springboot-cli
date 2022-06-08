package com.springboot.cli.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * 有了可重入锁之后，破解第一把之后就可以一直持有该锁，直到内层执行完毕
 *
 * @author ding
 */
public class ReentrantLockTest {

    private static final Lock LOCK = new ReentrantLock();

    public static void test() {
        LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "买1");
            LOCK.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "送1");
                LOCK.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "再送");
                } finally {
                    LOCK.unlock();
                }
            } finally {
                LOCK.unlock();
            }
        } finally {
            LOCK.unlock();
        }
    }


    public static void main(String[] args) {
        new Thread(() -> {
            test();
        }, "线程：").start();
    }
}
