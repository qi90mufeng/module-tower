package com.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class MyLock {
    //排斥锁
    private AtomicReference<Thread> flag = new AtomicReference<>();

    private LinkedBlockingQueue<Thread> list = new LinkedBlockingQueue<>();

    /**
     * try{
     *    //获取锁   阻塞等待
     *   lock.lock();
     *   for (int j = 0; j < 10000; j++) {
     *      myLockTest.add();
     *   }
     * }catch (Exception e){
     *     //
     * }finally {
     *     lock.unlock();
     * }
     */
    public void lock(){
        //如果代码没被锁住，则继续执行。
        //如果代码被锁住了，则挂起当前线程（或循环等待），直到锁被释放。
        while(!flag.compareAndSet(null, Thread.currentThread())){
            //锁已被其他线程获得,等待
            list.offer(Thread.currentThread());
            System.out.println(Thread.currentThread().getName() + "\t未获得锁，进入等待中");
            LockSupport.park(Thread.currentThread());
        }
        System.out.println(Thread.currentThread().getName() + "\t获得锁");
    }

    /**
     * try{
     *     //尝试获取锁，超时则不继续获取锁，  非阻塞
     *     if (lock.tryLock(1)){
     *         for (int j = 0; j < 10000; j++) {
     *         myLockTest.add();
     *     }
     *     }
     * }catch (Exception e){
     *     //
     * }finally {
     *     lock.unlock();
     * }
     */
    public boolean tryLock(long time){
        if (!flag.compareAndSet(null, Thread.currentThread())){
            //锁已被获得,等待
            list.offer(Thread.currentThread());
            LockSupport.parkNanos(Thread.currentThread(), TimeUnit.SECONDS.toNanos(time));
            System.out.println(Thread.currentThread().getName() + "\t尝试时间已到期，开始尝试获取锁");
            boolean mm = flag.compareAndSet(null, Thread.currentThread());
            System.out.println(Thread.currentThread().getName() + "\t尝试获取锁" + mm);
            return mm;
        }
        System.out.println(Thread.currentThread().getName() + "\t获得锁");
        return true;
    }


    /**
     * 公平锁
     */
    public void unlock(){
        if (flag.compareAndSet(Thread.currentThread(), null)){
            System.out.println(Thread.currentThread().getName() + "\t释放锁");
            Thread poll = list.poll();
            if (poll != null){
                //恢复挂起的线程，使其重新争抢锁
                LockSupport.unpark(poll);
                System.out.println(poll.getName() + "\t被唤醒");
            }
        }
    }


    class MyLockQueue{
        private Node head;
        private Node tail;

        class Node{
            private Thread thread;
            private Node prev;
            private Node next;

            public Node(Thread thread){
                this.thread = thread;
            }
        }
    }
}
