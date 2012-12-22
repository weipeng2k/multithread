package com.murdock.books.multithread;

import java.util.concurrent.locks.Lock;

import org.junit.Test;

import com.murdock.books.multithread.lock.TASLock;
import com.murdock.books.multithread.lock.TTASLock;

/**
 * <pre>
 * TTAS > TAS
 * 
 * </pre>
 * 
 * @author weipeng
 *
 */
public class TasTest {

    long al = 0;

    /**
     * 56785841
     */
    @Test
    public void tasTest() {

	Lock lock = new TASLock();
	for (int i = 0; i < 10; i++) {
	    Count count = new Count();
	    count.lock = lock;
	    Thread thread = new Thread(count);
	    thread.start();
	}

	try {
	    Thread.sleep(10000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	System.out.println(al);

    }

    /**
     * 81565162
     */
    @Test
    public void ttasTest() {

	Lock lock = new TTASLock();
	for (int i = 0; i < 10; i++) {
	    Count count = new Count();
	    count.lock = lock;
	    Thread thread = new Thread(count);
	    thread.start();
	}

	try {
	    Thread.sleep(10000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	System.out.println(al);

    }

    class Count implements Runnable {

	Lock lock;

	@Override
	public void run() {
	    while (true) {
		lock.lock();

		try {
		    al++;
		} finally {
		    lock.unlock();
		}
	    }
	}
    }
}
