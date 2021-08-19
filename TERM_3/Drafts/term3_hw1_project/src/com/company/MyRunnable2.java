package com.company;

public class MyRunnable2 implements  Runnable{
    Convertings convertings;

    public MyRunnable2(Convertings convertings) {
        this.convertings = convertings;
    }

    @Override
    public void run() {
        convertings.addProperty("b", "b");
    }
}
