package com.company.tests.junit;

import com.company.algorithms.TurtleWalk;
import com.company.auxiliaries.MyPair;
import com.company.auxiliaries.PositionOfTurtle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TurtleWalkTest {
    private MyPair[] plainInput = new MyPair[]{new MyPair(45, 4), new MyPair(30, 5), new MyPair(105, 4),
            new MyPair(90, 2)};
    private static final double DELTA = 0.000001;
    private static final int ITERATIONS = 100;

    @Test
    void calculateFinalPositionWithSingleThread() {
        PositionOfTurtle actual = TurtleWalk.calculateFinalPositionWithSingleThread(plainInput);
        PositionOfTurtle expected = new PositionOfTurtle(0.122522350258794, 5.6580562561915, 270.0);
        performAssertations(expected, actual, plainInput);
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void calculateFinalPositionInParallel(int threads, int length) throws InterruptedException {
        for (int i = 0; i < ITERATIONS; i++) {
            MyPair[] input = generateInput(length);
            PositionOfTurtle actual = TurtleWalk.calculateFinalPositionInParallel(input, threads);
            PositionOfTurtle expected = TurtleWalk.calculateFinalPositionWithSingleThread(input);
            performAssertations(expected, actual, input);
        }
    }

    private static Stream<Arguments> provideParameters() {
        Arguments[] arguments = new Arguments[15];
        int k = 0;
        for (int threads = 1; threads <= 16; threads *= 2) {
            for (int length = 10; length <= 100_000; length *= 100) {
                arguments[k++] = Arguments.of(threads, length);
            }
        }
        return Stream.of(arguments);
    }

    private MyPair[] generateInput(int baseLength) {
        int length;
        Random random = new Random();
        do {
            length = random.nextInt(2 * baseLength);
        } while (baseLength / 2 >= length);
        MyPair[] pairs = new MyPair[length];
        int angle;
        int distance;

        for (int i = 0; i < length; i++) {
            angle = random.nextInt(360);
            distance = random.nextInt(20);
            pairs[i] = new MyPair(angle, distance);
        }
        return pairs;
    }

    private void performAssertations(PositionOfTurtle expected, PositionOfTurtle actual, MyPair[] input) {
        assertEquals(expected.getX(), actual.getX(), DELTA, "input = " + Arrays.toString(input));
        assertEquals(expected.getY(), actual.getY(), DELTA, "input = " + Arrays.toString(input));
        assertEquals(expected.getAngle(), actual.getAngle(), DELTA, "input = " + Arrays.toString(input));
    }
}