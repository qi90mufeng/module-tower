package com.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//多条件精确唤醒
class ShareThread{
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition a1 = lock.newCondition();
    private Condition b1 = lock.newCondition();
    private Condition c1 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            while (number != 1){
                a1.await();
            }
            for (int i = 0; i < 5; i++){
                System.out.println(Thread.currentThread().getName() +"\t" + i);
            }
            number++;
            b1.signal();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try{
            while (number != 2){
                b1.await();
            }
            for (int i = 0; i < 10; i++){
                System.out.println(Thread.currentThread().getName() +"\t" + i);
            }
            number++;
            c1.signal();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }


    public void print15(){
        lock.lock();
        try{
            while (number != 3){
                c1.await();
            }
            for (int i = 0; i < 15; i++){
                System.out.println(Thread.currentThread().getName() +"\t" + i);
            }
            number = 1;
            a1.signal();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }
}
public class ThreadGroupTest {
    public static void main(String[] args) {
        ShareThread shareThread = new ShareThread();
        new Thread(()->{
            for (int i = 0; i < 10; i++){
                shareThread.print5();
            }
        }, "A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++){
                shareThread.print10();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++){
                shareThread.print15();
            }
        },"C").start();
    }
}
