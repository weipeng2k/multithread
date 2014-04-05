package com.murdock.books.multithread;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author weipeng2k 2013年10月30日 下午7:25:23
 */
public class ReadWriteLockTest {

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	static class ReentrantReadWriteLock2 extends ReentrantReadWriteLock {
		private static final long serialVersionUID = -4098944085068668816L;

		protected Collection<Thread> getQueuedWriterThreads() {
			return super.getQueuedWriterThreads();
		}

		protected Collection<Thread> getQueuedReaderThreads() {
			return super.getQueuedReaderThreads();
		}
	}

	public static void main(String[] args) {
		final Lock r = rwl.readLock();
		final Lock w = rwl.writeLock();

		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}

				w.lock();
				try {
					System.out.println("I got write lock.");
				} finally {
					w.unlock();
				}
			}
		}.start();

		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}

				r.lock();
				try {
					System.out.println("I got read lock.");
				} finally {
					r.unlock();
				}
			}
		}.start();

		new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}

				try {
					Field field = ReentrantReadWriteLock.class
							.getDeclaredField("sync");
					field.setAccessible(true);
					Object obj = field.get(rwl);
					System.out.println(ToStringBuilder.reflectionToString(
							field.get(rwl), ToStringStyle.MULTI_LINE_STYLE,
							true));

					field = AbstractQueuedSynchronizer.class
							.getDeclaredField("head");
					field.setAccessible(true);
					System.out.println(ToStringBuilder.reflectionToString(
							field.get(obj), ToStringStyle.MULTI_LINE_STYLE,
							true));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}.start();

		r.lock();
		r.unlock();
		w.lock();
		r.lock();
		r.unlock();
		w.unlock();
		System.out.println("I am out.");
	}
}
