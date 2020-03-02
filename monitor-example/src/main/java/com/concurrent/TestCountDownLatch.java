package com.concurrent;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++){
            final int tempInt = i;
            new Thread(()->{
                System.out.println(tempInt + "国被消灭");
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println("秦国一统中国");
    }
}
