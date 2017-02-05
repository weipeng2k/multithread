package com.murdock.books.multithread.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by weipeng2k on 16/9/7.
 */
public class CacheTest {

    static volatile boolean value = true;
    private Cache<String, AtomicBoolean> cache = CacheBuilder.newBuilder().expireAfterAccess(5,
            TimeUnit.MICROSECONDS).removalListener(
            new RemovalListener<String, AtomicBoolean>() {
                @Override
                public void onRemoval(RemovalNotification<String, AtomicBoolean> notification) {
                    notification.getValue().set(false);
                }
            }).build();

    @Test
    public void multi() throws ExecutionException {
        cache.get("key", new Callable<AtomicBoolean>() {
            @Override
            public AtomicBoolean call() throws Exception {
                return new AtomicBoolean(true);
            }
        });
        for (int i = 0; i < 10; i++) {
            GetJob getJob = new GetJob();
            getJob.cache = cache;
            Thread thread = new Thread(getJob);
            thread.start();
        }

        value = false;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static AtomicInteger index = new AtomicInteger();

    private class GetJob implements Runnable {

        Cache<String, AtomicBoolean> cache;

        @Override
        public void run() {
            while (value) {

            }

            for (int i = 0; i < 1000; i++) {
                try {
                    AtomicBoolean key = cache.get("key", new Callable<AtomicBoolean>() {
                        @Override
                        public AtomicBoolean call() throws Exception {
                            return new AtomicBoolean(true);
                        }
                    });
                    if (!key.get()) {
                        System.err.println(index.incrementAndGet() + ". Got a key is false.");
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
