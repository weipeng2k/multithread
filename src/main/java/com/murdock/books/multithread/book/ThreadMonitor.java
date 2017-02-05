package com.murdock.books.multithread.book;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadMonitor {

	/**
	 * 线程MXBean
	 */
	private ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	/**
	 * 线程时间线，其中队列按照新产生的在前，后产生的在后
	 */
	private ConcurrentHashMap<Long, Deque<ThreadInfoSnapshot>> threadTimeline = new ConcurrentHashMap<Long, Deque<ThreadInfoSnapshot>>();
	/**
	 * 产生的ThreadInfoSnapshot队列最大长度
	 */
	private int threadInfoSnapshotSize	= 60;
	/**
	 * 调度服务
	 */
	private ScheduledExecutorService scheduleExecutorService = Executors.newScheduledThreadPool(1);
	
	/**
	 * 启动监控
	 */
	public void start() {
		scheduleExecutorService.scheduleAtFixedRate(new ScheduleJob(), 0, 1, TimeUnit.SECONDS);
	}
	
	/**
	 * 停止监控
	 */
	public void stop() {
		scheduleExecutorService.shutdown();
	}

	/**
	 * 获取各个线程CPU使用比率
	 */
	public Collection<Deque<ThreadInfoSnapshot>> getCpuTimeInfo() {
		return threadTimeline.values();
	}

	private void snapshotThreads() {
		ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds());
		long time = System.currentTimeMillis();
		if (threadInfos != null) {
			for (ThreadInfo threadInfo : threadInfos) {
				if (threadInfo != null) {
					// 获取线程对应的threadInfoSnapshot队列
					Deque<ThreadInfoSnapshot> snapshotQueue = fetchThreadSnapshotQueue(threadInfo.getThreadId());
					long cpuTime = threadMXBean.getThreadCpuTime(threadInfo.getThreadId());
					ThreadInfoSnapshot current = new ThreadInfoSnapshot();
					current.setThreadId(threadInfo.getThreadId());
					current.setThreadName(threadInfo.getThreadName());
					current.setTime(time);
					current.setCpuTime(cpuTime);
					current.setThreadState(threadInfo.getThreadState().name());
					// 存在前驱节点，进行计算
					if (!snapshotQueue.isEmpty()) {
						// 前驱节点，计算时间差以及CPU耗时
						// [cpu time (current - previous)] / [time (current - previous)]
						ThreadInfoSnapshot previous = snapshotQueue.peekLast();
						if (previous != null) {
							long usedMillis = TimeUnit.NANOSECONDS.toMillis(current.getCpuTime()
									- previous.getCpuTime());
							long deltaMillis = current.getTime() - previous.getTime();

							current.setPercent(((double) usedMillis * 100) / deltaMillis);
						}
					}
					snapshotQueue.offerLast(current);
				}
			}
		}
	}

	private void cleanUpThreadInfo(long deadline) {
		List<Long> emptyKeyList = new ArrayList<Long>();
		for (Map.Entry<Long, Deque<ThreadInfoSnapshot>> entry : threadTimeline.entrySet()) {
			Deque<ThreadInfoSnapshot> list = entry.getValue();
			if (list != null) {
				Iterator<ThreadInfoSnapshot> iterator = list.iterator();
				while (iterator.hasNext()) {
					ThreadInfoSnapshot tis = iterator.next();
					// should remove
					if (tis.getTime() < deadline) {
						iterator.remove();
					}
				}
			}

			// 如果队列内容为空，则需要移除
			if (list == null || list.isEmpty()) {
				emptyKeyList.add(entry.getKey());
			}
		}

		for (Long emptyKey : emptyKeyList) {
			threadTimeline.remove(emptyKey);
		}
	}

	/**
	 * 获取线程对应的SnapshotQueue，如果没有会建立一个，该方法是线程安全的，队列是尾部添加，头部删除
	 */
	private Deque<ThreadInfoSnapshot> fetchThreadSnapshotQueue(Long threadId) {
		Deque<ThreadInfoSnapshot> snapshotQueue = threadTimeline.get(threadId);
		if (snapshotQueue == null) {
			snapshotQueue = new LinkedBlockingDeque<ThreadInfoSnapshot>();
			Deque<ThreadInfoSnapshot> oldOne = threadTimeline.putIfAbsent(threadId, snapshotQueue);
			// 该时刻，已经存在了，使用oldOne
			if (oldOne != null) {
				snapshotQueue = oldOne;
			}
		}

		// 如果满了腾出前面的位置
		while (snapshotQueue.size() >= threadInfoSnapshotSize) {
			snapshotQueue.pollFirst();
		}

		return snapshotQueue;
	}

	private class ScheduleJob implements Runnable {
		public void run() {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, -10);
			cleanUpThreadInfo(calendar.getTime().getTime());
			snapshotThreads();
		}
	}

}
