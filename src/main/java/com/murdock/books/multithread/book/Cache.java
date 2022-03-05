package com.murdock.books.multithread.book;

/**
 * <pre>
 * Mem cache
 * </pre>
 *
 * @author weipeng2k 2022年03月04日 下午15:04:04
 */
public interface Cache<K, V> {

    V get(K k);

    V put(K k, V v);

    V putIfAbsent(K k, V v);

    void clear();

}
