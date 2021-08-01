package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PolynomialWithData implements Comparable<PolynomialWithData>{
    private final Polynomial polynomial;
    private final double a;
    private final double b;
    private double cachedValue;

    public PolynomialWithData(Polynomial polynomial, double lowerBound, double upperBound) {
        this.polynomial = polynomial;
        this.a = lowerBound;
        this.b = upperBound;
    }

    public Polynomial getPolynomial() {
        return polynomial;
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

    public void setCachedValue(double cachedValue) {
        this.cachedValue = cachedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolynomialWithData unit = (PolynomialWithData) o;
        return Double.compare(unit.a, a) == 0 &&
                Double.compare(unit.b, b) == 0 &&
                Objects.equals(polynomial, unit.polynomial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(polynomial, a, b);
    }

    @Override
    public String toString() {
        return "Unit{" +
                "polynomial=" + polynomial.getCoefficients() +
                ", lowerBound=" + a +
                ", upperBound=" + b +
                ", cachedValue=" + cachedValue +
                '}';
    }

    @Override
    public int compareTo(PolynomialWithData another) {
        List<Integer> l1 = this.polynomial.getCoefficients();
        List<Integer> l2 = another.getPolynomial().getCoefficients();

        if (Arrays.equals(l1.toArray(), l2.toArray())) {
            //same coeffs
            //look at bounds

            //case 1: equal bounds
            if (Double.compare(a, another.getA()) == 0 && Double.compare(b, another.getB()) == 0) {
                return 0;
            }
            //case2: раз неравны, то надо добавлять в коллекцию
            int res;
            if ((res = Double.compare(a, another.getA())) != 0) {
                return res;
            }
            if ((res = Double.compare(b, another.getB())) != 0) {
                return res;
            }
        }

        if (l1.size() != l2.size()) {
            return l1.size() - l2.size();
        }
        for (int i = l1.size() - 1; i >= 0; i--) {
            int res;
            if ((res = l1.get(i) - l2.get(i)) != 0) {
                return res;
            }

        }
        return 0;
    }
}
