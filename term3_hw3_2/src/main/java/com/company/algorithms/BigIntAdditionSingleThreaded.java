package com.company.algorithms;

public class BigIntAdditionSingleThreaded {

    public static int[] calculate(String x, String y) {
        int length = Math.max(x.length(), y.length());
        int[] a = new int[length];
        int[] b = new int[length];
        System.arraycopy(stringToArray(x), 0, a, 0, x.length());
        System.arraycopy(stringToArray(y), 0, b, 0, y.length());
        int[] result = new int[length + 1];
        int c = 0;
        for (int i = 0; i < length; i++) {
            int sum = a[i] + b[i];
            result[i] = (sum + c) % 10;
            c = (a[i] + b[i] + c) / 10;
        }
        result[length] = c;
        return result;
    }

    private static int[] stringToArray(String s) {
        int length = s.length();
        int[] a = new int[length];
        String rev = new StringBuilder(s).reverse().toString();
        for (int i = 0; i < length; i++) {
            a[i] = Integer.parseInt(String.valueOf(rev.charAt(i)));
        }
        return a;
    }
}
