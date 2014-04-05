/**
 * 
 */
package com.murdock.books.multithread.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 * 默认的线程池实现，可以新增工作线程也可以减少工作线程
 * 
 * 当然提交JOB后会进入队列中，而Worker进行消费
 * 
 * 这是一个简单的生产和消费者模式
 * 
 * </pre>
 * 
 * @author weipeng
 * 
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

	/**
	 * 线程池最大限制数
	 */
	private static final int		MAX_WORKER_NUMBERS		= 10;
	/**
	 * 线程池默认的数量
	 */
	private static final int		DEFAULT_WORKER_NUMBERS	= 5;
	/**
	 * 线程池最小的数量
	 */
	private static final int		MIN_WORKER_NUMBERS		= 1;
	/**
	 * 这是一个工作列表，将会向里面插入工作
	 */
	private final LinkedList<Job>	jobs					= new LinkedList<Job>();
	/**
	 * 工作者列表
	 */
	private final List<Worker>		workers					= Collections.synchronizedList(new ArrayList<Worker>());
	/**
	 * 工作者线程的数量
	 */
	private int						workerNum				= DEFAULT_WORKER_NUMBERS;

	public DefaultThreadPool() {
		initializeWokers(DEFAULT_WORKER_NUMBERS);
	}

	public DefaultThreadPool(int num) {
		workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
		initializeWokers(workerNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.murdock.books.multithread.example.ThreadPool#execute(java.lang.Runnable
	 * )
	 */
	@Override
	public void execute(Job job) {
		if (job != null) {
			// 添加一个工作，然后进行通知
			synchronized (jobs) {
				jobs.addLast(job);
				jobs.notify();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.murdock.books.multithread.example.ThreadPool#shutdown()
	 */
	@Override
	public void shutdown() {
		for (Worker worker : workers) {
			worker.shutdown();
		}
	}

	@Override
	public void addWorkers(int workerNum) {
		synchronized (jobs) {
			int addedNum = workerNum;
			if (workerNum + this.workerNum > MAX_WORKER_NUMBERS) {
				addedNum = MAX_WORKER_NUMBERS - this.workerNum;
			}
			initializeWokers(addedNum);
			this.workerNum = this.workerNum + addedNum;
		}
	}

	@Override
	public void removeWorker(int workerNum) {
		if (workerNum >= this.workerNum) {
			throw new IllegalArgumentException("can not remove beyond workerNum. now num is " + this.workerNum);
		}

		synchronized (jobs) {
			int count = 0;
			while (count < workerNum) {
				workers.get(count).shutdown();
				count++;
			}

			this.workerNum = this.workerNum - count;
		}
	}

	@Override
	public int getJobSize() {
		return jobs.size();
	}

	/**
	 * 初始化线程工作者
	 */
	private void initializeWokers(int num) {
		for (int i = 0; i < num; i++) {
			Worker worker = new Worker();
			workers.add(worker);

			Thread thread = new Thread(worker);
			thread.start();
		}
	}

	/**
	 * <pre>
	 * 工作者，负责消费任务
	 * 
	 * </pre>
	 */
	class Worker implements Runnable {
		/**
		 * 工作
		 */
		private volatile boolean	running	= true;

		@Override
		public void run() {
			while (running) {

				Job job = null;
				synchronized (jobs) {
					// 如果工作者列表是空的，那么就wait，放弃cpu执行占用
					while (jobs.isEmpty()) {
						try {
							jobs.wait();
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
							return;
						}
					}

					// 取出一个Job
					job = jobs.removeFirst();
				}
				if (job != null) {
					try {
						job.run();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		public void shutdown() {
			running = false;
		}

	}
}
