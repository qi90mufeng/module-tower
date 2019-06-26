package com.albert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThreadTest001 {

    /**
     * -Xms16m -Xmx32m
     * java8  Exception in thread "Thread-0" java.lang.OutOfMemoryError: Java heap space
     * 其他线程正常跑
     */
    public static void main(String[] args) {
        new Thread(() -> {
            List<byte[]> list = new ArrayList();
            while (true) {
                System.out.println(new Date().toString() + Thread.currentThread() + "==");
                byte[] b = new byte[1024 * 1024 * 1];
                list.add(b);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 线程二
        new Thread(() -> {
            while (true) {
                System.out.println(new Date().toString() + Thread.currentThread() + "==");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
