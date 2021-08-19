package com.company;

public class CarryOperator {

    private CarryState identityElement = CarryState.M;

    public CarryState calculate(CarryState c1, CarryState c2) {
        if (c2 == CarryState.M) {
            return c1;
        }
        if (c2 == CarryState.C || c2 == CarryState.N) {
            return c2;
        }
        return null;
    }

    public CarryState getIdentityElement() {
        return identityElement;
    }
}
