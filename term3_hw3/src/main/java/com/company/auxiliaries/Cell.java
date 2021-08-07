package com.company.auxiliaries;

public class Cell<T> {
    private T value;
    private boolean hasValue;

    //We imply that every particular instance of Cell class will be used for only one time
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
