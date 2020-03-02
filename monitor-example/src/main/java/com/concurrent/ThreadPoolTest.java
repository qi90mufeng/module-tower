package com.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池拒绝策略
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        //policy();
    }

    private static void policy() {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        try{
            for (int i = 1; i <= 6; i++) {
                threadPool.execute(()-> {
                    try {
                        Thread.sleep(2L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t" + "办理业务");
                });
            }
        }catch (Exception e){

        }
    }
}
