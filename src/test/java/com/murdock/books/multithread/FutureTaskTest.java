/**
 * 
 */
package com.murdock.books.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

/**
 * @author weipeng
 * @author weipeng2k
 */
public class FutureTaskTest {
	ExecutorService es = Executors.newFixedThreadPool(10);

	@Test
	public void future() {
		System.out.println("start");
		Future<Object> future = es.submit(new Computation());

		System.out.println("create future");
		try {
			System.out.println("Do some.");
			Object obj = future.get(8000, TimeUnit.MILLISECONDS);
			System.out.println("after do" + obj);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			future.cancel(true);
			e.printStackTrace();
		}
	}

	class Computation implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			System.out.println("Start XXXXXXXXXXXX.");
			Thread.sleep(10000);
			System.out.println("Finish XXXXXXXXXXXXXXX.");

			return new Object();
		}

	}

}
