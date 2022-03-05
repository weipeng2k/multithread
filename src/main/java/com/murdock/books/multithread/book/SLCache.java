package com.murdock.books.multithread.book;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/**
 * @author weipeng2k 2022年03月04日 下午15:13:18
 */
public class SLCache<K, V> implements Cache<K, V> {
    private final Map<K, V> map = new HashMap<>();
    private final StampedLock stampedLock = new StampedLock();

    @Override
    public V get(K k) {
//        原始版本
//        long stamp = stampedLock.tryOptimisticRead();
//
//        V v = map.get(k);
//
//        if (!stampedLock.validate(stamp)) {
//            stamp = stampedLock.readLock();
//            try {
//                v = map.get(k);
//            } finally {
//                stampedLock.unlockRead(stamp);
//            }
//        }
//
//        return v;

        long stamp = stampedLock.tryOptimisticRead();

        try {
            for (; ; stamp = stampedLock.readLock()) {
                if (stamp == 0L) {
                    continue;
                }

                V v = map.get(k);

                if (!stampedLock.validate(stamp)) {
                    continue;
                }

                return v;
            }
        } finally {
            if (StampedLock.isReadLockStamp(stamp)) {
                stampedLock.unlockRead(stamp);
            }
        }
    }

    @Override
    public V put(K k, V v) {
        long stamp = stampedLock.writeLock();
        try {
            return map.put(k, v);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    @Override
    public V putIfAbsent(K k, V v) {
        long stamp = stampedLock.tryOptimisticRead();

        try {
            for (; ; stamp = stampedLock.writeLock()) {
                if (stamp == 0L) {
                    continue;
                }

                V prev = map.get(k);

                if (!stampedLock.validate(stamp)) {
                    continue;
                }

                // 如果存在一个值
                if (prev != null) {
                    return prev;
                }

                stamp = stampedLock.tryConvertToWriteLock(stamp);

                if (stamp == 0L) {
                    continue;
                }

                prev = map.get(k);
                if (prev == null) {
                    map.put(k, v);
                }

                return prev;
            }
        } finally {
            if (StampedLock.isWriteLockStamp(stamp)) {
                stampedLock.unlockWrite(stamp);
            }
        }
    }

    @Override
    public void clear() {
        long stamp = stampedLock.writeLock();
        try {
            map.clear();
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}
