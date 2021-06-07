package com.company.algorithms;


import com.company.auxiliaries.MyPair;;
import com.company.operations.CoefficientsMix;

public class EquationsSolver {

    public static int solveInParallel(MyPair[] pairs, int threadsNumber) throws InterruptedException {
        ParallelScanner<MyPair> parallelScanner = new ParallelScanner<>();
        MyPair resultPair = parallelScanner.doScan(threadsNumber, pairs, new CoefficientsMix());
        return resultPair.getB();
    }

    public static int solveWithSingleThread(MyPair[] pairs) {
        int x = pairs[0].getB();
        for(int i = 1; i < pairs.length; i++) {
            x = pairs[i].getA() * x + pairs[i].getB();
        }
        return x;
    }
}
