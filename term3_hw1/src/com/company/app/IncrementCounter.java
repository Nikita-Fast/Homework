package com.company.app;

import com.company.counters.Counter;

public class IncrementCounter implements Runnable{
    private final Counter counter;
    public static final int ITERATIONS = 100_000;

    public IncrementCounter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < ITERATIONS; i++) {
            counter.inc();
            /*int x = counter.getValue();
            System.out.println(x);*/
        }
    }
}
