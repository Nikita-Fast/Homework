package com.company.algorithms;

import com.company.tasks.ConvertBracketToInt;
import com.company.operations.Addition;
import com.company.operations.FindMin;
import java.util.ArrayList;

public class BracketsMatching {

    public static boolean areBalancedInParallel(char[] brackets, int threadsNumber)
            throws InterruptedException {
        Integer[] array = new Integer[brackets.length];
        ArrayList<Thread> threads = new ArrayList<>();

        for (int j = 0; j < threadsNumber; j++) {
            Thread thread = new Thread(new ConvertBracketToInt(brackets, array, j, threadsNumber));
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();

        Integer[] result = new Integer[brackets.length];
        ParallelPrefixScanner<Integer> prefixScanner = new ParallelPrefixScanner<>();
        prefixScanner.doScan(threadsNumber, array, result, new Addition());

        ParallelScanner<Integer> parallelScanner = new ParallelScanner<>();
        int min = parallelScanner.doScan(threadsNumber, result, new FindMin());

        return min >= 0 && result[result.length - 1] == 0;
    }

    public static boolean areBalancedInSingleThread(char[] brackets) {
        int sum = 0;
        for (char bracket : brackets) {
            if (bracket != '(' && bracket != ')') {
                throw new IllegalArgumentException("Input string consists not only from brackets!");
            }
            if (bracket == '(') {
                sum += 1;
            }
            if (bracket == ')') {
                sum -= 1;
            }
            if (sum < 0) {
                return false;
            }
        }
        return sum == 0;
    }
}
