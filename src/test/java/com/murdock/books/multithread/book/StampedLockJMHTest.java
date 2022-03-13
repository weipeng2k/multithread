package com.murdock.books.multithread.book;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 * @author weipeng2k 2022年02月19日 下午22:18:06
 */
@State(Scope.Benchmark)
public class StampedLockJMHTest {

    private Cache<String, String> rwlCache = new RWLCache<>();
    private Cache<String, String> slCache = new SLCache<>();

    @Setup
    public void fill() {
        rwlCache.put("A", "B");
        slCache.put("A", "B");
    }

    @Benchmark
    public void readWriteLock() {
        rwlCache.get("A");
    }

    @Benchmark
    public void stampedLock() {
        slCache.get("A");
    }


}
