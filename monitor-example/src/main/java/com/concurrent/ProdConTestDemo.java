package com.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//生产者消费者传统版
public class ProdConTestDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 0; i < 5; i++){
                shareData.increment();
            }
        }, "AA").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++){
                shareData.decrement();
            }
        }, "BB").start();


    }
}

class ShareData{  //资源类

    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment(){
        lock.lock();
        try{
            while (number != 0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() +"\t" + number);
            condition.signalAll();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

    public void decrement(){
        lock.lock();
        try{
            while (number == 0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() +"\t" + number);
            condition.signalAll();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }
}
