package com.company.tests;

import com.company.app.MyTask;
import com.company.app.PolynomialWithData;
import com.company.app.Solution;
import com.company.concurrent.LazyList;
import com.company.concurrent.OptimisticList;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class PerformanceTest {
    public static final String INPUT_FILE = "resources\\input.txt";
    /*public ConcurrentSkipListSet<PolynomialWithData> calculatedPolynomials;
    public ArrayList<MyTask> tasks;*/

    @Param({"16","8", "4", "2", "1"})
    public int parallelism;

    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void concurrentSkipListSetOwnThreadPool() throws IOException, InterruptedException {
        Solution.compute(INPUT_FILE, parallelism);
    }

    /*@Setup(Level.Invocation)
    public void prepare() throws IOException {
        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(INPUT_FILE)));
        calculatedPolynomials = new ConcurrentSkipListSet<>();

        tasks = new ArrayList<>();
        for (String line : lines) {
            tasks.add(new MyTask(line, calculatedPolynomials));
        }
    }*/

}
