package com.murdock.books.multithread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Test;

public class ExecutorTaskTest {

	Executor exec = Executors.newFixedThreadPool(10);

	@Test
	public void test() {
		for (int i = 0; i < 100; i++) {
			exec.execute(new Task());
		}
	}

	class Task implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread() + ": call me.");
		}
	}

}
