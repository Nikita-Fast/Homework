package com.company.operations;

import com.company.auxiliaries.MyPair;

public class CoefficientsMix extends AssociativeOperation<MyPair>{
    @Override
    public MyPair apply(MyPair p1, MyPair p2) {
        int a = p1.getA() * p2.getA();
        int b = p1.getB() * p2.getA() + p2.getB();
        return new MyPair(a, b);
    }
}
