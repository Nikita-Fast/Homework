package com.company.operations;

public class Addition extends AssociativeOperationWithNeutralElement<Integer> {
    public static final int ZERO = 0;

    public Addition() {
        setNeutralElement(ZERO);
    }

    @Override
    public Integer apply(Integer x, Integer y) {
        return x + y;
    }
}
