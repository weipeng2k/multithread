package com.murdock.books.multithread.example;

/**
 * @author weipeng2k 2018年11月26日 下午15:59:49
 */
public class PrimitiveVisibilityDemo {
    static int i = 0;
    private static boolean isStop = false;

    private static volatile  int y = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Thread 1 is started.");
                while (!isStop) {
                    y = 10;
                }

                System.out.println("Thread 1 is stop." + i);
            }
        });

        thread1.start();

        Thread.sleep(1000);
        isStop = true;
        System.out.println("Main thread is stop. The isStop value is " + isStop);
    }

    static int findI(long start) {

        return (int) start;
    }

    interface Add {
        void add();
    }

    static class StudentCachePadding implements Add {
        int age;
        long createTime;
        long createTime1;
        long createTime2;
        long createTime3;
        long createTime4;
        long a1, b2, c3, d4, e5, f6, g7;
        long modifyTime;
        long score;

        public void add() {
            modifyTime = System.currentTimeMillis();
            score++;
        }

    }

    static class Job implements Runnable {

        Add add;

        int i = 1000000;

        @Override
        public void run() {

            while (i > 0) {
                add.add();
                i--;
            }

        }
    }
}
