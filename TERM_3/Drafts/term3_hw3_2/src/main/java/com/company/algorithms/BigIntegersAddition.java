package com.company.algorithms;

import com.company.runnables.BuildResult;
import com.company.runnables.ConvertStringsToIntArrays;
import com.company.runnables.FillCarryStateArray;
import com.company.runnables.PerformAddition;
import com.company.auxiliaries.CarryState;
import com.company.operations.CarryAddition;

import java.util.ArrayList;
import java.util.Arrays;

public class BigIntegersAddition {

    public static int[] addInParallelFromIntArrays(int threadsNumber, int[] number1, int[] number2) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        int max = Math.max(number1.length, number2.length);
        int[] num1 = Arrays.copyOf(number1, max);
        int[] num2 = Arrays.copyOf(number2, max);

        CarryState[] carryStates = new CarryState[max];
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new FillCarryStateArray(carryStates, num1, num2, i, threadsNumber));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        //calculate caries
        CarryState[] resultCarryStates = new CarryState[max];
        ParallelPrefixScanner<CarryState> prefixScanner = new ParallelPrefixScanner<>();
        prefixScanner.doScan(threadsNumber, carryStates, resultCarryStates, new CarryAddition());

        //perform addition
        int[] result = new int[max + 1];
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new PerformAddition(result, num1, num2, resultCarryStates, i, threadsNumber));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        return result;
    }


    public static String addInParallel(int threadsNumber, String s1, String s2) throws InterruptedException {
        //convert strings to int[]
        int max = Math.max(s1.length(), s2.length());
        int[] num1 = new int[max];
        int[] num2 = new int[max];

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new ConvertStringsToIntArrays(s1, s2, num1, num2, i, threadsNumber));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        //fill CarryState[]
        CarryState[] carryStates = new CarryState[max];
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new FillCarryStateArray(carryStates, num1, num2, i, threadsNumber));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        //calculate caries
        CarryState[] resultCarryStates = new CarryState[max];
        ParallelPrefixScanner<CarryState> prefixScanner = new ParallelPrefixScanner<>();
        prefixScanner.doScan(threadsNumber, carryStates, resultCarryStates, new CarryAddition());

        //perform addition
        int[] result = new int[max + 1];
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new PerformAddition(result, num1, num2, resultCarryStates, i, threadsNumber));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        //build parts of result String
        StringBuilder[] stringBuilders = new StringBuilder[threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            stringBuilders[i] = new StringBuilder();
        }
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new BuildResult(result, i, threadsNumber, stringBuilders[threadsNumber - i - 1]));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        //compose result String
        for (int i = 1; i < threadsNumber; i++) {
            stringBuilders[0].append(stringBuilders[i]);
        }
        return stringBuilders[0].toString();
    }

    public static String addWithSingleThread(String s1, String s2) {
        int max = Math.max(s1.length(), s2.length());
        char[] a = s1.toCharArray();
        int[] num1 = new int[max];
        for (int i = 0; i < a.length; i++) {
            num1[i] = Integer.parseInt(String.valueOf(a[a.length - i - 1]));
        }
        char[] b = s2.toCharArray();
        int[] num2 = new int[max];
        for (int i = 0; i < b.length; i++) {
            num2[i] = Integer.parseInt(String.valueOf(b[b.length - i - 1]));
        }

        int c = 0;
        int[] result = new int[max + 1];
        for (int i = 0; i < max; i++) {
            result[i] = (num1[i] + num2[i] + c) % 10;
            c = (num1[i] + num2[i] + c) / 10;
        }
        result[max] = c;

        return new StringBuilder(Arrays.toString(result)).reverse().toString();
    }

    private static void startAllThreadsAndWaitForResults(ArrayList<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();
    }
}
