package com.company.tasks;

import com.company.auxiliaries.MyPair;
import static com.company.tasks.PerformAddition.getPairWithBounds;

public class BuildResult implements Runnable {

    private int[] array;
    private int threadID;
    private int threadsNumber;
    private StringBuilder stringBuilder;

    public BuildResult(int[] array, int threadID, int threadsNumber, StringBuilder stringBuilder) {
        this.array = array;
        this.threadID = threadID;
        this.threadsNumber = threadsNumber;
        this.stringBuilder = stringBuilder;
    }

    @Override
    public void run() {
        MyPair bounds = getBounds(array.length, threadID, threadsNumber);
        if (threadID == threadsNumber - 1) {
            //убрать ведущие нули
            boolean leadingZerosAreOver = false;
            for (int i = bounds.getB() - 1; i >= bounds.getA(); i--) {
                if (array[i] != 0) {
                    leadingZerosAreOver = true;
                }
                if (leadingZerosAreOver) {
                    stringBuilder.append(array[i]);
                }
            }
        }
        else {
            for (int i = bounds.getB() - 1; i >= bounds.getA(); i--) {
                stringBuilder.append(array[i]);
            }
        }
    }

    private MyPair getBounds(int arrayLength, int threadID, int threadsNumber) {
        return getPairWithBounds(arrayLength, threadID, threadsNumber);
    }
}
