package com.company.basic;

public abstract class Car {
    private int enginePower;
    private int seats;
    private String brand;
    private String modelName;

    public Car(int enginePower, int seats, String brand, String modelName) {
        this.enginePower = enginePower;
        this.seats = seats;
        this.brand = brand;
        this.modelName = modelName;
    }

    public String getInfo() {
        return brand + " " + modelName + " is " + seats + " seats car\nengine power is " + enginePower;
    };

    public int getEnginePower() {
        return enginePower;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    public int getSeats() {
        return seats;
    }
}
