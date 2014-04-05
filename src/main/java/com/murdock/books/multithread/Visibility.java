package com.murdock.books.multithread;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Visibility {
	
	private static  boolean stop = false;
	
	public static void main(String[] args) throws Exception {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				long start = System.nanoTime();
				while (!stop) {
					new HashMap<String, Object>();
				}
				System.out.println("Stop at " + (System.nanoTime() -  start));
			}
			
		});
		thread.start();
		
		TimeUnit.SECONDS.sleep(2);
		
		stop = true;
		
		TimeUnit.SECONDS.sleep(2);
	}
	
	static void dummy(Object obj) {
		obj.hashCode();
	}

}
