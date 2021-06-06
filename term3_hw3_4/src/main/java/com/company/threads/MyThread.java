package com.company.threads;


import com.company.auxiliaries.Cell;
import com.company.auxiliaries.Result;
import com.company.operations.AssociativeOperation;
import com.company.operations.AssociativeOperationWithNeutralElement;

public abstract class MyThread<T> extends Thread {
    private int pid;
    private int threadsNumber;
    private int minIndex;
    private int maxIndex;
    private Cell<T>[][] sendReceiveTable;
    private T[] array;

    @Override
    public void run() {

    }

    public MyThread(int minIndex, int maxIndex, T[] array, int pid, Cell<T>[][] sendReceiveTable, int threadsNumber) {
        super();
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
        this.array = array;
        this.pid = pid;
        this.sendReceiveTable = sendReceiveTable;
        this.threadsNumber = threadsNumber;
    }

    protected <U extends AssociativeOperationWithNeutralElement<T>> void doParallelPrefixScan(U operation) {
        T total = operation.getNeutralElement();
        for (int i = minIndex; i <= maxIndex; i++) {
            total = operation.apply(total, array[i]);
        }
        int k;
        for (k = 1; k < threadsNumber; k *= 2) {
            if ((pid & k) == 0) {
                sendReceiveTable[pid][pid + k].send(total);
                break;
            }
            else {
                total = operation.apply(total, sendReceiveTable[pid - k][pid].receive());
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
                sendReceiveTable[pid][pid + k].send(total);
                total = sendReceiveTable[pid + k][pid].receive();
            }
            else {
                T received = sendReceiveTable[pid - k][pid].receive();
                sendReceiveTable[pid][pid - k].send(total);
                total = operation.apply(total, received);
            }
            k /= 2;
        }
        for (int i = minIndex; i <= maxIndex; i++) {
            total = operation.apply(total, array[i]);
            array[i] = total;
        }
    }

    protected <U extends AssociativeOperation<T>> void doParallelScan(U operation, Result<T> result) {
        T total = array[minIndex];
        for (int i = minIndex + 1; i <= maxIndex; i++) {
            total = operation.apply(total, array[i]);
        }

        for (int k = 1; k < threadsNumber; k *= 2) {
            if ((pid & k) != 0) {
                sendReceiveTable[pid][pid - k].send(total);
                break;
            }
            else if (pid + k < threadsNumber) {
                total = operation.apply(total, sendReceiveTable[pid + k][pid].receive());
            }
        }

        if (pid == 0) { //поток с id == 0 ответственен за сохранение результата
            result.setResult(total);
        }
    }

    protected <U extends AssociativeOperation<T>> void doParallelScanFromLeftToRight(U operation, Result<T> result) {
        T total = array[minIndex];
        for (int i = minIndex + 1; i <= maxIndex; i++) {
            total = operation.apply(total, array[i]);
        }
        for (int k = 1; k < threadsNumber; k *= 2) {
            if ((pid & k) == 0) {
                sendReceiveTable[pid][pid + k].send(total);
                break;
            }
            else {
                total = operation.apply(sendReceiveTable[pid - k][pid].receive(), total);
            }
        }
        if (pid == threadsNumber - 1) {
            result.setResult(total);
        }
    }

    protected void setSendReceiveTable(Cell<T>[][] sendReceiveTable) {
        this.sendReceiveTable = sendReceiveTable;
    }

    protected boolean isLastThread() {
        return pid == threadsNumber - 1;
    }

    protected int getMinIndex() {
        return minIndex;
    }

    protected int getMaxIndex() {
        return maxIndex;
    }

    protected int getPid() {
        return pid;
    }

    protected T[] getArray() {
        return array;
    }
}
