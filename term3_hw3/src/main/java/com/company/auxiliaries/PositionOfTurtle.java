package com.company.auxiliaries;
import java.util.Objects;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PositionOfTurtle {
    private double x;
    private double y;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PositionOfTurtle that = (PositionOfTurtle) obj;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.angle, angle) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, angle);
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
