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

import com.company.algorithms.BigIntegersAddition;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class PerformanceTest {
    public static final int LENGTH = 10_000_000;
    private String s1;
    private String s2;

    private int[] num1;
    private int[] num2;

    public static final int SINGLE = -1;

    @Param({/*"-1",*/ "8", "4", "2", "1"})
    public int THREADS_NUMBER;

    @Setup
    public void prepare() {
        /*StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < LENGTH - 1; i++) {
            sb1.append(9);
        }
        for (int i = 0; i < LENGTH; i++) {
            sb2.append(1);
        }
        s1 = sb1.toString();
        s2 = sb2.toString();*/

        num1 = new int[LENGTH];
        num2 = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            num1[i] = 9;
            num2[i] = 1;
        }
    }

    @Benchmark
    public void measureBigIntAddition(Blackhole bh) throws InterruptedException {
        if (THREADS_NUMBER == SINGLE) {
            bh.consume(BigIntegersAddition.addWithSingleThread(s1, s2));
        }
        else {
            //bh.consume(BigIntegersAddition.addInParallel(THREADS_NUMBER, s1, s2));
            bh.consume(BigIntegersAddition.addInParallelFromIntArrays(THREADS_NUMBER, num1, num2));
        }
    }
}
