package com.murdock.books.multithread.book;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLCache<K, V> implements Cache<K, V> {
    private final Map<K, V> map = new HashMap<>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    @Override
    public V get(K k) {
        r.lock();
        try {
            return map.get(k);
        } finally {
            r.unlock();
        }
    }

    @Override
    public V put(K k, V v) {
        w.lock();
        try {
            return map.put(k, v);
        } finally {
            w.unlock();
        }
    }

    @Override
    public V putIfAbsent(K k, V v) {
        w.lock();
        try {
            return map.putIfAbsent(k, v);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }
}
