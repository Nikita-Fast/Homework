package com.company.app;

public class IntegralCalculator {

    public static final int N = 10_000;

    public static double calc(Polynomial polynomial, double a, double b) { //coefficients[i] is the coeff of x^i
        double lowerBound = a;
        double upperBound = b;
        int negation = 1;
        if (Double.compare(a, b) > 0) { //b < a
            lowerBound = b;
            upperBound = a;
            negation = -1;
        }
        double sum = 0;
        double segLength = (upperBound - lowerBound) / N;
        if (segLength == 0) {
            return 0;
        }
        for (int i = 0; i <= N; i++) {
            sum += 2 * polynomial.getValueIn(lowerBound + segLength * i);
        }
        sum -= polynomial.getValueIn(lowerBound);
        sum-= polynomial.getValueIn(upperBound);

        return negation * sum * (segLength / 2);
    }
}
