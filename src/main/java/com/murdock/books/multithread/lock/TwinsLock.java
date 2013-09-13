/**
 * 
 */
package com.murdock.books.multithread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {
	private static final Sync sync = new Sync();

	private static final class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = -7889272986162341211L;
		{
			setState(2);
		}

		protected boolean tryAcquire(int arg) {
			if (arg != 1) {
				return false;
			}
			int currentStats = getState();
			if (currentStats <= 0) {
				return false;
			}
			if (compareAndSetState(currentStats, currentStats - 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		protected boolean tryRelease(int arg) {
			if (arg != 1) {
				return false;
			}
			for (;;) {
				int currentStats = getState();
				if (compareAndSetState(currentStats, currentStats + 1)) {
					setExclusiveOwnerThread(null);
					return true;
				}
			}
		}

		protected boolean isHeldExclusively() {
			return getState() < 2;
		}

		final ConditionObject newCondition() {
			return new ConditionObject();
		}

	}

	public void lock() {
		sync.acquire(1);
	}

	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
}
