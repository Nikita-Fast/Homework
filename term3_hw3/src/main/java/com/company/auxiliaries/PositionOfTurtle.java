package com.company.auxiliaries;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PositionOfTurtle {
    private final double x;
    private final double y;
    private double angle;

    public PositionOfTurtle(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        if (this.angle >= 360) {
            this.angle -= 360.0;
        }
    }

    public PositionOfTurtle() {
        this(0.0, 0.0, 0.0);
    }

    public PositionOfTurtle rotate(double rotationAngle) {
        double a = Math.toRadians(rotationAngle);
        double xr = x * cos(a) - y * sin(a);
        double yr = x * sin(a) + y * cos(a);
        return new PositionOfTurtle(xr, yr, this.angle + rotationAngle);
    }

    @Override
    public String toString() {
        return "PositionOfTurtle{" +
                "x=" + x +
                ", y=" + y +
                ", angle=" + angle +
                '}';
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }
}
