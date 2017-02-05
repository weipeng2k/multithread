package com.murdock.books.multithread.book;

/**
 * <pre>
 * 表示一个Thread在某一时刻的快照，这里目前包括了线程名称，Id等属性
 * 减少该类的成员变量，减轻缓存ThreadSnaphostQueue的占用内存
 * 
 * </pre>
 * 
 * @author weipeng2k 2014年2月8日 下午5:21:19
 */
public class ThreadInfoSnapshot {

	/**
	 * 线程名称
	 */
	private String	threadName;
	/**
	 * 线程状态
	 */
	private String	threadState;
	/**
	 * 线程Id
	 */
	private long	threadId;
	/**
	 * 统计的时刻，单位millis
	 */
	private long	time;
	/**
	 * CPU耗时，单位nano
	 */
	private long	cpuTime;
	/**
	 * 耗时百分比，整数
	 */
	private double	percent;

	public long getTime() {
		return time;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getCpuTime() {
		return cpuTime;
	}

	public void setCpuTime(long cpuTime) {
		this.cpuTime = cpuTime;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getThreadState() {
		return threadState;
	}

	public void setThreadState(String threadState) {
		this.threadState = threadState;
	}

	@Override
	public String toString() {
		return "ThreadInfoSnapshot [threadName=" + threadName + ", threadState=" + threadState + ", threadId="
				+ threadId + ", time=" + time + ", cpuTime=" + cpuTime + ", percent=" + percent + "]";
	}

}
