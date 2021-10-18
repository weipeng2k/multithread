//package com.murdock.books.multithread;
//
//import org.junit.Test;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//interface Add {
//    void add();
//}
//
///**
// * @author weipeng2k 2016年11月13日 下午22:17:51
// */
//public class PaddingTest {
//
//    private Executor executor = Executors.newFixedThreadPool(8);
//
//    @Test
//    public void test() {
//        Add add = new StudentCache();
//
//        for (Field field : StudentCache.class.getDeclaredFields()) {
//            System.out.println(field.getType() + ":" + field.getName());
//        }
//
//        long sum = 0L;
//        for (int i = 0; i < 100; i++) {
//            sum += work(add);
//        }
//        // 15653
//        // if contended 13497
//        System.out.println(sum);
//    }
//
//    @Test
//    public void padding() {
//        Add add = new StudentCachePadding();
//        long sum = 0L;
//        for (int i = 0; i < 100; i++) {
//            sum +=work(add);
//        }
//        // 13258
//        System.out.println(sum);
//    }
//
//    private long work(Add add) {
//        CountDownLatch start = new CountDownLatch(1);
//        CountDownLatch end = new CountDownLatch(8);
//        for (int i = 0; i < 10; i++) {
//            Job job = new Job();
//            job.add = add;
//            job.start = start;
//            job.end = end;
//            executor.execute(job);
//        }
//
//        long startTime = System.currentTimeMillis();
//
//        start.countDown();
//
//        try {
//            end.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        long endTime = System.currentTimeMillis();
//
//        return endTime - startTime;
//    }
//
//    class Job implements Runnable {
//
//        Add add;
//
//        int i = 1000000;
//
//        CountDownLatch start;
//
//        CountDownLatch end;
//
//        @Override
//        public void run() {
//
//            try {
//                start.await();
//
//                while (i > 0) {
//                    add.add();
//                    i--;
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                end.countDown();
//            }
//        }
//    }
//}
//
//class StudentCache implements Add {
//    int age;
//    long createTime;
//    long createTime1;
//    long createTime2;
//    long createTime3;
//    long createTime4;
//    @sun.misc.Contended("group1")
//    long modifyTime;
//    @sun.misc.Contended("group1")
//    long score;
//
//    public void add() {
//        modifyTime = System.currentTimeMillis();
//        score++;
//    }
//}
//
//class StudentCachePadding implements Add {
//    int age;
//    long createTime;
//    long createTime1;
//    long createTime2;
//    long createTime3;
//    long createTime4;
//    long a1, b2, c3, d4, e5, f6, g7;
//    long modifyTime;
//    long score;
//
//    public void add() {
//        modifyTime = System.currentTimeMillis();
//        score++;
//    }
//
//}
