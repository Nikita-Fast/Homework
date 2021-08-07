package com.company.algorithms;

import com.company.operations.StringBuilderAddition;
import com.company.tasks.BuildResult;
import com.company.tasks.ConvertStringsToIntArrays;
import com.company.tasks.FillCarryStateArray;
import com.company.tasks.PerformAddition;
import com.company.auxiliaries.CarryState;
import com.company.operations.CarryAddition;
import java.util.ArrayList;

public class BigIntegersAddition {

    public static String addInParallel(int threadsNumber, String s1, String s2) throws InterruptedException {
        //convert strings to int[]
        ArrayList<Thread> threads = new ArrayList<>();
        int max = Math.max(s1.length(), s2.length());

        int[] num1 = new int[max];
        int[] num2 = new int[max];

        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new ConvertStringsToIntArrays(s1, s2, num1, num2, i, threadsNumber));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        //fill carryState array
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

        return reverseAndConvertToStringParallel(result, threadsNumber);
    }

    public static String reverseAndConvertToStringParallel(int[] res, int threadsNumber) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        StringBuilder[] stringBuilders = new StringBuilder[threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            stringBuilders[i] = new StringBuilder();
        }
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new BuildResult(res, i, threadsNumber, stringBuilders[threadsNumber - i - 1]));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        ParallelScanner<StringBuilder> scanner = new ParallelScanner<>();
        StringBuilder sb = scanner.doScan(threadsNumber, stringBuilders, new StringBuilderAddition());
        return sb.toString();
    }

    /*public static int[] convertStringToIntArrAndReverse(String s1) {
        int[] arr1 = new int[s1.length()];
        for (int i = 0; i < s1.length(); i++) {
            arr1[arr1.length - 1 - i] = Character.digit(s1.charAt(i), 10);
        }
        return arr1;
    }*/

   /* public static String reverseAndConvertToString(int[] res, int threadsNumber) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        StringBuilder[] stringBuilders = new StringBuilder[threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            stringBuilders[i] = new StringBuilder();
        }
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new BuildResult(res, i, threadsNumber, stringBuilders[threadsNumber - i - 1]));
            threads.add(thread);
        }
        startAllThreadsAndWaitForResults(threads);

        //compose result String
        for (int i = 1; i < threadsNumber; i++) {
            stringBuilders[0].append(stringBuilders[i]);
        }
        return stringBuilders[0].toString();
    }*/

    public static String addWithSingleThread(String s1, String s2) {
        int max = Math.max(s1.length(), s2.length());
        int[] num1 = new int[max];
        for (int i = 0; i < s1.length(); i++) {
            num1[i] = Character.digit(s1.charAt(s1.length() - 1 - i), 10);

        }
        int[] num2 = new int[max];
        for (int i = 0; i < s2.length(); i++) {
            num2[i] = Character.digit(s2.charAt(s2.length() - 1 - i), 10);
        }

        int c = 0;
        int[] result = new int[max + 1];
        for (int i = 0; i < max; i++) {
            result[i] = (num1[i] + num2[i] + c) % 10;
            c = (num1[i] + num2[i] + c) / 10;
        }
        result[max] = c;

        StringBuilder sb = new StringBuilder();
        boolean leadingZeros = true;
        for (int i = result.length - 1; i >= 0; i--) {
            if (leadingZeros && result[i] != 0) {
                leadingZeros = false;
            }
            if (!leadingZeros) {
                sb.append(result[i]);
            }
        }
        return sb.toString();
    }

    public static void startAllThreadsAndWaitForResults(ArrayList<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();
    }
}
