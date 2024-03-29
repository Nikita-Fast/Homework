package com.company.operations;

import com.company.auxiliaries.CarryState;

public class CarryAddition extends AssociativeOperationWithNeutralElement<CarryState> {

    public CarryAddition() {
        setNeutralElement(CarryState.M);
    }

    @Override
    public CarryState apply(CarryState x, CarryState y) {
        CarryState result;
        if (y == CarryState.C || y == CarryState.N) {
            result = y;
        }
        else {
            result = x;
        }
        return result;
    }
}
