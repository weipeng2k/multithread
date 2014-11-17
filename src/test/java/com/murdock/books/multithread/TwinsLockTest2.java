package com.murdock.books.multithread;

import java.util.concurrent.locks.Lock;

import com.murdock.books.multithread.book.SleepUtils;
import com.murdock.books.multithread.lock.TwinsLock;

public class TwinsLockTest2 {

	public static void main(String[] args) throws InterruptedException {
		final Lock lock = new TwinsLock();
		class Client extends Thread {
			public void run() {
				while (true) {
					lock.lock();
					System.out.println(Thread.currentThread().getName() + " got lock.");
					try {
						SleepUtils.second(1);
					} finally {
						System.out.println(Thread.currentThread().getName() + " release lock.");
						lock.unlock();
					}
				}
			}
		}

		for (int i = 1; i <= 10; i++) {
			Client client = new Client();
			client.setName("Client-" + i);
			client.setDaemon(true);
			client.start();
		}
		for (int i = 0; i < 30; i++) {
			System.out.println();
			SleepUtils.second(1);
		}
	}
}
