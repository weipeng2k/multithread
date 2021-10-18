package com.murdock.books.multithread;

import org.junit.Test;

/**
 * @author weipeng2k 2018年02月09日 下午14:28:22
 */
public class PrintJobTest {

    @Test
    public void test() {
        PrintJob p1 = new PrintJob("A");
        PrintJob p2 = new PrintJob("B");
        PrintJob p3 = new PrintJob("C");

        p1.setNext(p2);
        p1.setPrevious(p3);

        p2.setPrevious(p1);
        p2.setNext(p3);

        p3.setPrevious(p2);
        p3.setNext(p1);

        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(p3);

        t1.start();
        t2.start();
        t3.start();

        synchronized (p1) {
            p1.notify();
        }

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}