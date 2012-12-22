/**
 * 
 */
package com.murdock.books.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.junit.Test;

/**
 * @author weipeng
 * 
 */
public class FutureTaskTest2 {

	@Test
	public void test() throws Exception {
		FutureTask<Object> future = new FutureTask<Object>(new Consumer());
		long start = System.currentTimeMillis();
		
		Thread thread = new Thread(future);
		Object model = new Object();
		thread.start();
		Thread.sleep(2000);
		Object obj = future.get();
		
		System.out.println(obj.toString() + model);
		System.out.println(System.currentTimeMillis() - start);
	}

	class Consumer implements Callable<Object> {

		public Object call() throws Exception {
			System.out.println("Computation begin");

			Thread.sleep(3000);

			return new Object();
		}

	}

}
