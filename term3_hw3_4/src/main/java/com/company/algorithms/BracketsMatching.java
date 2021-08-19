package com.company.algorithms;

import com.company.runnables.ConvertBracketToInt;
import com.company.operations.Addition;
import com.company.operations.FindMin;
import java.util.ArrayList;

public class BracketsMatching {

    public static boolean areBalancedInParallel(char[] brackets, int threadsNumber)
            throws InterruptedException {
        Integer[] array = new Integer[brackets.length];
        ArrayList<Thread> threads = new ArrayList<>();

        int from = 0;
        int to = brackets.length / threadsNumber;
        int step = brackets.length / threadsNumber;

        for (int j = 0; j < threadsNumber; j++) {
            if (j == threadsNumber - 1) {
                to = brackets.length;
            }
            Thread thread = new Thread(new ConvertBracketToInt(from, to, brackets, array));
            threads.add(thread);
            to += step;
            from += step;
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

        if (min >= 0 && result[result.length - 1] == 0) {
            return true;
        }
        return false;
    }

    public static boolean areBalancedInSingleThread(char[] brackets) {
        int sum = 0;
        for (int i = 0; i < brackets.length; i++) {
            if (brackets[i] != '(' && brackets[i] != ')') {
                throw new IllegalArgumentException("Input string consists not only from brackets!");
            }
            if (brackets[i] == '(') {
                sum += 1;
            }
            if (brackets[i] == ')') {
                sum -= 1;
            }
            if (sum < 0) {
                return false;
            }
        }
        return sum == 0;
    }
}
