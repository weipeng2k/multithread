/**
 * 
 */
package com.murdock.books.multithread;

/**
 * @author weipeng
 * 
 */
public class SuspendAndResume {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Thread t = new Thread(new Print());
		t.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		t.suspend();

		System.out.println("SUSPEND");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		t.resume();

		System.out.println("RESUME");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class Print implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("Live.");
			}
		}

	}
}
