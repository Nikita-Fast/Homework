package com.company.tests;

import com.company.app.Solution;
import com.company.counters.Counter;
import com.company.counters.ThreadSafeCounter;
import com.company.lock.TASLock;
import com.company.lock.TTASLock;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class PerformanceTestTASvsTTAS {

    private final Counter counterWithTAS = new ThreadSafeCounter(new TASLock());
    private final Counter counterWithTTAS = new ThreadSafeCounter(new TTASLock());

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
}
