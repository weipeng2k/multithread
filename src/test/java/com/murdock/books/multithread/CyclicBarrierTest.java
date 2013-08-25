/**
 * 
 */
package com.murdock.books.multithread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

/**
 * <pre>
 * 这个CyclicBarrier的例子简要的介绍了CyclicBarrier的用法：
 * CyclicBarrier关注这些线程能够同时的到达某个点，然后在执行一件事情（可选），然后在重新开始
 * 不同于Latch，Latch的状态是前推的（初始、执行、完成），而CyclicBarrier是可以重复的，一定程度
 * 上更加专注于多个线程并行的到达某个地方。
 * 
 * 这个例子是分组进行并发计算，待最后一个完成后，开始下一组。每组以最后一个作为完结的点。根据测试
 * 会选中最后一个到达的线程完成一些额外的工作。
 * 
 * </pre>
 * 
 * 
 * @author weipeng
 * 
 */
public class CyclicBarrierTest {

	private int times = 5;

	CountDownLatch countDown = new CountDownLatch(times);

	CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {

		@Override
		public void run() {
			System.out.println((6 - times) + " GROUP IS DONE."
					+ Thread.currentThread());

			countDown.countDown();

			times--;
		}
	});

	@Test
	public void test() {
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Run());
			thread.start();
		}

		try {
			countDown.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class Run implements Runnable {

		@Override
		public void run() {
			while (countDown.getCount() > 0) {
				Random random = new Random();
				int interval = random.nextInt(5000);

				try {
					Thread.sleep(interval);
					System.out.println("COST : " + interval + " ms. "
							+ Thread.currentThread());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					try {
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
