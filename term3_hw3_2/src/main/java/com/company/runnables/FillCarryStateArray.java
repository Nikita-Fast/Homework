package com.company.runnables;

import com.company.auxiliaries.CarryState;
import com.company.auxiliaries.MyPair;

public class FillCarryStateArray implements Runnable {

    private CarryState[] carryStates;
    private int[] num1;
    private int[] num2;
    private int threadID;
    private int threadsNumber;

    public FillCarryStateArray(CarryState[] carryStates, int[] num1, int[] num2, int threadID, int threadsNumber) {
        this.carryStates = carryStates;
        this.num1 = num1;
        this.num2 = num2;
        this.threadID = threadID;
        this.threadsNumber = threadsNumber;
    }

    @Override
    public void run() {
        MyPair bounds = getBounds(num1.length, threadID, threadsNumber);
        for (int i = bounds.getA(); i < bounds.getB(); i++) {
            carryStates[i] = getCarryState(num1[i], num2[i]);
        }
    }

    private CarryState getCarryState(int x, int y) {
        return x + y > 9 ? CarryState.C : x + y == 9 ? CarryState.M : CarryState.N;
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
