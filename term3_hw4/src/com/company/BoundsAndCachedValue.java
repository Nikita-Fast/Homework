package com.company;

public class BoundsAndCachedValue {
    private double a;
    private double b;
    private double cashedValue;

    public boolean boundsLieInsideOf(double lowerBound, double upperBound) {
        return lowerBound <= a && b <= upperBound;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getCashedValue() {
        return cashedValue;
    }

    public boolean isNotBound(double x) {
        return x != a && x != b;
    }
}
