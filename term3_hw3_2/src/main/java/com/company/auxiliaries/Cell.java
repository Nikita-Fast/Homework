package com.company.auxiliaries;

public class Cell<T> {
    private T value;
    private boolean hasValue;

    public synchronized void send(T value) {
        while (hasValue) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        T receivedValue = value;
        hasValue = false;
        notify();
        return receivedValue;
    }

}
