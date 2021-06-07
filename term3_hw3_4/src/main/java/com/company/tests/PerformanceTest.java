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

import com.company.algorithms.BracketsMatching;
import com.company.algorithms.ParallelPrefixScanner;
import com.company.auxiliaries.Cell;
import com.company.operations.Addition;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)

/*@Warmup(iterations = 2)
@Measurement(iterations = 2)*/
public class PerformanceTest {
    public static final int LENGTH = 10000000;
    public static final int SINGLE = -1;
    private char[] array;

    @Param({"16", "8", "4", "2", "1", "-1"})
    public int THREADS_NUMBER;

    @Setup
    public void prepare() {
        array = new char[LENGTH];
        for (int i = 0; i < LENGTH; i += 2) {
            array[i] = '(';
            array[i + 1] = ')';
        }
        /*System.out.println(Arrays.toString(array));*/
    }

    @Benchmark
    public void measureBracketsMatching(Blackhole bh) throws Exception {
        if (THREADS_NUMBER == SINGLE) {
            bh.consume(BracketsMatching.areBalancedInSingleThread(array));
        }
        else {
            bh.consume(BracketsMatching.areBalancedInParallel(array, THREADS_NUMBER));
        }
    }
}
