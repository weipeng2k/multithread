package com.murdock.books.multithread.book;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author weipeng2k 2013年10月30日 下午7:25:23
 */
public class ReadWriteLockTest2 {

	private static ReentrantReadWriteLock	rwl	= new ReentrantReadWriteLock();

	public static void main(String[] args) {
		m(10);
	}
	
	private static void m(int i) {
		if (i < 0) {
			return;
		} else {
			rwl.readLock().lock();
			try {
				System.out.println(rwl.getReadLockCount());
				System.out.println(rwl.getReadHoldCount());
				m(i - 1);
			} finally {
				rwl.readLock().unlock();
			}
		}
	}
}
