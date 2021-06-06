package com.company;

import com.company.algorithms.BracketsMatching;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        String s = "(()())";
        Integer[] array = new Integer[s.length()];
        for (int i = 0; i < s.length(); i++) {
            array[i] = (s.charAt(i) == '(') ? 1 : -1;
        }
        System.out.println(Arrays.toString(array));

        boolean resultSingle = BracketsMatching.calculateInOneThread(array);
        boolean resultParallel = BracketsMatching.calculateInParallel(4, array);
        System.out.println(Arrays.toString(array));
        System.out.println(resultSingle + " <--- single threaded\n" + resultParallel + " <--- parallel");
    }

}
