/**
 * 
 */
package com.murdock.books.multithread.example;

import java.util.concurrent.TimeUnit;

/**
 * 从notfiy返回的，由WAITING变为BLOCKED
 * 
 * @author weipeng2k 2014年3月2日 上午12:45:36
 */
public class Wait2Block {

	private static Object obj = new Object();

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Wait());
			thread.start();
		}
		
		TimeUnit.SECONDS.sleep(10);
		
		System.out.println("begin notifyall");
		synchronized (obj) {
			obj.notifyAll();
		}
		System.out.println("end notifyall");
		
		
		TimeUnit.SECONDS.sleep(10);
	}

	static class Wait implements Runnable {

		@Override
		public void run() {
			synchronized (obj) {
				try {
					obj.wait();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
