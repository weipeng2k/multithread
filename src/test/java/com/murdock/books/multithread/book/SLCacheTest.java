package com.murdock.books.multithread.book;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author weipeng2k 2022年03月05日 下午23:25:36
 */
public class SLCacheTest {

    private Cache<String, String> cache = new SLCache<>();

    @Test
    public void put_if_absent() {
        String s = cache.putIfAbsent("A", "A");
        assertNull(s);

        String s1 = cache.putIfAbsent("A", "B");
        assertEquals("A", s1);
    }

}