package com.company.algorithms;

import com.company.auxiliaries.Cell;
import com.company.auxiliaries.Scanner;
import com.company.operations.AssociativeOperationWithNeutralElement;
import com.company.threads.ParallelPrefixScanThread;

import java.util.ArrayList;

public class ParallelPrefixScanner<T> extends Scanner<T> {

    public void doScan(int threadsNumber, T[] array, T[] resultArray, AssociativeOperationWithNeutralElement<T> operation)
            throws InterruptedException {
        ArrayList<ParallelPrefixScanThread<T>> threads = new ArrayList<>();
        ArrayList<ArrayList<Cell<T>>> table1 = createTable(threadsNumber);
        ArrayList<ArrayList<Cell<T>>> table2 = createTable(threadsNumber);
        int step = array.length / threadsNumber;
        int minIndex = 0;
        int maxIndex = step - 1;

        for (int i = 0; i < threadsNumber; i++) {
            ParallelPrefixScanThread<T> thread = new ParallelPrefixScanThread<>(array, resultArray, i, threadsNumber,
                    minIndex, maxIndex, table1, table2, operation);
            threads.add(thread);
            minIndex += step;
            if (i == threadsNumber - 2) {
                maxIndex = array.length - 1;
            }
            else {
                maxIndex += step;
            }
        }

        for (ParallelPrefixScanThread<T> thread : threads) {
            thread.start();
        }
        for (ParallelPrefixScanThread<T> thread : threads) {
            thread.join();
        }
    }
}
