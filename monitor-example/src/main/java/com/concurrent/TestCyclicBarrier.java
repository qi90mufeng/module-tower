package com.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(7, ()-> System.out.println("******召唤龙珠"));
        for (int i = 1; i <= 7; i++){
            final int tempInt = i;
            new Thread(()->{
                System.out.println("收集到:" + tempInt + "龙之");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 1; i <= 7; i++){
            final int tempInt = i;
            new Thread(()->{
                System.out.println("收集到:" + tempInt + "龙之");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        for (int i = 1; i <= 7; i++){
            final int tempInt = i;
            new Thread(()->{
                System.out.println("收集到:" + tempInt + "龙之");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
