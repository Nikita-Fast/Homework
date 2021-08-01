package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MyRecTask extends RecursiveTask<Double> {

    private final String line;
    private final ConcurrentSkipListSet<Unit> doneCalculations;

    public MyRecTask(String line, ConcurrentSkipListSet<Unit> doneCalculations) {
        this.line = line;
        this.doneCalculations = doneCalculations;
    }

    @Override
    protected Double compute() {
        Unit currentTaskUnit = buildUnit();
        Unit innerPolynomial = null; //можно потом и список таких сделать
        for (Unit unit : doneCalculations) {
            if (unit == null) {
                continue;
            }
            if (unit.getPolynomial().equals(currentTaskUnit.getPolynomial()) &&
            currentTaskUnit.getLowerBound() <= unit.getLowerBound() &&
            unit.getUpperBound() <= currentTaskUnit.getUpperBound()) {
                innerPolynomial = unit;
                break;
            }
        }
        //есть подходящий вычисленнй полином, берем закэшированное значение и считаем с краёв
        if (innerPolynomial != null) {
            if (currentTaskUnit.getLowerBound() == innerPolynomial.getLowerBound() &&
                    innerPolynomial.getUpperBound() == currentTaskUnit.getUpperBound()) {
                return innerPolynomial.getCachedValue();
            }

            List<MyRecTask> subTasks = createSubTasks(currentTaskUnit, innerPolynomial);
            for (MyRecTask subTask : subTasks) {
                subTask.fork();
            }
            double res = innerPolynomial.getCachedValue();
            for (MyRecTask subTask : subTasks) {
                res += subTask.join();
            }
            return res;
        }
        //вычисляем целиком
        else {
            double integral = IntegralCalculator.calc(currentTaskUnit.getPolynomial(),
                    currentTaskUnit.getLowerBound(), currentTaskUnit.getUpperBound());

            currentTaskUnit.setCachedValue(integral);
            doneCalculations.add(currentTaskUnit);
            return integral;
        }
    }

    private List<MyRecTask> createSubTasks(Unit currentTaskUnit, Unit innerPolynomial) {
        ArrayList<MyRecTask> subTasks = new ArrayList<>();
        if (Double.compare(currentTaskUnit.getLowerBound(), innerPolynomial.getLowerBound()) != 0) {
            //бред, но пока так
            StringBuilder sb = new StringBuilder();
            sb.append(currentTaskUnit.getLowerBound()).append(" ").append(innerPolynomial.getLowerBound());
            currentTaskUnit.getPolynomial().getCoefficients().forEach(coefficient -> sb.append(coefficient).append(" "));
            String createdLine = sb.toString();
            subTasks.add(new MyRecTask(createdLine, doneCalculations));
        }
        if (Double.compare(currentTaskUnit.getUpperBound(), innerPolynomial.getUpperBound()) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(innerPolynomial.getUpperBound()).append(" ").append(currentTaskUnit.getUpperBound());
            currentTaskUnit.getPolynomial().getCoefficients().forEach(coefficient -> sb.append(coefficient).append(" "));
            String createdLine = sb.toString();
            subTasks.add(new MyRecTask(createdLine, doneCalculations));
        }
        return subTasks;
    }

    private Unit buildUnit() {
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
        return new Unit(new Polynomial(coeffs), bounds[0], bounds[1]);
    }
}
