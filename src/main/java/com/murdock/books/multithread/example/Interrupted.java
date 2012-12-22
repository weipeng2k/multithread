/**
 * 
 */
package com.murdock.books.multithread.example;

/**
 * @author weipeng
 * 
 */
public class Interrupted {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		InterruptedJob ij = new InterruptedJob();
		ij.setName("InterruptedJobThread ");
		ij.start();

		Thread.sleep(2000);

		// 中断
		ij.interrupt();
		System.out.println("INTERRUPTED IJ");

		Thread.sleep(2000);
	}

	static class InterruptedJob extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				System.out.println("CURRENT INTERRUPT STATUS IS "
						+ Thread.currentThread().getName()
						+ Thread.currentThread().isInterrupted());
				// 再次进行中断
				Thread.currentThread().interrupt();

				System.out.println("CURRENT INTERRUPT STATUS IS "
						+ Thread.currentThread().getName()
						+ Thread.currentThread().isInterrupted());
			}
		}
	}

}
