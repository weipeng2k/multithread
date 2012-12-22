/**
 * 
 */
package com.murdock.books.multithread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

/**
 * @author weipeng
 */
public class CountDownLatchTest {

	private CountDownLatch countDownLatch = new CountDownLatch(10);

	@Test
	public void countDown() {
		Random random = new Random();
		
		for (int i = 0; i < 10; i++) {
			CountDown cd = new CountDown(random.nextInt(2000), countDownLatch);
			Thread thread = new Thread(cd);
			thread.start();
		}
		
		System.out.println("all thread runs.");
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("OK");
	}

	static class CountDown implements Runnable {

		long sleep;
		CountDownLatch countDownLatch;

		public CountDown(long sleep, CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
			this.sleep = sleep;
		}

		@Override
		public void run() {
			try {
				System.out.println("i am sleep " + sleep + ".");
				Thread.sleep(sleep);
				countDownLatch.countDown();
				System.out.println("i am ready.");
			} catch (InterruptedException ex) {
			}
		}
	}
}
