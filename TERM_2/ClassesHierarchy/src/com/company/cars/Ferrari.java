package com.company.cars;

import com.company.basic.Car;

public class Ferrari extends Car {
    private static final String brand = "Ferrari";
    private float accelerationTime;

    public Ferrari(int enginePower, int seats, String modelName, float accelerationTime) {
        super(enginePower, seats, brand, modelName);
        this.accelerationTime = accelerationTime;
    }

    public String getInfo() {
        return super.getInfo() + "\nacceleration from 0 to 100 km/h takes " + accelerationTime + " seconds";
    }
}
