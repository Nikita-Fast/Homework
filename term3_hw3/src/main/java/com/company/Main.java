package com.company;

import com.company.algorithms.BigIntegersAddition;
import com.company.algorithms.TurtleWalk;
import com.company.auxiliaries.MyPair;
import com.company.auxiliaries.PositionOfTurtle;
import com.company.tasks.ConvertStringsToIntArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntFunction;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        /*MyPair[] pairs = new MyPair[]{new MyPair(45, 40), new MyPair(30, 50),
                new MyPair(105, 40), new MyPair(90, 20)};
        PositionOfTurtle singleRes = TurtleWalk.calculateFinalPositionWithSingleThread(pairs);
        PositionOfTurtle parallelRes = TurtleWalk.calculateFinalPositionInParallel(pairs, 8);
        System.out.println(compare(singleRes, parallelRes));
        System.out.println(parallelRes);
        System.out.println(singleRes);*/
        //System.out.println(Character.digit('3', 10));
        String res = BigIntegersAddition.addWithSingleThread("88888888", "21221021");
        res = BigIntegersAddition.addInParallel(4, "88888888", "21221021");
        System.out.println(res);
    }

}
