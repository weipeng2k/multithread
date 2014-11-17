package com.murdock.books.multithread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ParkTest {
	public static void main(String[] args) throws Exception {
		TimeUnit.SECONDS.sleep(2);
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Park(), "Park-" + i);
			thread.start();
		}
	}

	static class Park implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				long start = System.nanoTime();
				LockSupport.parkNanos(900);
				System.out.println(System.nanoTime() - start);
			}
		}
	}
}
