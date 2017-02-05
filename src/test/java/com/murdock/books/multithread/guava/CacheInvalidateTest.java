package com.murdock.books.multithread.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author weipeng2k 2016年12月21日 下午16:42:22
 */
public class CacheInvalidateTest {
    private final Cache<String, String> cacherClients = CacheBuilder.newBuilder()//
            .maximumSize(2 << 17).expireAfterAccess(5, TimeUnit.SECONDS)// 最大是65535*2
            .removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    System.out.println("delete : " + notification.getKey());
                }
            })//
            .build();


    @Test
    public void test() throws Exception {
        String a = cacherClients.get("A", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "A";
            }
        });

        String b = cacherClients.get("B", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "B";
            }
        });

        String c = cacherClients.get("C", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "C";
            }
        });

        String d = cacherClients.get("D", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "D";
            }
        });

        System.out.println(a + b + c + d);

        TimeUnit.SECONDS.sleep(6);

        a = cacherClients.get("A", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "A";
            }
        });

        TimeUnit.SECONDS.sleep(5);
    }


}
