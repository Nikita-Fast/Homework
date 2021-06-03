package com.company.threads;

import com.company.auxiliaries.Cell;
import com.company.auxiliaries.Result;
import com.company.operations.AssociativeOperation;

public class ParallelScanThread<T> extends MyThread<T> {
    private Result<T> result;
    private AssociativeOperation<T> operation;

    public ParallelScanThread(int minIndex, int maxIndex, T[] sharedGenericArray, int pid,
                              Cell<T>[][] sendReceiveTable, int threadsNumber, AssociativeOperation<T> operation, Result<T> result) {
        super(minIndex, maxIndex, sharedGenericArray, pid, sendReceiveTable, threadsNumber);
        this.result = result;
        this.operation = operation;
    }

    @Override
    public void run() {
        doParallelScan(operation, result);
    }
}
