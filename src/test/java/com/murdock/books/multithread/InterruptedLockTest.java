/**
 * 
 */
package com.murdock.books.multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * @author weipeng2k
 * 
 */
public class InterruptedLockTest {

	private Lock lock = new ReentrantLock();

	@Test
	public void test() {

		Thread thread = new Thread() {

			@Override
			public void run() {
				Thread.currentThread().interrupt();
				lock.lock();

				try {
					System.out.println(Thread.interrupted());
				} finally {
					lock.unlock();
				}
			}
		};

		thread.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
