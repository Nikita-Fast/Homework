package com.company.algorithms;

import com.company.threads.BracketsMatchingThread;
import com.company.auxiliaries.Cell;
import com.company.auxiliaries.Result;

public class BracketsMatching {

    public static boolean calculateInParallel(int threadsNumber, Integer[] array) {
        Cell<Integer>[][] sendReceiveTable1 = initSendReceiveTable(threadsNumber);
        Cell<Integer>[][] sendReceiveTable2 = initSendReceiveTable(threadsNumber);
        Result<Integer> minValue = new Result<>();
        Thread[] threads = prepareThreads(threadsNumber, array, sendReceiveTable1, sendReceiveTable2, minValue);
        startThreads(threads);
        return bracketsAreBalanced(minValue, array);
    }

    private static boolean bracketsAreBalanced(Result<Integer> minValue, Integer[] array) {
        int length = array.length;
        return minValue.getResult() >= 0 && array[length - 1] == 0;
    }

    public static boolean calculateInOneThread(Integer[] array) throws Exception {
        int s = 0;
        for (Integer integer : array) {
            if (integer != 1 && integer != -1) {
                throw new Exception("Array should have only 1 and -1 that stands for ( and )");
            }
            s += integer;
            if (s < 0) {
                return false;
            }
        }
        return s == 0;
    }

    private static void startThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Thread[] prepareThreads(int threadsNumber, Integer[] array, Cell<Integer>[][] sendReceiveTable1,
                                           Cell<Integer>[][] sendReceiveTable2, Result<Integer> result) {
        int step = array.length / threadsNumber;
        int min = 0;
        int max = step - 1;
        Thread[] threads = new Thread[threadsNumber];

        for (int i = 0; i < threadsNumber; i++) {
            threads[i] = new BracketsMatchingThread(min, max, array, i, sendReceiveTable1, sendReceiveTable2,
                    threadsNumber, result);
            min += step;
            max += step;
            if (i == threadsNumber - 2) {
                max = array.length - 1;
            }
        }
        return threads;
    }

    private static Cell<Integer>[][] initSendReceiveTable(int threadsNumber) {
        Cell<Integer>[][] table = new Cell[threadsNumber][threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            for (int j = 0; j < threadsNumber; j++) {
                table[i][j] = new Cell<>();
            }
        }
        return table;
    }
}
