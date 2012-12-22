/**
 * 
 */
package com.murdock.books.multithread;

/**
 * @author weipeng
 * 
 */
public class DaemonTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new D());
		t.setDaemon(true);
		t.start();
		
		Thread.sleep(3000);
	}
	
	static class D implements Runnable {

		@Override
		public void run() {
			System.out.println("Start!");
			
			try {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println("Run.");
				}
			} finally {
				System.out.println("Quit Runnable.");
			}
		}
	}

}
