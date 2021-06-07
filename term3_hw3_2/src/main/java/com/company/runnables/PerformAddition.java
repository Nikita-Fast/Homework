package com.company.runnables;

import com.company.auxiliaries.CarryState;
import com.company.auxiliaries.MyPair;

public class PerformAddition implements Runnable {

    private int[] result;
    private int[] num1;
    private int[] num2;
    private CarryState[] carries;
    private int threadID;
    private int threadsNumber;

    public PerformAddition(int[] result, int[] num1, int[] num2, CarryState[] carries, int threadID, int threadsNumber) {
        this.result = result;
        this.num1 = num1;
        this.num2 = num2;
        this.carries = carries;
        this.threadID = threadID;
        this.threadsNumber = threadsNumber;
    }

    @Override
    public void run() {
        MyPair bounds = getBounds(result.length, threadID, threadsNumber);
        for (int i = bounds.getA(); i < bounds.getB(); i++) {
            if (i != 0 && i != result.length - 1) {
                int c = getCarry(carries[i - 1]);
                result[i] = (c + num1[i] + num2[i]) % 10;
            }
            if (i == 0) {
                result[0] = (num1[0] + num2[0]) % 10;
            }
            if (i == result.length - 1) {
                result[i] = getCarry(carries[i - 1]);
            }
        }
    }

    private int getCarry(CarryState c) {
        if (c == CarryState.C) {
            return 1;
        }
        if (c == CarryState.N) {
            return 0;
        }
        throw new IllegalArgumentException("After prefix scan can be only CarryState.C or CarryState.N");
    }

    private MyPair getBounds(int arrayLength, int threadID, int threadsNumber) {
        if (threadID < 0 || threadID >= threadsNumber) {
            throw new IllegalArgumentException("Incorrect threadID!");
        }
        int step = arrayLength / threadsNumber;
        int from = step * threadID;
        int to = threadID == threadsNumber - 1 ? arrayLength : step * (threadID + 1);
        return new MyPair(from, to);
    }
}
