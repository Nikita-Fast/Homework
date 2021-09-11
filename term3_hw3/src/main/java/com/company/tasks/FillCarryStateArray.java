package com.company.tasks;

import com.company.auxiliaries.CarryState;
import com.company.auxiliaries.MyPair;

import static com.company.tasks.PerformAddition.getPairWithBounds;

public class FillCarryStateArray implements Runnable {

    private final CarryState[] carryStates;
    private final int[] num1;
    private final int[] num2;
    private final int threadID;
    private final int threadsNumber;

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
            if (i == 0 && carryStates[0] == CarryState.M) {
                carryStates[0] = CarryState.N;
            }
        }
    }

    private CarryState getCarryState(int x, int y) {
        return x + y > 9 ? CarryState.C : x + y == 9 ? CarryState.M : CarryState.N;
    }

    private MyPair getBounds(int arrayLength, int threadID, int threadsNumber) {
        return getPairWithBounds(arrayLength, threadID, threadsNumber);
    }
}
