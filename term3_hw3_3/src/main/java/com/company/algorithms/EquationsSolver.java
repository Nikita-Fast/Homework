package com.company.algorithms;

import com.company.auxiliaries.Cell;
import com.company.auxiliaries.MyPair;
import com.company.auxiliaries.Result;
import com.company.operations.CoefficientsMix;
import com.company.threads.ParallelScanThread;

public class EquationsSolver {

    public static void calculateStatic(int threadsNumber, MyPair<Integer>[] array, Result<MyPair<Integer>> saveResultHere) {
        Cell<MyPair<Integer>>[][] sendReceiveTable = initSendReceiveTableStatic(threadsNumber);
        Thread[] threads = initThreadsStatic(threadsNumber, array, sendReceiveTable, saveResultHere);
        startThreadsStatic(threads);
    }

    private static void startThreadsStatic(Thread[] threads) {
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

    private static Thread[] initThreadsStatic(int threadsNumber, MyPair<Integer>[] array,
                                          Cell<MyPair<Integer>>[][] sendReceiveTable, Result<MyPair<Integer>> result) {
        int step = array.length / threadsNumber;
        int min = 0;
        int max = step - 1;
        Thread[] threads = new ParallelScanThread[threadsNumber];

        for (int i = 0; i < threadsNumber; i++) {
            threads[i] = new ParallelScanThread<>(min, max, array, i, sendReceiveTable, threadsNumber,
                    new CoefficientsMix(), result);
            min += step;
            max += step;
            if (i == threadsNumber - 2) {
                max = array.length - 1;
            }
        }
        return threads;
    }

    private static Cell<MyPair<Integer>>[][] initSendReceiveTableStatic(int threadsNumber) {
        Cell<MyPair<Integer>>[][] table = new Cell[threadsNumber][threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            for (int j = 0; j < threadsNumber; j++) {
                table[i][j] = new Cell<>();
            }
        }
        return table;
    }

    //private Cell<MyPair<Integer>>[][] sendReceiveTable;
    //private Thread[] threads;

    /*public void calculate(int threadsNumber, MyPair<Integer>[] array, Result<MyPair<Integer>> saveResultHere) {
        initSendReceiveTable(threadsNumber);
        initThreads(threadsNumber, array, saveResultHere);
        startThreads(threadsNumber);
    }*/

    /*private void startThreads(int threadsNumber) { //лишний аргумент
        for (int i = 0; i < threadsNumber; i++) {
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*private void initThreads(int threadsNumber, MyPair<Integer>[] array, Result<MyPair<Integer>> result) {
        int step = array.length / threadsNumber;
        int min = 0;
        int max = step - 1;
        threads = new ParallelScanThread[threadsNumber];

        for (int i = 0; i < threadsNumber; i++) {
            threads[i] = new ParallelScanThread<>(min, max, array, i, sendReceiveTable, threadsNumber,
                    new CoefficientsMix(), result);
            min += step;
            max += step;
            if (i == threadsNumber - 2) {
                max = array.length - 1;
            }
        }
    }*/

    /*private void initSendReceiveTable(int threadsNumber) {
        sendReceiveTable = new Cell[threadsNumber][threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            for (int j = 0; j < threadsNumber; j++) {
                sendReceiveTable[i][j] = new Cell<>();
            }
        }
    }*/
}
