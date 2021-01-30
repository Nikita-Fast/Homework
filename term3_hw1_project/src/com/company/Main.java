package com.company;


public class Main {

    static String[] allResults = new String[30_000];

    public static void main(String[] args) {
        int numberOfErrors = 0;
        for (int i = 0; i < 30_000; i++) {
            Convertings convertings = new Convertings();
            Thread t1 = new Thread(new MyRunnable(convertings), "Thread-0");
            Thread t2 = new Thread(new MyRunnable2(convertings), "Thread-1");

            t1.start();
            t2.start();

            while (t1.isAlive() || t2.isAlive()) {
                //wait
            }

            allResults[i] = convertings.getResult();

            if (!resultIsCorrect(convertings.getResult())) {
                System.out.println(convertings.getResult() + " <---------------INCORRECT!!! in attempt " + i);
                numberOfErrors++;
            }

            if (i % 5000 == 0) {
                System.out.println(i + " attempts passed!");
            }
        }
        if (numberOfErrors == 0) {
            System.out.println("NO INCORRECT ATTEMPTS!");
        }
        printAllResults();
        /*if (numberOfErrors > 0) {
            printAllResults();
        }*/

    }

    public static boolean resultIsCorrect(String result) {
        if (result.equals(new String("sb != safe,a=a,b=b")) ||
        result.equals(new String("sb != safe,b=b,a=a"))) {
            return true;
        }
        return false;
    }

    public static void printAllResults() {
        System.out.println("---------------------------------------------------");
        for (int i = 0; i < allResults.length; i++) {
            System.out.printf("%5d: %s\n", i, allResults[i]);
        }
    }
}
