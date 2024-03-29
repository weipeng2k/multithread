package com.murdock.books.multithread.book;

import java.util.concurrent.locks.StampedLock;

/**
 * @author weipeng2k 2022年02月13日 下午22:39:49
 */
public class Point {
    private final StampedLock sl = new StampedLock();
    private double x, y;

    // 排他的锁定方法
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // 只读方法
    // 如果乐观读失效，将会晋升到读模式
    double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();
        try {
            for (; ; stamp = sl.readLock()) {
                if (stamp == 0L) {
                    continue;
                }
                // possibly racy reads
                double currentX = x;
                double currentY = y;
                if (!sl.validate(stamp)) {
                    continue;
                }
                return Math.hypot(currentX, currentY);
            }
        } finally {
            if (StampedLock.isReadLockStamp(stamp)) {
                sl.unlockRead(stamp);
            }
        }
    }

    // 从乐观读升级到写
    void moveIfAtOrigin(double newX, double newY) {
        long stamp = sl.tryOptimisticRead();
        try {
            for (; ; stamp = sl.writeLock()) {
                if (stamp == 0L) {
                    continue;
                }
                // possibly racy reads
                double currentX = x;
                double currentY = y;
                if (!sl.validate(stamp)) {
                    continue;
                }
                if (currentX != 0.0 || currentY != 0.0) {
                    break;
                }
                stamp = sl.tryConvertToWriteLock(stamp);
                if (stamp == 0L) {
                    continue;
                }
                // exclusive access
                x = newX;
                y = newY;
                return;
            }
        } finally {
            if (StampedLock.isWriteLockStamp(stamp)) {
                sl.unlockWrite(stamp);
            }
        }
    }

    // 从读升级到写
    void moveIfAtOrigin2(double newX, double newY) {
        long stamp = sl.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                long ws = sl.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }
}
