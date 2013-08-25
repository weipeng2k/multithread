package com.murdock.books.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

import org.junit.Test;

public class ExchangerTest {

	private byte[] contexts = "Hello world!".getBytes();

	private Exchanger<Byte> byteExchanger = new Exchanger<Byte>();

	private List<Byte> buffer = new ArrayList<Byte>();

	private boolean over;

	class Reader implements Runnable {

		@Override
		public void run() {
			Byte current = null;
			for (byte b : contexts) {
				current = b;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					System.out.println(Thread.currentThread()
							+ " reader exchange " + (char) current.byteValue());
					current = byteExchanger.exchange(current);
					if (current != null) {
						System.out.println(Thread.currentThread()
								+ " reader exchanged "
								+ (char) current.byteValue());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			over = true;
		}
	}

	class Writer implements Runnable {
		@Override
		public void run() {
			Byte current = null;
			while (!over) {
				try {
					if (current != null) {
						System.out.println(Thread.currentThread()
								+ " writer exchange "
								+ (char) current.byteValue());
					}
					current = byteExchanger.exchange(current);
					System.out
							.println(Thread.currentThread()
									+ " writer exchanged "
									+ (char) current.byteValue());
					buffer.add(current);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Test
	public void test() {
		new Thread(new Reader()).start();
		new Thread(new Writer()).start();

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
