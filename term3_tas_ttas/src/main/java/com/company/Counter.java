package com.company;

import java.util.concurrent.locks.Lock;

public class Counter {
    public static final int LIMIT = 1_00_000;
    private int value;
    private Lock lock;

    public Counter(int value, Lock lock) {
        this.value = value;
        this.lock = lock;
    }

    public int get() {
        return value;
    }

    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }
}

