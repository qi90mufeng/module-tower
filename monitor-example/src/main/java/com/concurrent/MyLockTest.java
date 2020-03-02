package com.concurrent;

import java.util.concurrent.TimeUnit;

public class MyLockTest {

    private int count;
    public static void main(String[] args) {
        MyLock lock = new MyLock();

        MyLockTest myLockTest = new MyLockTest();
        for (int i = 0; i < 10; i++) {
            new Thread(() ->{
                try{
                    //获取锁阻塞
                    lock.lock();
                    for (int j = 0; j < 10000; j++) {
                        myLockTest.add();
                    }

                    //尝试获取锁，超时则不继续获取锁，  非阻塞
//                    if (lock.tryLock(1)){
//                        for (int j = 0; j < 10000; j++) {
//                          myLockTest.add();
//                        }
//                    }
                }catch (Exception e){
                    //
                }finally {
                    lock.unlock();
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(myLockTest.count);
    }

    public void add(){
        this.count++;
    }
}
