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

    @Setup
    public void fill() {
//       RWLCache.put("A", "B");
       CacheStamp.put("A", "B");
    }

    @Benchmark
    public void readWriteLock() {
//        RWLCache.get("A");
    }

    @Benchmark
    public void stampedLock() {
        CacheStamp.get("A");
    }


}
