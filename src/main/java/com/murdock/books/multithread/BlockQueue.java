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
public class BlockQueue<T> {
	
	public BlockQueue(int capacity) {
		size = capacity;
	}
	
	int size = 10;
	
	List<T> items = new ArrayList<T>();
	
	public void add(T t) {
		synchronized (items) {
			while (items.size() >= size) {
				try {
					items.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Insert " + t);
			items.add(t);
			
			items.notifyAll();
		}
	}
	
	public T remove() {
		synchronized (items) {
			while (items.isEmpty()) {
				try {
					items.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			T result =  items.remove(0);
			System.out.println("output " + result);
			items.notifyAll();
			
			return result;
		}
	}
}
