/**
 * 
 */
package com.murdock.books.multithread;

import org.junit.Test;

/**
 * @author weipeng
 * 
 */
public class ShutDownTest {
	@Test
	public void shutdown() throws Exception {
		ShutDown sd = new ShutDown();
		Thread thread = new Thread(sd);
		thread.start();
		
		Thread.sleep(1000);
		
		sd.cancel();
	}
}

class ShutDown implements Runnable {
	private volatile boolean isCanceled;

	@Override
	public void run() {
		int i =0;
		while (!isCanceled) {
			System.out.println(i++);
		}
	}

	public void cancel() {
		this.isCanceled = true;
	}
}
