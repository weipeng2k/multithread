/**
 * 
 */
package com.murdock.books.multithread;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import sun.misc.Unsafe;

/**
 * @author weipeng2k 2013年10月31日 下午8:01:24
 */
public class UnsafeTest {

	static long parkerOffset;
	static Unsafe unsafe;
	static Object lock = new Object();

	static {
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			unsafe = (Unsafe) theUnsafe.get(null);
			parkerOffset = unsafe.objectFieldOffset(A.class
					.getDeclaredField("i"));
		} catch (Exception ex) {

		}
	}

	public static void main(String[] args) {
		A a = new A();
		unsafe.putInt(a, parkerOffset, 101);
		System.out.println(a.getI());
		System.out.println(unsafe.getInt(a, parkerOffset));

		unsafe.monitorEnter(lock);
		Printer p = new Printer();
		p.start();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Unsafe Quit.");
		unsafe.monitorExit(lock);
	}

	static class Printer extends Thread {
		public void run() {
			// 获取到lock的锁（monitorenter)
			synchronized (lock) {
				System.out.println("Printer Got It.");
			}
			// 释放monitorexit
		}
	}

	static class A {
		private int i;

		public int getI() {
			return i;
		}
	}
}
