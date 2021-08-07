package com.company.app;

import com.company.counters.Counter;

public class IncrementCounter implements Runnable{
    private final Counter counter;
    public static final int ITERATIONS = 200_000;

    public IncrementCounter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < ITERATIONS; i++) {
            counter.inc();
        }
    }
}
