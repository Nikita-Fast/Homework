package com.company.auxiliaries;

public class MyPair<T> {
    private T a;
    private T b;

    public MyPair(T a, T b) {
        this.a = a;
        this.b = b;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    @Override
    public String toString() {
        return "(" + a.toString() + ", " + b.toString() + ")";
    }
}
