package com.company.tests;

import com.company.app.Solution;
import com.company.counters.Counter;
import com.company.counters.ThreadSafeCounter;
import com.company.lock.BackoffLock;
import com.company.lock.TASLock;
import com.company.lock.TTASLock;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(time = 5)
@Measurement(time = 5)
public class PerformanceTestTASvsTTASvsBackoff {

    private final Counter counterWithTAS = new ThreadSafeCounter(new TASLock());
    private final Counter counterWithTTAS = new ThreadSafeCounter(new TTASLock());
    private final Counter counterWithBackoff = new ThreadSafeCounter(new BackoffLock());

    @Param({"1", "2", "4", "8", "16"})
    public int THREADS_NUMBER;

    @Benchmark
    public int testTAS() throws InterruptedException {
        return Solution.incCounter(THREADS_NUMBER, counterWithTAS);
    }

    @Benchmark
    public int testTTAS() throws InterruptedException {
        return Solution.incCounter(THREADS_NUMBER, counterWithTTAS);
    }

    @Benchmark
    public int testBackoff() throws InterruptedException {
        return Solution.incCounter(THREADS_NUMBER, counterWithBackoff);
    }
}
