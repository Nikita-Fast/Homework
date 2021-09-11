package com.company.tests.junit;

import com.company.algorithms.BracketsMatching;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BracketsMatchingTest {

    private static final int SHORT_INPUT_LENGTH = 6;
    private static final int LONG_INPUT_LENGTH = 96;
    private static final int ITERATIONS = 1000;

    @Test
    void areBalancedInSingleThread() {
        String goodInput = "((()(())))";
        boolean actual = BracketsMatching.areBalancedInSingleThread(goodInput.toCharArray());
        assertTrue(actual);
        String badInput = "(()()(()())(())(()())))";
        actual = BracketsMatching.areBalancedInSingleThread(badInput.toCharArray());
        assertFalse(actual);
    }

    //We believe that the first test approves the correctness of BracketsMatching.areBalancedInSingleThread
    //method and because of that we use it here as the source of expected value
    @ParameterizedTest
    @ValueSource(ints = {1,2,4,8,16})
    void areBalancedInParallel(int threadsNumber) throws InterruptedException {
        for (int i = 0; i < ITERATIONS; i++) {
            char[] input = generateInput(SHORT_INPUT_LENGTH);
            boolean actual = BracketsMatching.areBalancedInParallel(input, threadsNumber);
            boolean expected = BracketsMatching.areBalancedInSingleThread(input);
            assertEquals(expected, actual, "input where problem occurs: " + Arrays.toString(input));
        }
        for (int i = 0; i < ITERATIONS; i++) {
            char[] input = generateInput(LONG_INPUT_LENGTH);
            boolean actual = BracketsMatching.areBalancedInParallel(input, threadsNumber);
            boolean expected = BracketsMatching.areBalancedInSingleThread(input);
            assertEquals(expected, actual, "input where problem occurs: " + Arrays.toString(input));
        }
    }

    private char[] generateInput(int length) {
        char[] array = new char[length];
        for (int i = 0; i < length; i++) {
            array[i] = Math.random() < 0.5 ? '(' : ')';
        }
        return array;
    }
}