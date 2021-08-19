package com.company;

import java.util.Arrays;

public class BigIntegersAdd {

    public static final int ZERO_ASCII_CODE = 48;
    public static final int THREADS_NUMBER = 4; //must be power of 2

    public int[] calculateWithSingleThread(String num1, String num2) {
        int[] aInArr = new int[num1.length()];
        int[] bInArr = new int[num2.length()];

        for (int i = 0; i < num1.length(); i++) {
            char[] str = num1.toCharArray();
            aInArr[i] = str[str.length - 1 - i] - ZERO_ASCII_CODE;
        }
        for (int i = 0; i < num2.length(); i++) {
            char[] str = num2.toCharArray();
            bInArr[i] = str[str.length - 1 - i] - ZERO_ASCII_CODE;
        }

        int length = Math.max(num1.length(), num2.length());
        int[] result = new int[length + 1];
        int carry = 0;
        for (int i = 0; i < length; i++) {
            int a = 0;
            int b = 0;
            if (i < aInArr.length) {
                a = aInArr[i];
            }
            if (i < bInArr.length) {
                b = bInArr[i];
            }
            result[i] = (a + b + carry) % 10;
            carry = (a + b + carry) / 10;
        }
        result[length] = carry;
        return result;
    }

    public int[] calculateWithMultipleThreads(String num1, String num2) {
        int[] aInArr = new int[num1.length()];
        int[] bInArr = new int[num2.length()];
        for (int i = 0; i < aInArr.length; i++) {
            char[] str = num1.toCharArray();
            aInArr[i] = str[str.length - 1 - i] - ZERO_ASCII_CODE;
        }
        for (int i = 0; i < bInArr.length; i++) {
            char[] str = num2.toCharArray();
            bInArr[i] = str[str.length - 1 - i] - ZERO_ASCII_CODE;
        }

        Link[][] links = new Link[THREADS_NUMBER][THREADS_NUMBER];
        for (int i = 0; i < links.length; i++) {
            for (int j = 0; j < links[i].length; j++) {
                links[i][j] = new Link();
            }
        }

        int length = Math.max(num1.length(), num2.length());
        CarryState[] carries = new CarryState[length];
        Thread[] threads = new Thread[THREADS_NUMBER];
        int[] result = new int[length + 1];

        for (int i = 0; i < THREADS_NUMBER; i++) {
            MyThread thread = new MyThread(aInArr, bInArr, carries, result, links);
            thread.start();
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
