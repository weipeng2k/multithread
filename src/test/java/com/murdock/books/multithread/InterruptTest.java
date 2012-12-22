/**
 * 
 */
package com.murdock.books.multithread;

import org.junit.Test;

/**
 * <pre>
 * 测试一个线程的interrupt方法
 * 
 * </pre>
 * 
 * 
 * @author weipeng
 * 
 */
public class InterruptTest {

	@Test
	public void test() throws Exception {
		Thread t = new Thread(new Run());
		t.start();
		
		Thread.sleep(1000);
		t.interrupt();
	}

	class Run implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("Interrupted." + e);
			}
		}

	}

}
