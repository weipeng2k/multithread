package com.murdock.books.multithread.book;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

/**
 * @author weipeng2k 2022年02月14日 下午21:08:14
 */
public class StampedLockTest {

    private final StampedLock stampedLock = new StampedLock();

    @Test
    public void try_acquire_write() {
        long stamp = stampedLock.writeLock();

        Assert.assertTrue(stampedLock.tryConvertToWriteLock(stamp) != 0);

        Assert.assertEquals(stampedLock.tryConvertToWriteLock(stamp), stamp);
    }

    /**
     * <pre>
     * 获取两个读锁，不释放
     *
     * 然后获取写锁
     * </pre>
     */
    @Test
    public void acquire_write_lock() {
        Thread thread = new Thread(() -> {
            long stamp = stampedLock.readLock();
        }, "T");
        thread.start();


        Thread thread2 = new Thread(() -> {
            long stamp = stampedLock.readLock();
        }, "T2");
        thread2.start();

        stampedLock.writeLock();
    }

    @Test
    public void acquire_write_lock_seq() {
        Thread thread = new Thread(() -> {
            long stamp = stampedLock.readLock();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stampedLock.unlockRead(stamp);
        }, "T");
        thread.start();

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long stamp = stampedLock.writeLock();

        stampedLock.unlockWrite(stamp);

        stampedLock.writeLock();
    }

    @Test
    public void read_lock() {
        long stamp = 0;
        for (int i = 0; i < 1000; i++) {
            System.out.println(stampedLock.readLock());
            System.out.println(stampedLock.getReadLockCount());
        }
        System.out.println(stamp);
    }

    @Test
    public void try_optimistic_read() {
        System.out.println(stampedLock.tryOptimisticRead());
    }

    @Test
    public void print_bits() {
        System.out.println(1 << (8 -1));
        System.out.println(Long.toBinaryString(255));
    }

    @Test
    public void print_sbits() {
        Long l = ~127L;
        System.out.println(Long.toBinaryString(l));
        System.out.println(l);
        System.out.println(Long.toBinaryString(126));
    }

    @Test
    public void read_permits() {
        IntStream.range(0, 128)
                .forEach(i -> {
                    Thread thread = new Thread(() -> {
                        stampedLock.readLock();
                    }, "Reader" + i);
                    thread.start();
                });

        stampedLock.writeLock();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * will block.
     */
    @Test
    @Ignore
    public void acquire_write_lock_twice() {
        stampedLock.writeLock();

        stampedLock.writeLock();
    }

    @Test
    public void acquire_read_lock_twice() {
        stampedLock.readLock();

        stampedLock.readLock();
    }

}
