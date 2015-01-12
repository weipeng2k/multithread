package com.murdock.books.multithread.book;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class LockSupportTest {
	
	@Test
	public void park() {
		LockSupport.parkNanos(this, TimeUnit.SECONDS.toNanos(10));
	}

}
