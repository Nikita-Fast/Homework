package com.company.tasks;

import com.company.auxiliaries.MyPair;
import static com.company.tasks.PerformAddition.getPairWithBounds;

public class ConvertBracketToInt implements Runnable {
    private final char[] brackets;
    private final Integer[] array;
    private final int threadID;
    private final int threadsNumber;

    public ConvertBracketToInt(char[] brackets, Integer[] array, int threadID, int threadsNumber) {
        this.brackets = brackets;
        this.array = array;
        this.threadID = threadID;
        this.threadsNumber = threadsNumber;
    }

    @Override
    public void run() {
        MyPair bounds = getBounds(brackets.length, threadID, threadsNumber);
        for (int i = bounds.getA(); i < bounds.getB(); i++) {
            if (brackets[i] == '(') {
                array[i] = 1;
            }
            if (brackets[i] == ')') {
                array[i] = -1;
            }
            if (brackets[i] != '(' && brackets[i] != ')') {
                throw new IllegalArgumentException("Input string consists not only from brackets! Extraneous character " +
                        "is '" + brackets[i] + "'.");
            }
        }
    }

    private MyPair getBounds(int arrayLength, int threadID, int threadsNumber) {
        return getPairWithBounds(arrayLength, threadID, threadsNumber);
    }
}
