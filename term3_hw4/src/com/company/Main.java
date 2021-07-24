package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static final String FILE_NAME = "resources\\input.txt";
    public static final int LENGTH = 1024;

    public static void main(String[] args) {
        CopyOnWriteArrayList<Polynomial> computedPolynomials = new CopyOnWriteArrayList<>();
        ForkJoinPool fjp = new ForkJoinPool(2);
        double[] numbers = new double[LENGTH];
        int i = 0;
        try (BufferedReader in = Files.newBufferedReader(Paths.get(FILE_NAME))) {
            Charset charset = StandardCharsets.UTF_8;
            try (BufferedWriter writer = Files.
                    newBufferedWriter(Paths.get("resources\\output.txt"), charset)) {
                String line;
                while ((line = in.readLine()) != null) {
                    i = 0;
                    try (Scanner scanner = new Scanner(line)) {
                        while (scanner.hasNext()) {
                            numbers[i++] = scanner.nextDouble();
                        }
                        PolynomialRecComputation task = new PolynomialRecComputation(
                                new Polynomial(Arrays.copyOfRange(numbers, 2, i), numbers[0], numbers[1]),
                                computedPolynomials
                        );
                        double res = fjp.invoke(task);//task.compute();
                        writer.write(Double.toString(res) + "\n");

                        System.out.println(res);
                    System.out.println("check = " + IntegralCalculator
                            .calc(numbers[0], numbers[1], Arrays.copyOfRange(numbers, 2, i)));
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
