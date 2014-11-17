package com.murdock.books.multithread.book;

import java.util.concurrent.locks.Lock;

public class MutexTest {

	public static void main(String[] args) {
		Lock lock = new Mutex();
		lock.lock();
		try {
			lock.lock();
			try {
				
			} finally {
				lock.unlock();
			}
		} finally {
			lock.unlock();
		}
	}

}
