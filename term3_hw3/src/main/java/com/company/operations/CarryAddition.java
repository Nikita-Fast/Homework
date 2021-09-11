package com.company.operations;

import com.company.auxiliaries.CarryState;

public class CarryAddition extends AssociativeOperationWithNeutralElement<CarryState> {

    public CarryAddition() {
        setNeutralElement(CarryState.M);
    }

    @Override
    public CarryState apply(CarryState x, CarryState y) {
        if (y == CarryState.C || y == CarryState.N) {
            return y;
        }
        if (y == CarryState.M) {
            return x;
        }
        throw new RuntimeException("Carry addition function works incorrectly!");
    }
}
