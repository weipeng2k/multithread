/**
 * 
 */
package com.murdock.books.multithread;

/**
 * @author weipeng
 * 
 */
public class NoVisibilityTest {
	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {
		public void run() {
			while (!ready) {
				Thread.yield();
				System.out.println(number);
			}
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		new ReaderThread().start();
		new ReaderThread().start();
		new ReaderThread().start();
		new ReaderThread().start();
		new ReaderThread().start();
		new ReaderThread().start();
		new ReaderThread().start();
		new ReaderThread().start();
		number = 42;
		ready = true;
	}
}
