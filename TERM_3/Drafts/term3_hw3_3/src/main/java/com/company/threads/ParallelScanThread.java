package com.company.threads;

import com.company.auxiliaries.Cell;
import com.company.auxiliaries.Result;
import com.company.operations.AssociativeOperation;

import java.util.ArrayList;

public class ParallelScanThread<T> extends Thread{

    private T[] array;
    private int pid;
    private int threadsNumber;
    private int minIndex;
    private int maxIndex;
    private ArrayList<ArrayList<Cell<T>>> table;
    private AssociativeOperation<T> operation;
    private Result<T> result;

    @Override
    public void run() {
        doParallelScan();
    }

    public ParallelScanThread(T[] array, int pid, int threadsNumber, int minIndex, int maxIndex,
                              ArrayList<ArrayList<Cell<T>>> table, AssociativeOperation<T> operation,
                              Result<T> result) {
        this.array = array;
        this.pid = pid;
        this.threadsNumber = threadsNumber;
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
        this.table = table;
        this.operation = operation;
        this.result = result;
    }

    private void doParallelScan() {
        T total = array[minIndex];
        for (int i = minIndex + 1; i <= maxIndex; i++) {
            total = operation.apply(total, array[i]);
        }

        for (int k = 1; k < threadsNumber; k *= 2) {
            if ((pid & k) != 0) {
                send(pid - k, total);
                break;
            }
            else if (pid + k < threadsNumber) {
                total = operation.apply(total, receive(pid + k));
            }
        }

        if (pid == 0) { //поток с id == 0 ответственен за сохранение результата
            result.setResult(total);
        }
    }

    private void send(int dstPid, T value) {
        ((table.get(pid)).get(dstPid)).send(value);
    }

    private T receive(int srcPid) {
        return ((table.get(srcPid)).get(pid)).receive();
    }
}
