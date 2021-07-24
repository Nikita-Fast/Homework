package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static final String FILE_NAME = "resources\\input.txt";
    public static final int LENGTH = 1024;

    public static void main(String[] args) {
        //System.out.println("res == " + IntegralCalculator.calc(-1, 2, 9, 2, -3));
        ArrayList<Polynomial> computedPolynomials = new ArrayList<>();

        double[] numbers = new double[LENGTH];
        int i = 0;
        try (BufferedReader in = Files.newBufferedReader(Paths.get(FILE_NAME))) {
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
                    double res = task.compute();

                    /*double res = IntegralCalculator.calc(
                            numbers[0], numbers[1], Arrays.copyOfRange(numbers, 2, i));*/
                    System.out.println(res);
                    /*System.out.println("check = " + IntegralCalculator
                            .calc(numbers[0], numbers[1], Arrays.copyOfRange(numbers, 2, i)));*/
                }
            }
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
