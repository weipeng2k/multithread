/**
 * 
 */
package com.murdock.books.multithread;

import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * @author weipeng2k
 * 
 */
public class ReentrantLockTest {
	private static Lock fairLock = new ReentrantLock2(true);
	private static Lock unfairLock = new ReentrantLock2();

	@Test
	public void fair() {
		System.out.println("fair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(fairLock)) {
				public String toString() {
					return getName();
				}
			};
			thread.setName("" + i);
			thread.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void unfair() {
		System.out.println("unfair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(unfairLock)) {
				public String toString() {
					return getName();
				}
			};
			thread.setName("" + i);
			thread.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class Job implements Runnable {
		private Lock lock;

		public Job(Lock lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				lock.lock();
				try {
					System.out.println("Lock by:"
							+ Thread.currentThread().getName() + " and "
							+ ((ReentrantLock2) lock).getQueuedThreads()
							+ " waits.");
				} finally {
					lock.unlock();
				}
			}
		}
	}

	private static class ReentrantLock2 extends ReentrantLock {

		public ReentrantLock2() {
			super();
		}

		public ReentrantLock2(boolean f) {
			super(f);
		}

		private static final long serialVersionUID = 1773716895097002072L;

		public Collection<Thread> getQueuedThreads() {
			return super.getQueuedThreads();
		}

	}
}
