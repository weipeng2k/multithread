package com.murdock.books.multithread.guava;

import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by weipeng2k on 16/3/6.
 */
public class ListenableFutureTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        final SettableFuture<Integer> listenableFuture = SettableFuture.create();
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000L);
                    System.out.println("RUN in" + Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, Executors.newSingleThreadExecutor());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("RUN in" + Thread.currentThread());
                    listenableFuture.set(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("got value: " + listenableFuture.get());

        Thread.sleep(5000L);
    }

    @Test
    public void makeCall() throws Exception {
        Executor ups = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "快递公司");
            }
        });
        Executor messenger = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "快递员");
            }
        });


        final SettableFuture<Pack> signIn = SettableFuture.create();
        signIn.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "通知用户已经签收.");
            }
        }, ups);

        System.out.println("经过了一段时间...");
        TimeUnit.SECONDS.sleep(3);

        messenger.execute(new Runnable() {
            @Override
            public void run() {
                Pack pack = new Pack();
                pack.name = "快递";
                pack.time = new Date();
                System.out.println(Thread.currentThread() + "快递被签收.");
                signIn.set(pack);
            }
        });

    }

    class Pack {
        String name;
        Date time;
    }
}
