/**
 * 
 */
package com.murdock.books.multithread.example;

import java.util.concurrent.CountDownLatch;

/**
 * @author weipeng
 * 
 */
public class Priority {
	private static CountDownLatch countDownLatch = new CountDownLatch(10000000);

	private static CountDownLatch start = new CountDownLatch(1);

	public static void main(String[] args) {
		CountJob job1 = new CountJob();
		Thread lingdao = new Thread(job1);
		lingdao.setPriority(10);
		lingdao.start();

		CountJob job2 = new CountJob();
		Thread pming = new Thread(job2);
		pming.setPriority(1);
		pming.start();
		
		CountJob job3 = new CountJob();
		Thread zhongchan = new Thread(job3);
		zhongchan.setPriority(5);
		zhongchan.start();

		start.countDown();

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("lingdao : have " + job1.getTimes());
		System.out.println("pming : have" + job2.getTimes());
		System.out.println("zhongchan : have" + job3.getTimes());

	}

	static class CountJob implements Runnable {

		private int times = 0;

		@Override
		public void run() {
			// 等待开始
			try {
				start.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			while (countDownLatch.getCount() > 0) {
				synchronized (CountJob.class) {
					if (countDownLatch.getCount() > 0) {
						countDownLatch.countDown();
						times++;
						Thread.yield();
					}
				}
			}
		}

		public int getTimes() {
			return times;
		}
	}
}
