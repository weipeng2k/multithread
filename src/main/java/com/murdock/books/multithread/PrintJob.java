package com.murdock.books.multithread;

/**
 * @author weipeng2k 2018年02月09日 上午10:40:28
 */
public class PrintJob implements Runnable {

    private PrintJob previous;

    private PrintJob next;

    private String content;

    private int count = 20;

    public PrintJob(String content) {
        this.content = content;
    }

    public void setPrevious(PrintJob previous) {
        this.previous = previous;
    }

    public void setNext(PrintJob next) {
        this.next = next;
    }

    private void print() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(content);
    }

    @Override
    public void run() {
        while (count-- > 0) {
            if (previous != null) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            print();

            if (next != null) {
                synchronized (next) {
                    next.notify();
                }
            }
        }
    }
}
