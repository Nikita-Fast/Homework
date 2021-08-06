/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.company.tests;

import com.company.algorithms.*;
import com.company.auxiliaries.CarryState;
import com.company.auxiliaries.MyPair;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class PerformanceTest {
    public static final int LENGTH = 10_000_000;
    public static final int SINGLE = -1;
    private String s1;
    private String s2;
    private MyPair[] coefficients;
    private char[] brackets;
    private MyPair[] turtlePairs;

    private StringBuilder[] stringBuilders;
    //private int[] result;
    CarryState[] carryStates = new CarryState[LENGTH];

    int[] n1;
    int[] n2;

    @Param({"16", "8", "4", "2", "1", "-1"})
    public int THREADS_NUMBER;

    @Setup
    public void prepare() throws InterruptedException {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < LENGTH; i ++) {
            sb1.append('9');
            sb2.append('7');
        }
        s1 = sb1.toString();
        s2 = sb2.toString();
        /*coefficients = new MyPair[LENGTH];
        for (int i = 0; i < LENGTH; i ++) {
            coefficients[i] = new MyPair(i, i + 1);
        }

        brackets = new char[LENGTH];
        for (int i = 0; i < LENGTH; i += 2) {
            brackets[i] = '(';
            brackets[i + 1] = ')';
        }

        turtlePairs = new MyPair[LENGTH];
        for (int i = 0; i < LENGTH; i ++) {
            int angle = i % 360;
            int distance = i % 100;
            turtlePairs[i] = new MyPair(angle, distance);
        }*/

        /*result = new int[LENGTH + 1];
        Arrays.fill(result, 7);*/

        /*n1 = new int[LENGTH];
        n2 = new int[LENGTH];
        Arrays.fill(n1, 9);
        Arrays.fill(n2, 7);*/
    }


    /*//@Benchmark
    public int[] testQwrer3() {
        return BigIntegersAddition.convertStringToIntArrAndReverse(s1);
    }

    //@Benchmark
    public String testConversion() throws InterruptedException {
        return BigIntegersAddition.reverseAndConvertToString(n1, THREADS_NUMBER);
    }*/

    @Benchmark
    //@Warmup(iterations = 7)
    public void bigIntegersAddition(Blackhole bh) throws InterruptedException {
        if (THREADS_NUMBER == SINGLE) {
            bh.consume(BigIntegersAddition.addWithSingleThread(s1, s2));
        }
        else {
            bh.consume(BigIntegersAddition.addInParallel(THREADS_NUMBER, s1, s2));
        }
    }

    //@Benchmark
    public void measureEquationSolver(Blackhole bh) throws InterruptedException {
        if (THREADS_NUMBER == SINGLE) {
            bh.consume(EquationsSolver.solveWithSingleThread(coefficients));
        }
        else {
            bh.consume(EquationsSolver.solveInParallel(coefficients, THREADS_NUMBER));
        }
    }

    //@Benchmark
    public void measureBracketsMatching(Blackhole bh) throws Exception {
        if (THREADS_NUMBER == SINGLE) {
            bh.consume(BracketsMatching.areBalancedInSingleThread(brackets));
        }
        else {
            bh.consume(BracketsMatching.areBalancedInParallel(brackets, THREADS_NUMBER));
        }
    }

    //@Benchmark
    public void measureTurtleWalk(Blackhole bh) throws InterruptedException {
        if (THREADS_NUMBER == SINGLE) {
            bh.consume(TurtleWalk.calculateFinalPositionWithSingleThread(turtlePairs));
        }
        else {
            bh.consume(TurtleWalk.calculateFinalPositionInParallel(turtlePairs, THREADS_NUMBER));
        }
    }

    /*public static boolean compare(PositionOfTurtle p1, PositionOfTurtle p2) {
        double delta = 0.0001;
        boolean b1 = Math.abs(p1.getX() - p2.getX()) <= delta;
        boolean b2 = Math.abs(p1.getY() - p2.getY()) <= delta;
        boolean b3 = Math.abs(p1.getAngle() - p2.getAngle()) <= delta;
        return b1 & b2 & b3;
    }*/
}
