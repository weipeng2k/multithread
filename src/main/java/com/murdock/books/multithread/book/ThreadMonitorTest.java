package com.murdock.books.multithread.book;

public class ThreadMonitorTest {

	public static void main(String[] args) throws Exception {
		ThreadMonitor monitor = new ThreadMonitor();
		monitor.start();
		for (int i = 0; i < 10; i++) {
			final long sleep = (i + 1) * 10;
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(sleep);
							int count = 0;
							for (int j = 0; j < 100000000; j++) {
								count++;
							}
							System.setProperty("T", "" + count);
						} catch (Exception ex) {
						}
					}
				}
			}, "Thread-" + i);
			thread.setDaemon(true);
			thread.start();
		}
		
		Thread.sleep(10000);

		for (int i = 0; i < 10; i++) {
			ThreadSnapshotUtils.println(monitor.getCpuTimeInfo());
			Thread.sleep(10000);
		}
		
		monitor.stop();
	}
}
