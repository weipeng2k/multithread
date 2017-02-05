/**
 * 
 */
package com.murdock.books.multithread.book;

import java.util.Collection;
import java.util.Deque;

public class ThreadSnapshotUtils {
	public static void println(Collection<Deque<ThreadInfoSnapshot>> threadInfos) {
		if (threadInfos != null) {
			for (Deque<ThreadInfoSnapshot> item : threadInfos) {
				StringBuilder percent = new StringBuilder();
				long pid = item.getFirst().getThreadId();
				String threadName = item.getFirst().getThreadName();
				for (ThreadInfoSnapshot tis : item) {
					percent.append(String.format("%4s", " " + (int)tis.getPercent()) + "%");
				}
				String content = String.format("%4d%20s%s", pid, threadName, percent.toString());
				System.out.println(content);
			}
		}
	}
}
