package com.company.auxiliaries;

public class Cell<T> {
    private T value;
    private boolean hasValue;

    public synchronized void send(T value) {
        this.value = value;
        hasValue = true;
        notify();
    }

    public synchronized T receive() {
        while (!hasValue) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

}
