package com.murdock.books.multithread.book;

import org.junit.Test;

public class BoundedQueueTest {

	private BoundedQueue<String> bq = new BoundedQueue<String>(5);
	
	@Test
	public void test() {
		Thread thread = new Thread() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						SleepUtils.second(1);
						bq.add("" + i);
						System.out.println("add " + i + " to queue.");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		SleepUtils.second(1);
		Thread thread2 = new Thread() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						SleepUtils.second(1);
						String r = bq.remove();
						System.out.println("remove " + r + " from queue.");
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		};
		thread2.start();
		
		SleepUtils.second(10);
	}

}
