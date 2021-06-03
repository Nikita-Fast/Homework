package com.company.operations;

import com.company.auxiliaries.MyPair;

public class CoefficientsMix extends AssociativeOperation<MyPair<Integer>>{
    @Override
    public MyPair<Integer> apply(MyPair<Integer> p1, MyPair<Integer> p2) {
        int a = p1.getA() * p2.getA();
        int b = p1.getB() * p2.getA() + p2.getB();
        return new MyPair<>(a, b);
    }
}
