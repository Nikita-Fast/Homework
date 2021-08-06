package com.company.tests;

import com.company.algorithms.BigIntegersAddition;
import com.company.algorithms.ParallelPrefixScanner;
import com.company.auxiliaries.CarryState;
import com.company.operations.CarryAddition;
import com.company.tasks.ConvertStringsToIntArrays;
import com.company.tasks.FillCarryStateArray;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BigIntegersAdditionTest {

    private final String s1 =           "372232800981245617380333673823095867";
    private final String s2 =              "878443443333456764367840038636452";
    private final String correctValue = "373111244424579074144701513861732319";
    private String res;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8, 16})
    void addInParallel(int threadsNumber) throws InterruptedException {
        res = BigIntegersAddition.addInParallel(threadsNumber, s1, s2);
        assertEquals(correctValue, res);
    }

    @org.junit.jupiter.api.Test
    void addWithSingleThread() {
        res = BigIntegersAddition.addWithSingleThread(s1, s2);
        assertEquals(correctValue, res);
    }

    //commented code contains tests for almost all parts of BigIntegersAddition.addInParallel method

    /*public CarryState[] generateInput(int length) {
        CarryState[] array = new CarryState[length];
        for (int i = 0; i < length; i++) {
            double v = Math.random();
            array[i] = v < 0.33 ? CarryState.N : (v < 0.67 ? CarryState.M : CarryState.C);
        }
        if (array[0] == CarryState.M) {
            array[0] = CarryState.N;
        }
        return array;
    }

    public CarryState[] generateInput(String s1, String s2) {
        int max = Math.max(s1.length(), s2.length());
        String str1 =  new StringBuilder(s1).reverse().toString();
        String str2 =  new StringBuilder(s2).reverse().toString();
        CarryState[] array = new CarryState[max];
        for (int i = 0; i < max; i++) {
            char c1 = str1.charAt(i);
            char c2;
            try {
                c2 = str2.charAt(i);
            }
            catch (IndexOutOfBoundsException e) {
                c2 = '0';
            }
            int x1 = Character.digit(c1, 10);
            int x2 = Character.digit(c2, 10);
            int sum = x1 + x2;
            array[i] = sum < 9 ? CarryState.N : (sum == 9 ? CarryState.M : CarryState.C);
        }
        if (array[0] == CarryState.M) {
            array[0] = CarryState.N;
        }
        return array;
    }

    public CarryState[] generateInput(String str) {
        CarryState[] array = new CarryState[str.length()];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            array[i] = c == 'n' ? CarryState.N : (c == 'm' ? CarryState.M : CarryState.C);
        }
        if (array[0] == CarryState.M) {
            array[0] = CarryState.N;
        }
        return array;
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,4,8,16})
    void calculateCarries(int threadsNumber) throws InterruptedException {
        CarryState[] example = generateInput(35);
        System.out.println("input    = " + Arrays.toString(example));

        CarryState[] resultCarryStates = new CarryState[example.length];
        ParallelPrefixScanner<CarryState> prefixScanner = new ParallelPrefixScanner<>();
        prefixScanner.doScan(threadsNumber, example, resultCarryStates, new CarryAddition());

        //System.out.println(Arrays.toString(resultCarryStates));

        CarryState[] expected = new CarryState[example.length];

        CarryAddition func = new CarryAddition();
        expected[0] = example[0];
        if (expected[0] == CarryState.M) {
            expected[0] = CarryState.N; //так как нет переноса из предыдущего разряда, ведь это самый младший
        }
        for (int i = 1; i < expected.length; i++) {
            expected[i] = func.apply(expected[i - 1], example[i]);
        }

        System.out.println("expected = " + Arrays.toString(expected));
        System.out.println("actual   = " + Arrays.toString(resultCarryStates));
        assertArrayEquals(expected, resultCarryStates);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8, 16})
    void convertStringsToIntArrays(int threadsNumber) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        int max = Math.max(s1.length(), s2.length());

        int[] num1 = new int[max];
        int[] num2 = new int[max];

        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new ConvertStringsToIntArrays(s1, s2, num1, num2, i, threadsNumber));
            threads.add(thread);
        }
        BigIntegersAddition.startAllThreadsAndWaitForResults(threads);

        int[] expectedS1Representation = new int[max];
        int j = 0;
        for (int i = s1.length() - 1; i >= 0; i--) {
            expectedS1Representation[j++] = Character.digit(s1.charAt(i), 10);
        }
        int[] expectedS2Representation = new int[max];
        j = 0;
        for (int i = s2.length() - 1; i >= 0; i--) {
            expectedS2Representation[j++] = Character.digit(s2.charAt(i), 10);
        }
        assertArrayEquals(expectedS1Representation, num1);
        assertArrayEquals(expectedS2Representation, num2);
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8, 16})
    void fillCarryState(int threadsNumber) throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        int max = Math.max(s1.length(), s2.length());
        int[] num1 = new int[max];
        int[] num2 = new int[max];

        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new ConvertStringsToIntArrays(s1, s2, num1, num2, i, threadsNumber));
            threads.add(thread);
        }
        BigIntegersAddition.startAllThreadsAndWaitForResults(threads);
        //++++++++++++++++++++++++++++++++++++++++ part under test starts here
        CarryState[] carryStates = new CarryState[max];
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread = new Thread(new FillCarryStateArray(carryStates, num1, num2, i, threadsNumber));
            threads.add(thread);
        }
        BigIntegersAddition.startAllThreadsAndWaitForResults(threads);

        CarryState[] expectedCarryStates = new CarryState[max];
        for (int i = 0; i < max; i++) {
            int sum = num1[i] + num2[i];
            expectedCarryStates[i] = sum < 9 ? CarryState.N : (sum == 9 ? CarryState.M : CarryState.C);
        }
        if (expectedCarryStates[0] == CarryState.M) {
            expectedCarryStates[0] = CarryState.N;
        }
        assertArrayEquals(expectedCarryStates, carryStates);
    }*/
}
