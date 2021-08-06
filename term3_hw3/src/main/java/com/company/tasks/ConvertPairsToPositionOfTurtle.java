package com.company.tasks;

import com.company.auxiliaries.MyPair;
import com.company.auxiliaries.PositionOfTurtle;

import static com.company.tasks.PerformAddition.getPairWithBounds;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ConvertPairsToPositionOfTurtle implements Runnable {

    private MyPair[] pairs;
    private PositionOfTurtle[] positions;
    private int threadID;
    private int threadsNumber;

    public ConvertPairsToPositionOfTurtle(MyPair[] pairs, PositionOfTurtle[] positions, int threadID, int threadsNumber) {
        this.pairs = pairs;
        this.positions = positions;
        this.threadID = threadID;
        this.threadsNumber = threadsNumber;
    }

    @Override
    public void run() {
        MyPair bounds = getBounds(pairs.length, threadID, threadsNumber);
        for (int i = bounds.getA(); i < bounds.getB(); i++) {
            convert(i, pairs, positions);
        }
    }

    public static void convert(int i, MyPair[] pairs, PositionOfTurtle[] positions) {
        double a = Math.toRadians(pairs[i].getA());
        double d = pairs[i].getB();
        double x = d * cos(a);
        double y = d * sin(a);
        positions[i] = new PositionOfTurtle(x, y, pairs[i].getA());
    }

    private MyPair getBounds(int arrayLength, int threadID, int threadsNumber) {
        return getPairWithBounds(arrayLength, threadID, threadsNumber);
    }
}
