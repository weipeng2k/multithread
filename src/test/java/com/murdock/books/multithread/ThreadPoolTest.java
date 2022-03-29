/**
 * 
 */
package com.murdock.books.multithread;

import org.junit.Test;

import com.murdock.books.multithread.example.DefaultThreadPool;
import com.murdock.books.multithread.example.ThreadPool;

/**
 * @author weipeng
 * 
 */
public class ThreadPoolTest {

	/**
	 * 构造这个线程池
	 */
	static ThreadPool<Print> threadPoolPrint = new DefaultThreadPool<Print>();

	/**
	 * 构造这个线程池
	 */
	static ThreadPool<NoPrint> threadPoolNoPrint = new DefaultThreadPool<NoPrint>();

//	@Test
//	public void worker() {
//		threadPoolPrint.removeWorker(2);
//		threadPoolPrint.removeWorker(2);
//		System.out.println(threadPoolPrint.getJobSize());
//	}

	@Test
	public void testExe() {
		for (int i = 0; i < 1000; i++) {
			threadPoolNoPrint.execute(new NoPrint());
		}

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(5000);

		System.out.println(threadPoolNoPrint.getJobSize());
	}

	@Test
	public void addExe() {
		for (int i = 0; i < 1000; i++) {
			threadPoolNoPrint.execute(new NoPrint());
		}

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		System.out.println("============Add Worker============");

		threadPoolNoPrint.addWorkers(5);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(5000);

		System.out.println(threadPoolNoPrint.getJobSize());
	}

	@Test
	public void reduceExe() {
		for (int i = 0; i < 1000; i++) {
			threadPoolNoPrint.execute(new NoPrint());
		}

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		System.out.println("============Add Worker============");

		threadPoolNoPrint.addWorkers(5);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		System.out.println("==============Reduce Worker==============");

		threadPoolNoPrint.removeWorker(7);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(20);

		System.out.println(threadPoolNoPrint.getJobSize());

		sleep(5000);

		System.out.println(threadPoolNoPrint.getJobSize());

	}

	@Test
	public void gracefulShutdown() {
		for (int i = 0; i < 1000; i++) {
			threadPoolPrint.execute(new Print());
		}

		sleep(50);

		threadPoolPrint.shutdown();
	}

	private void sleep(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static class Print implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + ", time="
					+ System.currentTimeMillis());
		}

	}

	static class NoPrint implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
