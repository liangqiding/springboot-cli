package com.springboot.cli.lock;

/**
 * 可重入锁
 * 有了可重入锁之后，破解第一把之后就可以一直持有该锁，直到内层执行完毕
 * @author ding
 */
public class Reentrant {

    public static void test() {
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                System.out.println(Thread.currentThread().getName() + "买1");

                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + "送1");

                    synchronized (o) {
                        System.out.println(Thread.currentThread().getName() + "再送");
                    }
                }
            }

        }, "线程：").start();
    }

    public static void main(String[] args) {
        test();
    }
}
