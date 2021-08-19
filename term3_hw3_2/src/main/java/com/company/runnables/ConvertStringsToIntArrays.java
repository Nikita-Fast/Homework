package com.company.runnables;

import com.company.auxiliaries.MyPair;

public class ConvertStringsToIntArrays implements Runnable {

    private String s1;
    private String s2;
    private int[] array1;
    private int[] array2;
    private int threadID;
    private int threadsNumber;

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

        char[] a = s1.toCharArray();
        for (int i = bounds1.getA(); i < bounds1.getB(); i++) {
            array1[a.length - i - 1] = Integer.parseInt(String.valueOf(a[i]));
        }

        char[] b = s2.toCharArray();
        for (int i = bounds2.getA(); i < bounds2.getB(); i++) {
            array2[b.length - i - 1] = Integer.parseInt(String.valueOf(b[i]));
        }
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
