package com.company;

public class Unit {
    private Polynomial polynomial;
    private double lowerBound;
    private double upperBound;
    private double cachedValue;

    public Unit(Polynomial polynomial, double lowerBound, double upperBound) {
        this.polynomial = polynomial;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Polynomial getPolynomial() {
        return polynomial;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public double getCachedValue() {
        return cachedValue;
    }

    public void setCachedValue(double cachedValue) {
        this.cachedValue = cachedValue;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "polynomial=" + polynomial +
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                ", cachedValue=" + cachedValue +
                '}';
    }
}
