package com.company.counters;

import java.util.concurrent.locks.Lock;

public class ThreadSafeCounter extends Counter {
    private int value;
    private final Lock lock;

    public ThreadSafeCounter(Lock lock) {
        this.value = 0;
        this.lock = lock;
    }

    public void inc() {
        lock.lock();
        try {
            value++;
        }
        finally {
            lock.unlock();
        }
    }

    public int getValue() {
        return value;
    }
}
