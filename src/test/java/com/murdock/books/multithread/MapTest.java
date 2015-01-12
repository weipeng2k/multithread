package com.murdock.books.multithread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class MapTest {
	private static ConcurrentHashMap<String, String>	map	= new ConcurrentHashMap<String, String>();
	
	static {
		for (int i = 0; i < 10; i++) {
			map.put(String.valueOf(i), String.valueOf(i));
		}
	}

	@Test
	public void test() {
		Map<String, String> copy = new HashMap<String, String>(map);
		Map<String, String> unmodified = Collections.unmodifiableMap(map);
		
		System.out.println(copy.size());
		System.out.println(unmodified.size());
		
		System.out.println("=============add map=============");
		
		map.put("10", "10");
		
		System.out.println(copy.size());
		System.out.println(unmodified.size());
	}

}
