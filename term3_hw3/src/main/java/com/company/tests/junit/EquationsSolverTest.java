package com.company.tests.junit;

import com.company.algorithms.EquationsSolver;
import com.company.auxiliaries.MyPair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EquationsSolverTest {

    private final MyPair[] input1 = new MyPair[]{new MyPair(0, 5), new MyPair(3, 2), new MyPair(4, 1)};
    private final MyPair[] input2 = new MyPair[]{new MyPair(0, -6), new MyPair(-5, -1), new MyPair(6, 0)};
    private static final int MAX_THREADS = 16;
    private static final int MAX_LENGTH = 1000;
    private static final int MAX_VALUE_OF_COEFFICIENT = 10;
    private static final int ITERATIONS = 100;

    @Test
    void solveWithSingleThread() {
        int actual = EquationsSolver.solveWithSingleThread(input1);
        int expected1 = 69;
        assertEquals(expected1, actual);
        actual = EquationsSolver.solveWithSingleThread(input2);
        int expected2 = 174;
        assertEquals(expected2, actual);
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void solveInParallel(int threadsNumber, int length) throws InterruptedException {
        for (int i = 0; i < ITERATIONS; i++) {
            MyPair[] input = generateInput(length);
            int actual = EquationsSolver.solveInParallel(input, threadsNumber);
            int expected = EquationsSolver.solveWithSingleThread(input);
            assertEquals(expected, actual, "input = " + Arrays.toString(input));
        }
    }

    private MyPair[] generateInput(int baseLength) {
        int length;
        Random random = new Random();
        do {
            length = random.nextInt(2 * baseLength);
        } while (baseLength / 2 >= length);
        MyPair[] pairs = new MyPair[length];
        int a = 0;
        int b = minus() * random.nextInt(MAX_VALUE_OF_COEFFICIENT);

        pairs[0] = new MyPair(a, b);
        for (int i = 1; i < length; i++) {
            a = minus() * random.nextInt(MAX_VALUE_OF_COEFFICIENT);
            b = minus() * random.nextInt(MAX_VALUE_OF_COEFFICIENT);
            pairs[i] = new MyPair(a, b);
        }
        return pairs;
    }

    private int minus() {
        return Math.random() < 0.5 ? -1 : 1;
    }

    private static Stream<Arguments> provideParameters() {
        Arguments[] arguments = new Arguments[15];
        int k = 0;
        for (int threads = 1; threads <= MAX_THREADS; threads *= 2) {
            for (int length = 10; length <= MAX_LENGTH; length = length * 10) {
                arguments[k++] = Arguments.of(threads, length);
            }
        }
        return Stream.of(arguments);
    }
}