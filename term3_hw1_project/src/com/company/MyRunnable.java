package com.company;

public class MyRunnable implements Runnable {
    Convertings convertings;

    public MyRunnable(Convertings convertings) {
        this.convertings = convertings;
    }

    @Override
    public void run() {
        convertings.addProperty("a", "a");
    }
}
