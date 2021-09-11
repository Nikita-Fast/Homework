package com.company.tests;

import com.company.app.IntegralCalculator;
import com.company.app.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegralCalculatorTest {
    private static final double DELTA = 0.001;
    private static final double EXPECTED_VALUE = 183.923522689625;
    private ArrayList<Integer> coeffs1 = new ArrayList<>();
    private double a = -3.2865;
    private double b = 4.945;

    @Test
    void checkPrecision() {
        coeffs1.add(5);
        coeffs1.add(-2);
        coeffs1.add(3);
        Polynomial p = new Polynomial(coeffs1);
        double res1 = IntegralCalculator.calc(p, a, b);
        assertEquals(EXPECTED_VALUE, res1, DELTA);
    }

    @Test
    void checkBordersTransposition() {
        coeffs1.add(5);
        coeffs1.add(-2);
        coeffs1.add(3);
        Polynomial p = new Polynomial(coeffs1);
        double res1 = IntegralCalculator.calc(p, a, b);
        double res2 = IntegralCalculator.calc(p, b, a);

        assertEquals(res1, -res2, DELTA);
    }
}