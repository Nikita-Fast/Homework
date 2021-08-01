package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Polynomial {
    private final List<Integer> coefficients;

    //за это время лист мог измениться, но так лучше чем никак
    public Polynomial(List<Integer> coefficients) {
        if (coefficients.size() == 0) {
            throw new IllegalArgumentException("Polynomial must have at least one coefficient!");
        }
        this.coefficients = Collections.unmodifiableList(coefficients);
    }

    public double getValueIn(double x) {
        double res = coefficients.get(0);
        double factor = x;
        final int length = coefficients.size();
        for (int i = 1; i < length; i++) {
            res += coefficients.get(i) * factor;
            factor *= x;
        }
        return res;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return Objects.equals(coefficients, that.coefficients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficients);
    }
}
