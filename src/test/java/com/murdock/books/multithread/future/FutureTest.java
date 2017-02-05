package com.murdock.books.multithread.future;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by weipeng2k on 16/3/23.
 */
public class FutureTest {
    @Test
    public void get() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

        long start = System.currentTimeMillis();
        // 提交任务,保存future
        for (int i = 0; i < 10; i++) {
            futureList.add(executorService.submit(new Compute()));
        }

        int sum = 0;
        // 从future中获取异步计算的结果,如果计算
        for (Future<Integer> future : futureList) {
            sum += future.get();
        }

        long end = System.currentTimeMillis();
        System.out.println("计算结果:" + sum + ",总共耗时:" + (end - start) + "ms.");

    }

    class Compute implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(2000);
            return 1;
        }
    }

}
