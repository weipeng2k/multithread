package com.murdock.books.multithread.book;

public class ThreadNotify {

	public static void main(String[] args) throws Exception {
		Runner r = new Runner();
		r.thread = Thread.currentThread();
		Thread thread = new Thread(r);
		thread.start();

		Thread.sleep(1000);
	}

	static class Runner implements Runnable {
		Thread thread;

		@Override
		public void run() {
			synchronized (thread) {
				try {
					thread.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Got");
			}
		}

	}

}
