/**
 * 
 */
package com.murdock.books.multithread;

/**
 * @author weipeng
 * 
 */
public class WaitNotifyTest2 {

	static Object obj = new Object();

	static class W implements Runnable {

		@Override
		public void run() {
			try {
				synchronized (obj) {
					obj.wait();
				}
			} catch (InterruptedException ex) {
				System.out.println("W is "
						+ Thread.currentThread().isInterrupted());
				return;
			}

			System.out.println("run");
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Thread t = new Thread(new W());
		t.start();
		
		t = new Thread(new W());
		t.start();
		
		Thread.sleep(1000);
		
		synchronized (obj) {
			obj.notifyAll();
		}
		
		System.out.println("main thread is over.");
	}

}
