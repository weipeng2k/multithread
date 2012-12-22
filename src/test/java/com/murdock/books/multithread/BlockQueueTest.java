/**
 * 
 */
package com.murdock.books.multithread;

import org.junit.Test;

/**
 * @author weipeng
 * 
 */
public class BlockQueueTest {
	private BlockQueue<Object> bq = new BlockQueue<Object>(10);

	@Test
	public void add() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 5;
				while (i > 0) {
					bq.add(new Object());
					i--;
				}
			}
		});
		t.start();
		
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 5;
				while (i > 0) {
					bq.add(new Object());
					i--;
				}
			}
		});
		t1.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		bq.add(new Object());
	}
	
	@Test
	public void remove() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				int i = 5;
				while (i > 0) {
					bq.add(new Object());
					i--;
				}
			}
		});
		t1.start();
		
		
		bq.remove();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
