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
public class CopyList {
	static List<String> list = new ArrayList<String>();

	static {
		for (int i = 0; i < 1000; i++) {
			list.add("" + i);
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<String> synchronizedList = Collections.synchronizedList(list);

		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			String[] array = synchronizedList.toArray(new String[0]);
		}
		System.out.println(System.currentTimeMillis() - currentTime);

		currentTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			synchronized (synchronizedList) {
				int size = synchronizedList.size();
				String[] array = new String[size];
				synchronizedList.toArray(array);
			}
		}
		System.out.println(System.currentTimeMillis() - currentTime);
		
		currentTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			String[] array = synchronizedList.toArray(new String[0]);
		}
		System.out.println(System.currentTimeMillis() - currentTime);
		
		currentTime = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			synchronized (synchronizedList) {
				int size = synchronizedList.size();
				String[] array = new String[size];
				synchronizedList.toArray(array);
			}
		}
		System.out.println(System.currentTimeMillis() - currentTime);
	}

}
