package com.company.operations;

public class FindMin extends AssociativeOperation<Integer> {

    @Override
    public Integer apply(Integer x, Integer y) {
        return x <= y ? x : y;
    }
}
