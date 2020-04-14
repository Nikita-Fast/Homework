package com.company.cars;

import com.company.basic.Car;

public class Jaguar extends Car {
    private String climateControlType;

    public Jaguar(int enginePower, int seats, String modelName, String climateControlType) {
        super(enginePower,seats, "Jaguar", modelName);
        this.climateControlType = climateControlType;
    }

    public String getInfo() {
        return super.getInfo() + "\nThe car is equipped with " + climateControlType + " climate control";
    }
}
