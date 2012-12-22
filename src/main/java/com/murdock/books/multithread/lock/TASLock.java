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
 * Test And Set Lock
 * 
 * 使用一个AtomicBoolean作为成员变量
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public class TASLock implements Lock {
    AtomicBoolean state = new AtomicBoolean(false);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.locks.Lock#lock()
     */
    @Override
    public void lock() {
	// 当返回false时出循环
	// 如果一开是是false，那么可以跳出
	// 设置成功后，后续的线程返回true
	while (state.getAndSet(true)) {

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
