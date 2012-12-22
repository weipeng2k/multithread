/**
 * 
 */
package com.murdock.books.multithread;

/**
 * @author weipeng
 *
 */
public class EarlyReturn {
	private int value;
	
	/**
	 * 设置一个值
	 * 
	 * @param value
	 */
	public synchronized void set(int value) {
		if (this.value != value) {
			this.value = value;
			System.out.println("set " + this.value);
			notifyAll();
		}
	}
	
	/**
	 * 等待一个超时间 millis
	 * 
	 * @param value
	 * @param millis
	 * @return
	 */
	public synchronized void waitUntil(int value, long millis) throws InterruptedException {
		long endTime = System.currentTimeMillis() + millis;
		long remain = millis;
		
		while ((this.value < value) && remain > 0) {
			wait(remain);
			
			remain = endTime - System.currentTimeMillis();
		}
		
		this.value = value;
		System.out.println("waitUntil " + this.value + " remain is " + remain);
	}
}
