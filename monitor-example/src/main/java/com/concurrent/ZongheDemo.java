package com.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws InterruptedException {
        String data = null;
        boolean retValue;
        while (FLAG){
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue){
                System.out.println(Thread.currentThread().getName() +"\t" + data + " success");
            }else{
                System.out.println(Thread.currentThread().getName() +"\t" + data + " fail");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() +"\t大老板叫停了，表示FLAG=false，生产动作结束");
    }
    public void myConsumer() throws InterruptedException {
        String result;
        while (FLAG){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName() +"\t超过2秒没有取到蛋糕，退出消费");
            }else{
                System.out.println(Thread.currentThread().getName() +"\t消费队列 " + result + " 成功");
            }

        }
    }

    public void stop(){
        this.FLAG = false;
        System.out.println(Thread.currentThread().getName() +"\t活动结束");
    }
}
public class ZongheDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() +"\t生产线程启动");
                myResource.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"prod").start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() +"\t消费线程启动");
                myResource.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myResource.stop();
        //tslee

    }
}
