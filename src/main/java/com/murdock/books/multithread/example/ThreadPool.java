/**
 * 
 */
package com.murdock.books.multithread.example;

/**
 * @author weipeng
 */
public interface ThreadPool<Job extends Runnable> {

	/**
	 * <pre>
	 * 执行一个Job，这个Job需要实现Runnable
	 * 
	 * </pre>
	 * 
	 * @param job
	 */
	void execute(Job job);

	/**
	 * <pre>
	 * 关闭线程池
	 * 
	 * </pre>
	 */
	void shutdown();

	/**
	 * <pre>
	 * 增加工作线程
	 * 
	 * </pre>
	 * 
	 * @param workerNum
	 */
	void addWorkers(int workerNum);

	/**
	 * <pre>
	 * 减少工作线程
	 * 
	 * </pre>
	 * 
	 * @param workerNum
	 */
	void removeWorker(int workerNum);

	/**
	 * <pre>
	 * 得到Jobs的列表
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	int getJobSize();

}
