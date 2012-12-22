/**
 * 
 */
package com.murdock.books.multithread.example;

import java.io.IOException;
import java.io.InputStream;


/**
 * <pre>
 * 处于同步读取的线程被中断，不会抛出异常
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public class ReadInterrupted {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 使用父线程，也就是main-thread
		Thread thread = new Thread(new InterruptedJob(Thread.currentThread()));
		thread.start();
		
		InputStream is = System.in;
		try {
			is.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Main Thread is interrupted ? " + Thread.currentThread().isInterrupted());
	}

	static class InterruptedJob implements Runnable {

		Thread interruptedThread;

		public InterruptedJob(Thread thread) {
			this.interruptedThread = thread;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			interruptedThread.interrupt();
		}
	}
}
