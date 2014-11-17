/**
 * 
 */
package com.murdock.books.multithread.book;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author weipeng2k 2014年11月5日 上午10:59:40
 */
public class FairUnfairPerformanceTest {

	static Lock				lock	= null;

	static CountDownLatch	start	= new CountDownLatch(1);
	static CountDownLatch	end		= new CountDownLatch(10);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length > 0 && args[0].equals("fair")) {
			lock = new ReentrantLock(true);
		} else {
			lock = new ReentrantLock();
		}

		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Job());
			thread.setName("Job-" + i);
			thread.start();
		}

		start.countDown();
		long startMillis = System.currentTimeMillis();
		try {
			end.await();
		} catch (InterruptedException e) {
		}
		System.out.println(System.currentTimeMillis() - startMillis);
	}

	static class Job implements Runnable {

		@Override
		public void run() {
			int i = 0;
			try {
				start.await();
			} catch (InterruptedException e) {
			}
			while (i < 100000) {
				i++;
				try {
					lock.lock();
				} finally {
					lock.unlock();
				}
			}
			end.countDown();
		}

	}

}
