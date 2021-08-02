package com.company;

import com.company.concurrent.LazyList;
import com.company.concurrent.OptimisticList;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

public class MyTask implements Runnable {

    private final String line;
    private final /*ConcurrentSkipListSet<PolynomialWithData>*/ /*LazyList*/ OptimisticList<PolynomialWithData> CalculatedPolynomials;

    public MyTask(String line,
            /*ConcurrentSkipListSet<PolynomialWithData>*//*LazyList*/OptimisticList<PolynomialWithData> CalculatedPolynomials) {
        this.line = line;
        this.CalculatedPolynomials = CalculatedPolynomials;

    }

    @Override
    public void run() {
        PolynomialWithData current = extractDataFromString();
        PolynomialWithData calculatedInPast = null;

        for (PolynomialWithData polynomialWithData : CalculatedPolynomials) {
            if (polynomialWithData == null) {
                continue;
            }
            if (polynomialWithData.getPolynomial().equals(current.getPolynomial()) &&
                    current.getA() <= polynomialWithData.getA() &&
                    polynomialWithData.getB() <= current.getB()) {
                calculatedInPast = polynomialWithData;
                break;
            }
        }

        double res;
        if (calculatedInPast != null) {
            double part1 = IntegralCalculator.calc(current.getPolynomial(), current.getA(), calculatedInPast.getA());
            double part2 = IntegralCalculator.calc(current.getPolynomial(), calculatedInPast.getB(), current.getB());
            res = calculatedInPast.getCachedValue() + part1 + part2;
        }
        else {
            res  = IntegralCalculator.calc(current.getPolynomial(), current.getA(), current.getB());
        }

        current.setCachedValue(res);
        CalculatedPolynomials.add(current);
    }

    private PolynomialWithData extractDataFromString() {
        double[] bounds = new double[2];
        ArrayList<Integer> coeffs = new ArrayList<>();
        try (Scanner scanner = new Scanner(line)) {
            //сканируем 2 дабла - это границы, потом сканируем инты пока можем - это коэффиценты
            int i = 0;
            while (scanner.hasNextDouble() && i < 2) {
                bounds[i++] = scanner.nextDouble();
            }
            while (scanner.hasNextInt()) {
                coeffs.add(scanner.nextInt());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new PolynomialWithData(new Polynomial(coeffs), bounds[0], bounds[1]);
    }
}
