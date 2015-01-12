package com.murdock.books.multithread.book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author weipeng2k 2013年10月30日 下午7:25:23
 */
public class ReadWriteLockTest {

	private static ReentrantReadWriteLock2	rwl	= new ReentrantReadWriteLock2();

	static class ReentrantReadWriteLock2 extends ReentrantReadWriteLock {
		private static final long	serialVersionUID	= -4098944085068668816L;

		public Collection<Thread> getQueuedThreads() {
			List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
			Collections.reverse(arrayList);
			return arrayList;
		}
	}

	public static void main(String[] args) {
		final Lock r = rwl.readLock();
		final Lock w = rwl.writeLock();
		final CountDownLatch start = new CountDownLatch(1);

		for (int i = 0; i < 3; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						start.await();
					} catch (InterruptedException e) {
					}
					
					for (int j = 0; j < 5; j++) {
						r.lock();
						SleepUtils.second(1);
						System.out.println("" + Thread.currentThread() + ":" + j);
						r.unlock();
					}
				}
			}, "READ-" + i) {
				public String toString() {
					return getName();
				}
			};
			thread.start();
		}
		
		for (int i = 0; i < 1; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						start.await();
					} catch (InterruptedException e) {
					}
					
					for (int j = 0; j < 5; j++) {
						w.lock();
						SleepUtils.second(2);
						System.out.println("" + Thread.currentThread() + ":" + j);
						w.unlock();
					}
				}
			}, "WRITE-" + i){
				public String toString() {
					return getName();
				}
			};
			thread.start();
		}
		
		start.countDown();
		for ( int i = 0; i < 20; i++) {
			System.out.println(rwl.getQueuedThreads());
			SleepUtils.second(1);
		}
	}
}
