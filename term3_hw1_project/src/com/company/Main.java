package com.company;

public class Main {


    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            Convertings convertings = new Convertings();
            Thread t1 = new Thread(new MyRunnable(convertings));
            Thread t2 = new Thread(new MyRunnable2(convertings));

            t1.start();
            t2.start();

            while (t1.isAlive() || t2.isAlive()) {
                //wait
            }
            convertings.printResult();
        }

    }


}
