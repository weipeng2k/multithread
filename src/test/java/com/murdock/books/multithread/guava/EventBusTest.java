package com.murdock.books.multithread.guava;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

/**
 * @author weipeng2k 2017年01月10日 下午16:18:37
 */
public class EventBusTest {
    private EventBus eventBus = new EventBus();

    @Test
    public void send() {
        eventBus.register(new Processor1());
        eventBus.register(new Processor1());
        eventBus.register(new Processor2());

        eventBus.post(new SubEvent());
    }

}
