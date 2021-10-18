/**
 * 
 */
package com.murdock.books.multithread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.stream.IntStream;

/**
 * @author weipeng
 * 
 */
public class NoVisibilityTest {
	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {

		public void run() {
			while (!ready) {
			}
			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		// 获取Java线程管理MXBean
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

		IntStream.range(0, 15)
				.forEach( i -> {
					ReaderThread readerThread = new ReaderThread();
					readerThread.setName("Reader:" + i);
					readerThread.start();
				});

		number = 42;
		ready = true;


		for (int i = 0; i < 20; i++) {
			// 不需要获取同步的monitor和synchronizer信息，仅仅获取线程和线程堆栈信息
			ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
			// 遍历线程信息，仅打印线程ID和线程名称信息
			for (ThreadInfo threadInfo : threadInfos) {
				System.out.println("[" + threadInfo.getThreadId() + "] "
						+ threadInfo.getThreadName() + ", status:" + threadInfo.getThreadState());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Ignore.
			}

			System.out.println("=============" + i + "===============");
		}
	}
}
