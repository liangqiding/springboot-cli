package com.springboot.cli.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 实现 Callable 接口
 * 带执行回调
 *
 * @author ding
 */
public class CallableDemo implements Callable<String> {

    @Override
    public String call() {
        System.out.println(Thread.currentThread().getName() + "执行了");
        return "执行成功!";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableDemo mc = new CallableDemo();
        FutureTask<String> ft0 = new FutureTask<>(mc);
        FutureTask<String> ft1 = new FutureTask<>(mc);
        Thread thread0 = new Thread(ft0);
        Thread thread1 = new Thread(ft1);
        thread0.start();
        thread1.start();
        System.out.println(ft0.get());
        System.out.println(ft1.get());
    }
}
