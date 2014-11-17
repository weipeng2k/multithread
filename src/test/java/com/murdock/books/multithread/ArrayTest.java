package com.murdock.books.multithread;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ArrayTest {

	@Test
	public void test() {

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = -1; j < i; j++) {
				sb.append((char) (j + 100));
			}
			list.add(sb.toString());
		}
		System.out.println(list);
		for (int i = 0; i < 100000; i++) {
			list.toArray(new String[10]);
			list.toArray(new String[0]);
		}
		
		long start = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			list.toArray(new String[0]);
		}
		System.out.println(System.nanoTime() - start);
		
		start = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			list.toArray(new String[10]);
		}
		System.out.println(System.nanoTime() - start);
		
		

	}

}
