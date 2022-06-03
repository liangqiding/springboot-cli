package com.springboot.cli.demo;

/**
 * @author ding
 */
public class ThreadDemo extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "执行了");
    }

    public static void main(String[] args) {
        ThreadDemo thread0 = new ThreadDemo();
        ThreadDemo thread1 = new ThreadDemo();
        thread0.start();
        thread1.start();
    }
}
