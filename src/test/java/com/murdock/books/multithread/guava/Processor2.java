package com.murdock.books.multithread.guava;

import com.google.common.eventbus.Subscribe;

/**
 * @author weipeng2k 2017年01月10日 下午16:20:49
 */
public class Processor2 {

    @Subscribe
    public void handleEvent(SubEvent2 event) {
        System.out.println("2" + event);
    }
}
