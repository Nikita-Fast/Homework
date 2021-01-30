package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Convertings {
    private StringBuilder sb = new StringBuilder("sb != safe");
    private Lock lock = new ReentrantLock();

    public void addProperty(String name, String value) {
        lock.lock();
        try {
            if (value != null && value.length() > 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(name).append('=').append(value);
            }
        } finally {
            lock.unlock();
        }
    }

    public String getResult() {
        return sb.toString();
    }

    public void printResult() {
        System.out.println(getResult());
    }
}
