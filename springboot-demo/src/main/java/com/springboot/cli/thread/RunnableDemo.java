package com.springboot.cli.thread;

/**
 * Runnable 开启多线程
 * 需要实现接口中的 run() 方法。
 * @author ding
 */
public class RunnableDemo implements Runnable {

    @Override
    public void run() {
            System.out.println(Thread.currentThread().getName() + "执行了");
    }

    /**
     * 使用 Runnable 实例再创建一个 Thread 实例，然后调用 Thread 实例的 start() 方法来启动线程。
     */
    public static void main(String[] args) {
        RunnableDemo runnable = new RunnableDemo();
        Thread thread0 = new Thread(runnable);
        Thread thread1 = new Thread(runnable);
        thread0.start();
        thread1.start();

    }
}
