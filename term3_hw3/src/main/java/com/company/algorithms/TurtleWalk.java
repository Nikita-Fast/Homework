package com.company.algorithms;

import com.company.auxiliaries.MyPair;
import com.company.auxiliaries.PositionOfTurtle;
import com.company.operations.CompositionOfTurtlePositions;
import com.company.tasks.ConvertPairsToPositionOfTurtle;

import java.util.ArrayList;

public class TurtleWalk {

    public static PositionOfTurtle calculateFinalPositionInParallel(MyPair[] pairs, int threadsNumber) throws InterruptedException {
        PositionOfTurtle[] positions = new PositionOfTurtle[pairs.length];
        ArrayList<Thread> threads = new ArrayList<>();
        for (int j = 0; j < threadsNumber; j++) {
            Thread thread = new Thread(new ConvertPairsToPositionOfTurtle(pairs, positions, j, threadsNumber));
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();

        ParallelScanner<PositionOfTurtle> parallelScanner = new ParallelScanner<>();
        return parallelScanner.doScan(threadsNumber, positions, new CompositionOfTurtlePositions());
    }

    public static PositionOfTurtle calculateFinalPositionWithSingleThread(MyPair[] pairs) {
        PositionOfTurtle[] positions = new PositionOfTurtle[pairs.length];
        for (int i = 0; i < positions.length; i++) {
            ConvertPairsToPositionOfTurtle.convert(i, pairs, positions);
        }

        CompositionOfTurtlePositions operation = new CompositionOfTurtlePositions();
        PositionOfTurtle result = new PositionOfTurtle();
        for (PositionOfTurtle position : positions) {
            result = operation.apply(result, position);
        }
        return result;
    }
}
