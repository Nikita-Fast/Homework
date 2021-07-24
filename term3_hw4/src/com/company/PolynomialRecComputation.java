package com.company;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class PolynomialRecComputation extends RecursiveTask<Double> {
    private final Polynomial polynomial;
    private final CopyOnWriteArrayList<Polynomial> computedPolynomials;

    public PolynomialRecComputation(Polynomial polynomial, CopyOnWriteArrayList<Polynomial> computedPolynomials) {
        this.polynomial = polynomial;
        this.computedPolynomials = computedPolynomials;
    }

    //надо ли здесь параллельную коллекцию?
    private ArrayList<Polynomial> find(Polynomial polynomial) {
        ArrayList<Polynomial> appropriatePolynomials = new ArrayList<>();
        computedPolynomials.forEach(p -> {
            if (Arrays.equals(polynomial.getCoefficients(), p.getCoefficients())) {
                if (p.boundsLieInside(polynomial.getA(), polynomial.getB())) {
                    appropriatePolynomials.add(p);
                }
            }
        });
        return appropriatePolynomials;
    }

    @Override
    protected Double compute() {
        ArrayList<Polynomial> appropriatePolynomials = find(polynomial);

        if (appropriatePolynomials.isEmpty()) { //в коллекции нет такого полинома, делаем полное вычисление
            double res = processing(polynomial);
            computedPolynomials.add(new Polynomial(polynomial.getCoefficients(),
                    polynomial.getA(), polynomial.getB(), res));
            System.out.println("computed value --> " + res);
            return res;
        }
        else { // в коллекции есть такой полином, проверяем границы, пытаемся делить задание
            //divide on parts
            Random random = new Random();
            Polynomial randomComputedPolynomial =
                    appropriatePolynomials.get(random.nextInt(appropriatePolynomials.size()));
            System.out.println("cached value = " + randomComputedPolynomial.getCachedValue());

            List<PolynomialRecComputation> subTasks = createSubtasks(randomComputedPolynomial);
            for (PolynomialRecComputation subTask : subTasks) {
                subTask.fork();
            }

            double result = randomComputedPolynomial.getCachedValue();

            for (PolynomialRecComputation subTask : subTasks) {
                result += subTask.join();
            }
            return result;

            /*return randomComputedPolynomial.getCachedValue() +
                    ForkJoinTask.invokeAll(createSubtasks(randomComputedPolynomial))
                    .stream()
                    .mapToDouble(ForkJoinTask::join)
                    .sum();*/
        }
    }

    //начнём со случая, что у нас есть один внутренний полином
    //передадим в этот метод одно рандомное значение из списка innerParts
    private List<PolynomialRecComputation> createSubtasks(Polynomial computedPolyomial) {
        List<PolynomialRecComputation> subTasks = new ArrayList<>();
        double[] bounds = Arrays.stream(
                new double[]{polynomial.getA(), polynomial.getB(), computedPolyomial.getA(), computedPolyomial.getB()}
        ).distinct().toArray();
        Arrays.sort(bounds);
        boolean[] helper = new boolean[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            helper[i] = !computedPolyomial.isNotBound(bounds[i]);
        }
        boolean current, next;
        for (int i = 0; i <helper.length - 1; i++) {
            current = helper[i];
            next = helper[i + 1];
            if ((!current && next) || (current && !next)) { //false true //true false
                //create new task to compute this section
                Polynomial p = new Polynomial(polynomial.getCoefficients(),
                        bounds[i], bounds[i + 1]);
                PolynomialRecComputation task = new PolynomialRecComputation(p, computedPolynomials);
                subTasks.add(task);
            }
        }
        return subTasks;
    }

    private double processing(Polynomial polynomial) {
        return IntegralCalculator.calc(polynomial.getA(), polynomial.getB(), polynomial.getCoefficients());
    }
}
