package com.murdock.books.multithread;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}
	
	public static void main(String[] args) throws Exception {
		TimeUnit.SECONDS.sleep(30);
	}

	@org.junit.Test
	public void testTime() {
		System.out.println(System.nanoTime());
		System.out.println(Instant.now().getNano());
	}
}
