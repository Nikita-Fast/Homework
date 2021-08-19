package com.company.algorithms;

import com.company.auxiliaries.Cell;
import com.company.auxiliaries.Result;
import com.company.operations.AssociativeOperation;
import com.company.threads.ParallelScanThread;

import java.util.ArrayList;

public class ParallelScanner<T> {

    public T doScan(int threadsNumber, T[] array, AssociativeOperation<T> operation) throws InterruptedException {
        ArrayList<ParallelScanThread<T>> threads = new ArrayList<>();
        int step = array.length / threadsNumber;
        int minIndex = 0;
        int maxIndex = step - 1;
        Result<T> saveResultHere = new Result<>();
        ArrayList<ArrayList<Cell<T>>> table = createTable(threadsNumber);

        for (int i = 0; i < threadsNumber; i++) {
            ParallelScanThread<T> thread = new ParallelScanThread<>(array, i, threadsNumber, minIndex, maxIndex,
                    table, operation, saveResultHere);
            threads.add(thread);
            minIndex += step;
            if (i == threadsNumber - 2) {
                maxIndex = array.length - 1;
            }
            else {
                maxIndex += step;
            }
        }

        for (ParallelScanThread<T> thread : threads) {
            thread.start();
        }
        for (ParallelScanThread<T> thread : threads) {
            thread.join();
        }

        return saveResultHere.getResult();
    }

    private ArrayList<ArrayList<Cell<T>>> createTable(int size) {
        ArrayList<ArrayList<Cell<T>>> table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Cell<T>> line = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                Cell<T> cell = new Cell<>();
                line.add(cell);
            }
            table.add(line);
        }
        return table;
    }
}
