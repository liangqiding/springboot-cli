package com.springboot.cli.demo;

/**
 * 直接创建Thread对象新建线程
 *
 * @author ding
 */
public class ThreadTest {

    public static void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "执行了");
            }
        }).start();
    }

    public static void main(String[] args) {
        start();
        start();
    }
}
