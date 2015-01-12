package com.murdock.books.multithread.book;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author weipeng2k 2014年12月14日 上午12:08:07
 */
public class ConditionExample {
	
	static Lock lock = new ReentrantLock();
	static Condition condition = lock.newCondition();

	public static void conditionWait() throws InterruptedException {
		lock.lock();
		try {
			condition.await();
		} finally {
			lock.unlock();
		}
	}

	public static void conditionSignal() throws InterruptedException {
		lock.lock();
		try {
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws Exception {
		Thread thread = new Thread() {
			public void run() {
				try {
					conditionWait();
					System.out.println("I am wake up.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
		
		SleepUtils.second(3);
		
		thread = new Thread() {
			public void run() {
				try {
					System.out.println("I am ready to singal.");
					conditionSignal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

}
