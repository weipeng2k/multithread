/**
 * 
 */
package com.murdock.books.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * @author weipeng2k
 * 
 */
public class ReentrantLock2Test {
	private static Lock fairLock = new ReentrantLock(true);
	private static Lock unfairLock = new ReentrantLock();

	/**
	 * fair version cost:4973978000
	 */
	@Test
	public void fair() {
		System.out.println("fair version");
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(1000000);
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Job(fairLock, begin, end));
			thread.start();
		}

		begin.countDown();
		long start = System.nanoTime();
		try {
			end.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("cost:" + (System.nanoTime() - start));
	}

	/**
	 * unfair version cost:79030000
	 */
	@Test
	public void unfair() {
		System.out.println("unfair version");
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(1000000);
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Job(unfairLock, begin, end));
			thread.start();
		}

		begin.countDown();
		long start = System.nanoTime();
		try {
			end.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("cost:" + (System.nanoTime() - start));
	}

	private static class Job implements Runnable {
		private Lock lock;

		private CountDownLatch end;

		private CountDownLatch begin;

		public Job(Lock lock, CountDownLatch begin, CountDownLatch end) {
			this.lock = lock;
			this.begin = begin;
			this.end = end;
		}

		@Override
		public void run() {
			try {
				begin.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < 100000; i++) {
				lock.lock();
				try {
					end.countDown();
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
