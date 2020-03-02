package com.concurrent;

import java.util.concurrent.CountDownLatch;

public class TestLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);

        latch.await();
    }
}
