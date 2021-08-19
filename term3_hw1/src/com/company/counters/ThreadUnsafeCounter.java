package com.company.counters;

public class ThreadUnsafeCounter extends Counter {
    private int value;

    public ThreadUnsafeCounter() {
        this.value = 0;
    }

    public void inc() {
        value++;
    }

    public int getValue() {
        return value;
    }
}
