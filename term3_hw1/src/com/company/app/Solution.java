package com.company.app;

import com.company.counters.Counter;

import java.util.ArrayList;

public class Solution {

    public static int incCounter(int threadsNumber, Counter counter) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new IncrementCounter(counter));
            thread.setName("" + i);
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return counter.getValue();
    }
}
