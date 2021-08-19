package com.company.app;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Backoff {
    final int minDelay;
    final int maxDelay;
    int limit;
    final Random random;

    public Backoff(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        limit = minDelay;
        this.random = new Random();
    }

    public void backoff() throws InterruptedException {
        int delay = ThreadLocalRandom.current().nextInt(minDelay, maxDelay);
        limit = Math.min(maxDelay, 2 * limit);
        Thread.sleep(0, delay);
    }
}
