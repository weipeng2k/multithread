package com.murdock.books.multithread.book;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.locks.StampedLock;

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

    @Test
    public void try_optimistic_read() {
        System.out.println(stampedLock.tryOptimisticRead());
    }

    @Test
    public void print_sbits() {
        Long l = ~127L;
        System.out.println(Long.toBinaryString(l));
        System.out.println(l);
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
