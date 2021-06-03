package com.company;

import com.company.algorithms.EquationsSolver;
import com.company.auxiliaries.MyPair;
import com.company.auxiliaries.Result;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int length = 16;
        int threads = 4;
        int bound = 4;
        Random random = new Random();
        MyPair<Integer>[] pairs = new MyPair[length];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                pairs[i] = new MyPair<>(0, random.nextInt(bound));
            }
            else {
                pairs[i] = new MyPair<>(random.nextInt(bound) + 1, random.nextInt(bound));
            }
        }
        System.out.println(Arrays.toString(pairs));

        Result<MyPair<Integer>> result = new Result<>();
        EquationsSolver.calculateStatic(threads, pairs, result);
        System.out.println(result.getResult().getB() + " <---parallel");

        int x = pairs[0].getB();
        System.out.println("x0 = " + x);
        for(int i = 1; i < length; i++) {
            x = pairs[i].getA() * x + pairs[i].getB();
            System.out.println("x" + i + " = " + x);
        }

        System.out.println(result.getResult().getB() == x);
    }

}
