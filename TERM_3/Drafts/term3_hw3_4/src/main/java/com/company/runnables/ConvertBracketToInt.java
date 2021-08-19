package com.company.runnables;

public class ConvertBracketToInt implements Runnable {
    private final int from;
    private final int to;
    private char[] brackets;
    private Integer[] array;

    public ConvertBracketToInt(int from, int to, char[] brackets, Integer[] array) {
        this.from = from;
        this.to = to;
        this.brackets = brackets;
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = from; i < to; i++) {
            if (brackets[i] == '(') {
                array[i] = 1;
            }
            if (brackets[i] == ')') {
                array[i] = -1;
            }
            if (brackets[i] != '(' && brackets[i] != ')') {
                throw new IllegalArgumentException("Input string consists not only from brackets! Extraneous character " +
                        "is '" + brackets[i] + "'.");
            }
        }
    }
}
