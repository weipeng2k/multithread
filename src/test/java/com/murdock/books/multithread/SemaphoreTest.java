/**
 * 
 */
package com.murdock.books.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import org.junit.Test;

/**
 * <pre>
 * Semaphore 信号量，通过自身的状态来限制同时使用的线程数
 * 或者说同时使用的资源数，可以用来构建有界队列，对于资源获取
 * 后完成acquire，在释放时进行release，这样可以简单的实现池状模型
 * 
 * </pre>
 * 
 * 
 * @author weipeng
 * 
 */
public class SemaphoreTest {
	Semaphore semaphore = new Semaphore(3);
	CountDownLatch countDown = new CountDownLatch(1);
	CountDownLatch over = new CountDownLatch(10);

	@Test
	public void test() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Run());
			thread.start();
		}

		System.out.println("Beign");

		countDown.countDown();
		over.await();
	}

	class Run implements Runnable {

		@Override
		public void run() {
			try {

				countDown.await();
				semaphore.acquire();

				System.out.println("own." + Thread.currentThread());

				Thread.sleep(2000);
			} catch (InterruptedException e) {
				semaphore.release();
			} finally {
				System.out.println("release." + Thread.currentThread());
				semaphore.release();
				over.countDown();
			}
		}
	}
}
