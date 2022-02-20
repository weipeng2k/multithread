package com.murdock.books.multithread.book;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Benchmark                          Mode  Cnt           Score          Error  Units
 * StampedLockJMHTest.readWriteLock  thrpt    9     5122977.127 ±    97802.843  ops/s
 * StampedLockJMHTest.stampedLock    thrpt    9  6631512202.228 ± 90757983.241  ops/s
 *
 * @author weipeng2k 2022年02月19日 下午22:19:25
 */
public class StampedLockJMHRunner {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include("StampedLockJMH")
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(3)
                .threads(10)
                .build();

        new Runner(opt).run();
    }
}
