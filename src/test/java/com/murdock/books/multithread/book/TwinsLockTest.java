/**
 * 
 */
package com.murdock.books.multithread.book;

import java.util.concurrent.locks.Lock;

import org.junit.Test;

import com.murdock.books.multithread.book.SleepUtils;
import com.murdock.books.multithread.lock.TwinsLock;

/**
 * @author weipeng2k
 * 
 */
public class TwinsLockTest {
	@Test
	public void test() {
		final Lock lock = new TwinsLock();
		class Worker extends Thread {
			public void run() {
				while (true) {
					lock.lock();
					try {
						SleepUtils.second(1);
						System.out.println(Thread.currentThread().getName());
						SleepUtils.second(1);
					} finally {
						lock.unlock();
					}
				}
			}
		}
		// 启动10个线程
		for (int i = 0; i < 10; i++) {
			Worker w = new Worker();
			w.setDaemon(true);
			w.start();
		}
		// 每隔1秒换行
		for (int i = 0; i < 10; i++) {
			SleepUtils.second(1);
			System.out.println();
		}
	}
}
