package com.company;

import java.util.Arrays;

public class Polynomial {
    private double[] coefficients; //coeff[i] == a  ==> ... + a * x^i + ...
    private double a;
    private double b;
    private double cachedValue;

    public Polynomial(double[] coefficients, double a, double b) {
        this.coefficients = coefficients;
        this.a = a;
        this.b = b;
    }

    public boolean boundsLieInside(double x, double y) {
        return x <= a && b <= y;
    }

    public Polynomial(double[] coefficients, double a, double b, double cachedValue) {
        this.coefficients = coefficients;
        this.a = a;
        this.b = b;
        this.cachedValue = cachedValue;
    }

    public boolean isNotBound(double x) {
        return x != a && x != b;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getCachedValue() {
        return cachedValue;
    }
}
