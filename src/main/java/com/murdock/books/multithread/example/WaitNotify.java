/**
 * 
 */
package com.murdock.books.multithread.example;

import java.util.Date;


/**
 * @author weipeng
 */
public class WaitNotify {
	static boolean flag = true;

	static Object OBJ = new Object();

	public static void main(String[] args) {
		Thread t1 = new Thread(new Waiter());
		t1.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Thread t2 = new Thread(new Notifier());
		t2.start();

	}

	/**
	 * 等待，如果flag为false则打印
	 */
	static class Waiter implements Runnable {

		@Override
		public void run() {
			// 加锁，拥有OBJ的Monitor
			synchronized (OBJ) {
				// 当条件不满足时，继续wait，同时释放了OBJ的锁
				while (flag) {
					try {
						System.out.println(Thread.currentThread()
								+ " still true. wait......" + new Date());
						OBJ.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 条件满足时，完成工作
				System.out
						.println(Thread.currentThread() + " is false. doXXX." + new Date());
			}
		}
	}

	static class Notifier implements Runnable {

		@Override
		public void run() {
			synchronized (OBJ) {

				// 获取OBJ的锁，然后进行通知，不会在notify调用中，释放OBJ的锁
				// 这也类似于过早通知
				// 直到当前线程释放了OBJ后，Waiter才能从wait方法中返回
				OBJ.notifyAll();
				
				flag = false;
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			synchronized (OBJ) {
				System.out.println("I win.");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
