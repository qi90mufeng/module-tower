package com.albert;

public class ThreadLocalTest extends Thread{

    public static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("----主线程开启----");
        inheritableThreadLocal.set("主线程赋值");
        threadLocal.set("主线程--");
        new ThreadLocalTest().start();

        Thread.sleep(30000);
        System.out.println("----主线程结束----");
    }

    @Override
    public void run() {
        System.out.println("子线程：" + inheritableThreadLocal.get());
        System.out.println("子线程：" + threadLocal.get());
    }
}
