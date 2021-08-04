package com.company.app;

import com.company.app.MyTask;
import com.company.app.PolynomialWithData;
import com.company.concurrent.LazyList;
import com.company.concurrent.MyThreadPool;
import com.company.concurrent.OptimisticList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Solution {

    public static ConcurrentSkipListSet<PolynomialWithData> compute(String pathToInputFile, int parallelism) throws IOException, InterruptedException {
        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(pathToInputFile)));
        ConcurrentSkipListSet<PolynomialWithData> calculatedPolynomials = new ConcurrentSkipListSet<>();
        //LazyList<PolynomialWithData> calculatedPolynomials = new LazyList<>();
        //OptimisticList<PolynomialWithData> calculatedPolynomials = new OptimisticList<>();

        ArrayList<MyTask> tasks = new ArrayList<>();
        for (String line : lines) {
            tasks.add(new MyTask(line, calculatedPolynomials));
        }
        //ExecutorService executorService = Executors.newFixedThreadPool(parallelism);
        MyThreadPool threadPool = new MyThreadPool(parallelism);
        for (MyTask task : tasks) {
            //executorService.execute(task);
            threadPool.execute(task);
        }
        //executorService.shutdown();
        threadPool.stop();
        //waits until all tasks are finished
        synchronized (threadPool.getThreadsDone()) {
            threadPool.getThreadsDone().wait();
        }
        //executorService.awaitTermination(2, TimeUnit.SECONDS);

        return calculatedPolynomials;
    }
}
