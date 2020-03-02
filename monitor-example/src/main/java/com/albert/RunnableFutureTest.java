package com.albert;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RunnableFutureTest {

    public static void main(String[] args) {
        RunnableFuture future = new RunnableFuture() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Object get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };

        Thread T1 = new Thread(future);
        T1.start();

        if (future.isDone()){
            System.out.println("----");
        }
    }

}
