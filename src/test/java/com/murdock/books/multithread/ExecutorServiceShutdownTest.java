/**
 * 
 */
package com.murdock.books.multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * @author weipeng
 * 
 */
public class ExecutorServiceShutdownTest {
	private ExecutorService es = Executors.newFixedThreadPool(10);

	@Test
	public void shutdown() {
		Thread thread = new Thread(new ShutDown());
		thread.start();

		while (!es.isShutdown()) {
			es.execute(new Task());
		}
	}

	class ShutDown implements Runnable {

		@Override
		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String line = null;

			try {
				while ((line = br.readLine()) == null
						|| (!line.equals("shutdown"))) {
				}

				es.shutdown();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	class Task implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
