/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.murdock.books.multithread;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author weipeng 2013-4-1 下午5:22:37
 */
public class LockSupportTest {

    class FIFOMutex {

        private final AtomicBoolean locked  = new AtomicBoolean(false);
        private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

        public void lock() {
            boolean wasInterrupted = false;
            Thread current = Thread.currentThread();
            waiters.add(current);

            // Block while not first in queue or cannot acquire lock
            while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
                LockSupport.park(this);
                if (Thread.interrupted()) // ignore interrupts while waiting
                wasInterrupted = true;
            }

            waiters.remove();
            if (wasInterrupted) // reassert interrupt status on exit
            current.interrupt();
        }

        public void unlock() {
            locked.set(false);
            LockSupport.unpark(waiters.peek());
        }
    }

}
