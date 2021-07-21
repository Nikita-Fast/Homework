package com.company;

public class IntegralCalculator {

    public static final int N = 100;

    public static double calc(double a, double b, double ... coefficients) { //coefficients[i] is the coeff of x^i
        if (coefficients.length == 0) {
            throw new IllegalArgumentException("Polynomial is not specified!");
        }
        double res = 0;
        double sum = 0;
        double segLength = (b - a) / N;
        for (int i = 0; i <= N; i++) {
            sum += 2 * getValueOfFunction(a + segLength * i, coefficients);
        }
        sum -= getValueOfFunction(a, coefficients);
        sum -= getValueOfFunction(b, coefficients);

        res = sum * (segLength / 2);
        return res;
    }

    public static double getValueOfFunction(double x, double ... coefficients) { //calc f(x)
        if (coefficients.length == 0) {
            throw new IllegalArgumentException("Polynomial is not specified!");
        }
        double res = coefficients[0];
        double valX = x;
        for (int i = 1; i < coefficients.length; i++) {
            res += coefficients[i] * valX;
            valX *= x;
        }
        return res;
    }
}
