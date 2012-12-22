/**
 * 
 */
package com.murdock.books.multithread.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author weipeng
 * 
 */
public class Synchronized {
	static synchronized void m() {
		System.out.println("T");
	}

	public static void main(String[] args) throws Exception {
		List<String> s = new ArrayList<String>();
		s.add("1");

		List<String> synchronizedList = Collections.synchronizedList(s);

		Thread t = new Thread(new AccessSynchronizedCollections(
				synchronizedList));
		t.start();

		synchronized (synchronizedList) {
			Thread.sleep(5000);
			System.out.println("Main-thread" + synchronizedList.size());
		}

	}

	/**
	 * 这个线程将会首先休息2000ms，然后唤醒后去请求锁，并执行操作
	 */
	static class AccessSynchronizedCollections implements Runnable {
		List<String> list;

		public AccessSynchronizedCollections(List<String> list) {
			this.list = list;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("AccessSynchronizedCollections" + list.size());
			list.add("2");
		}
	}
}
