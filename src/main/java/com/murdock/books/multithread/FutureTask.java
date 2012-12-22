/**
 * 
 */
package com.murdock.books.multithread;


/**
 * @author weipeng
 * 
 */
public class FutureTask {
	private Object result;

	public synchronized Object get(long mills) throws InterruptedException {
		long endTime = System.currentTimeMillis() + mills;
		long remained = mills;

		// 当结果为空并没有超时是否是
		while ((result == null) && remained > 0) {
			wait(remained);

			remained = endTime - System.currentTimeMillis();
		}

		return result;
	}

	public synchronized void put(Object result) {
		this.result = result;
		notifyAll();
	}
}
