package com;

import java.util.concurrent.Semaphore;

public class Foo {
    public Semaphore seam_first_two = new Semaphore(1);

    public Semaphore seam_two_second = new Semaphore(1);

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        seam_first_two.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        seam_first_two.acquire();
        printSecond.run();
        seam_two_second.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        seam_two_second.acquire();
        printThird.run();
    }


    public static void main(String[] args) throws Exception{

        Runnable r1 = () -> System.out.print("one");

        Runnable r2 = () -> System.out.print("two");

        Runnable r3 = () -> System.out.print("three");

        Foo foo = new Foo();
        foo.second(r2);
        foo.third(r3);
        foo.first(r1);


    }
}