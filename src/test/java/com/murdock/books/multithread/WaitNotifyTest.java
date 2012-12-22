/**
 * 
 */
package com.murdock.books.multithread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weipeng
 * 
 */
public class WaitNotifyTest {
	private static final Object lock = new Object();
	private static List<String> list = new ArrayList<String>();

	static class T1 extends Thread {
		public void run() {
			synchronized (lock) {
				while (list.size() < 3) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				for (String str : list) {
					System.out.println(str);
				}
			}
		}
	}

	static class T2 extends Thread {
		public void run() {
			while (true) {
				list.add("1");
				System.out.println("add");

				try {
					Thread.sleep(1000);
					synchronized (lock) {
						lock.notifyAll();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		T1 t = new T1();
		t.start();
		T2 t2 = new T2();
		t2.start();
	}
}
