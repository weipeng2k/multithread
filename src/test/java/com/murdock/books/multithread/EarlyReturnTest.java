/**
 * 
 */
package com.murdock.books.multithread;

import org.junit.Test;

/**
 * @author weipeng
 *
 */
public class EarlyReturnTest {

	@Test
	public void set() {
		final EarlyReturn er = new EarlyReturn();
		er.set(5);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 1;
				while (i <= 3) {
				
				try {
					Thread.sleep(1000);
					er.set(5 + i);
					i++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
			
		});
		
		
		
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 1;
				while (i <= 3) {
				
				try {
					Thread.sleep(1000);
					er.waitUntil(6, 3000);
					i++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
			
		});
		
		t.start();
		t1.start();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
