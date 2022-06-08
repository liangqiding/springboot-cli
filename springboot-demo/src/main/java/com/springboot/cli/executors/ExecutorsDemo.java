package com.springboot.cli.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExecutorsDemo {

    public static void print(String msg) {
        System.out.println(Thread.currentThread().getName() + ":" + msg);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newWorkStealingPool(4);
        executorService.execute(() -> ExecutorsDemo.print("任务1"));
        executorService.execute(() -> ExecutorsDemo.print("任务2"));
        executorService.execute(() -> ExecutorsDemo.print("任务3"));
        executorService.execute(() -> ExecutorsDemo.print("任务4"));
        // 由于是线程是抢占式，必须保持程序不终止，否则可能导致线程抢不到任务程序就终止了
        while (true) {}
    }
}
