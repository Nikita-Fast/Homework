package com.company.threads;

import com.company.auxiliaries.Stage;
import com.company.auxiliaries.Cell;
import com.company.operations.AssociativeOperationWithNeutralElement;

import java.util.ArrayList;

public class ParallelPrefixScanThread<T> extends Thread {

    private final T[] array;
    private final T[] resultArray;
    private final int pid;
    private final int threadsNumber;
    private final int minIndex;
    private final int maxIndex;
    private final ArrayList<ArrayList<Cell<T>>> table1;
    private final ArrayList<ArrayList<Cell<T>>> table2;
    private final AssociativeOperationWithNeutralElement<T> operation;

    @Override
    public void run() {
        doParallelPrefixScan();
    }

    public ParallelPrefixScanThread(T[] array, T[] resultArray, int pid, int threadsNumber, int minIndex, int maxIndex,
                                    ArrayList<ArrayList<Cell<T>>> table1, ArrayList<ArrayList<Cell<T>>> table2,
                                    AssociativeOperationWithNeutralElement<T> operation) {
        this.array = array;
        this.resultArray = resultArray;
        this.pid = pid;
        this.threadsNumber = threadsNumber;
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
        this.table1 = table1;
        this.table2 = table2;
        this.operation = operation;
    }

    private void doParallelPrefixScan() {
        //System.out.println("pid = " + pid + ", [" + minIndex + ", " + maxIndex + "]");

        T total = operation.getNeutralElement();
        for (int i = minIndex; i <= maxIndex; i++) {
            total = operation.apply(total, array[i]);
        }

        int k;
        for (k = 1; k < threadsNumber; k *= 2) {
            if ((pid & k) == 0) {
                send(pid + k, total, Stage.COLLECT);
                break;
            }
            else {
                T received = receive(pid - k, Stage.COLLECT);
                total = operation.apply(received, total);
            }
        }

        if (pid == threadsNumber - 1) {
            total = operation.getNeutralElement();
        }
        if (k >= threadsNumber) {
            k /= 2;
        }
        while (k > 0) {
            if((pid & k) == 0) {
                send(pid + k, total, Stage.DISTRIBUTE);
                total = receive(pid + k, Stage.DISTRIBUTE);
            }
            else {
                T receivedValue = receive(pid - k,  Stage.DISTRIBUTE);
                send(pid - k, total, Stage.DISTRIBUTE);
                total = operation.apply(total, receivedValue);
            }
            k /= 2;
        }
        for (int i = minIndex; i <= maxIndex; i++) {
            total = operation.apply(total, array[i]);
            resultArray[i] = total;
        }
    }

    private void send(int dstPid, T value, Stage stage) {
        if (stage == Stage.COLLECT) {
            table1.get(pid).get(dstPid).send(value);
        }
        if (stage == Stage.DISTRIBUTE) {
            table2.get(pid).get(dstPid).send(value);
        }
    }

    private T receive(int srcPid, Stage stage) {
        return stage == Stage.COLLECT ? table1.get(srcPid).get(pid).receive() : table2.get(srcPid).get(pid).receive();
    }
}
