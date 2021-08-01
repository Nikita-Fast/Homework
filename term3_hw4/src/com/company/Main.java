package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static final String INPUT_FILE = "resources\\test.txt";
    //public static final String OUTPUT_FILE = "resources\\output.txt";
    public static int parallelism = 4;

    public static void main(String[] args) throws IOException {

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(INPUT_FILE)));
        ConcurrentSkipListSet<Unit> doneCalculations = new ConcurrentSkipListSet();

        ArrayList<MyRecTask> tasks = new ArrayList<>();
        for (String line : lines) {
            tasks.add(new MyRecTask(line, doneCalculations));
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);
        for (MyRecTask task : tasks) {
            forkJoinPool.invoke(task);
        }
        forkJoinPool.shutdown();

        doneCalculations.forEach(System.out::println);
    }

}
