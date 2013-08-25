/**
 * 
 */
package com.murdock.books.multithread;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * @author weipeng2k
 * 
 */
public class ConditionTest {

	private static Lock lock = new ReentrantLock();

	private static Condition condition = lock.newCondition();

	@Test
	public void testAwaitNanos() {

		Thread thread = new Thread() {

			@Override
			public void run() {
				long remain = TimeUnit.SECONDS.toNanos(10);
				while (remain > 0) {
					lock.lock();
					try {
						remain = condition.awaitNanos(remain);
						System.out.println("剩余：" + TimeUnit.NANOSECONDS.toMillis(remain) + "ms");
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}

		};

		thread.start();

		Thread thread2 = new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					lock.lock();
					try {
						condition.signal();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}

		};

		thread2.start();

		try {
			Thread.sleep(1000 * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void awaitUntil() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 10);
		final Date deadline = calendar.getTime();
		System.out.println("结束时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deadline));
		Thread thread = new Thread() {

			@Override
			public void run() {
				lock.lock();
				try {
					while (condition.awaitUntil(deadline)) {
						System.out.println("没到时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					}

					System.out.println("到了");
				} catch (InterruptedException ex) {

				} finally {
					lock.unlock();
				}
			}

		};

		thread.start();

		Thread thread2 = new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					lock.lock();
					try {
						condition.signal();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
		};

		thread2.start();

		try {
			Thread.sleep(1000 * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
