package com.company.tasks;

import com.company.auxiliaries.MyPair;

import java.util.Arrays;

public class ConvertStringsToIntArrays implements Runnable {

    private final String s1;
    private final String s2;
    private final int[] array1;
    private final int[] array2;
    private final int threadID;
    private final int threadsNumber;

    public ConvertStringsToIntArrays(String s1, String s2, int[] array1, int[] array2, int threadID, int threadsNumber) {
        this.s1 = s1;
        this.s2 = s2;
        this.array1 = array1;
        this.array2 = array2;
        this.threadID = threadID;
        this.threadsNumber = threadsNumber;
    }

    @Override
    public void run() {
        MyPair bounds1 = getBounds(s1, threadID, threadsNumber);
        MyPair bounds2 = getBounds(s2, threadID, threadsNumber);

        String part1 = s1.substring(bounds1.getA(), bounds1.getB());
        String part2 = s2.substring(bounds2.getA(), bounds2.getB());


        final int pos1 = s1.length() - 1 - bounds1.getA();
        for (int i = 0; i < part1.length(); i++) {
            array1[pos1 - i] = Character.digit(part1.charAt(i), 10);
        }

        final int pos2 = s2.length() - 1 - bounds2.getA();
        for (int i = 0; i < part2.length(); i++) {
            array2[pos2 - i] = Character.digit(part2.charAt(i), 10);
        }




        /*char[] a = s1.toCharArray();
        for (int i = bounds1.getA(); i < bounds1.getB(); i++) {
            array1[a.length - i - 1] = Integer.parseInt(String.valueOf(a[i]));
        }

        char[] b = s2.toCharArray();
        for (int i = bounds2.getA(); i < bounds2.getB(); i++) {
            array2[b.length - i - 1] = Integer.parseInt(String.valueOf(b[i]));
        }*/
    }

    private MyPair getBounds(String str, int threadID, int threadsNumber) {
        if (threadID < 0 || threadID >= threadsNumber) {
            throw new IllegalArgumentException("Incorrect threadID!");
        }
        int step = str.length() / threadsNumber;
        int from = step * threadID;
        int to = threadID == threadsNumber - 1 ? str.length() : step * (threadID + 1);
        return new MyPair(from, to);
    }
}
