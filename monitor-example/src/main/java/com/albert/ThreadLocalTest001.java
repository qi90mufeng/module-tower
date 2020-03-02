package com.albert;

import java.lang.ref.WeakReference;

public class ThreadLocalTest001{


    public static void main(String[] args) throws InterruptedException {
        String a1 = new String("value");
        WeakReference<String> wk = new WeakReference<>(a1);
        a1 = null;
        System.gc();
        Thread.sleep(10000);
        System.out.println(wk.get());
    }

}
