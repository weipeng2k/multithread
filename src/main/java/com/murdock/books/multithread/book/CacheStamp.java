package com.murdock.books.multithread.book;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/**
 * @author weipeng2k 2022年02月20日 下午22:19:58
 */
public class CacheStamp {

    private static final Map<String, Object> map = new HashMap<>();
    private static final StampedLock stampedLock = new StampedLock();

    public static final Object get(String key) {
        long stamp = stampedLock.tryOptimisticRead();

        try {
            for (; ; stamp = stampedLock.readLock()) {
                if (stamp == 0L) {
                    continue;
                }

                Object result = map.get(key);

                if (!stampedLock.validate(stamp)) {
                    continue;
                }

                return result;
            }
        } finally {
            if (StampedLock.isReadLockStamp(stamp)) {
                stampedLock.unlockRead(stamp);
            }
        }
    }

    public static final Object put(String key, Object value) {
        long stamp = stampedLock.writeLock();
        try {
            return map.put(key, value);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    public static final void clear() {
        long stamp = stampedLock.writeLock();
        try {
            map.clear();
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}
