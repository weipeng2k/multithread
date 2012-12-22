/**
 * 
 */
package com.murdock.books.multithread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <pre>
 * Test - Test And Set Lock
 * 
 * 使用一个AtomicBoolean作为成员变量
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public class TTASLock implements Lock {
    AtomicBoolean state = new AtomicBoolean(false);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.locks.Lock#lock()
     */
    @Override
    public void lock() {
	// 先查看 ，如果返回false，那么有可能
	// 在按照TAS来运行
	while (true) {
	    while (state.get()) {
	    }

	    if (!state.getAndSet(true)) {
		return;
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.locks.Lock#lockInterruptibly()
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.locks.Lock#tryLock()
     */
    @Override
    public boolean tryLock() {
	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.locks.Lock#tryLock(long,
     * java.util.concurrent.TimeUnit)
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit)
	    throws InterruptedException {
	return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.locks.Lock#unlock()
     */
    @Override
    public void unlock() {
	state.set(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.locks.Lock#newCondition()
     */
    @Override
    public Condition newCondition() {
	return null;
    }

}
