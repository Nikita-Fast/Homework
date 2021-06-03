package com.company.algorithms;

import com.company.auxiliaries.CarryState;
import com.company.auxiliaries.Cell;
import com.company.threads.BigIntAdditionThread;

public class BigIntAddition {
    private Cell<CarryState>[][] sendReceiveTable;
    private Thread[] threads;
    private int[] processedNumber1;
    private int[] processedNumber2;
    private int[] carries;
    private int[] result;
    private CarryState[] c;

    public void calculate(int threadsNumber, String number1, String number2) {
        int[] x = stringToArray(number1);
        int[] y = stringToArray(number2);
        //System.out.println(Arrays.toString(x));
        //System.out.println(Arrays.toString(y));

        initFields(Math.max(x.length, y.length));
        initSendReceiveTable(threadsNumber);
        initThreads(threadsNumber, c, x, y, carries, result);
        startThreads(threadsNumber);
    }

    private int[] stringToArray(String s) {
        int length = s.length();
        int[] a = new int[length];
        String rev = new StringBuilder(s).reverse().toString();
        for (int i = 0; i < length; i++) {
            a[i] = Integer.parseInt(String.valueOf(rev.charAt(i)));
        }
        return a;
    }

    private void initFields(int length) {
        c = new CarryState[length];
        carries = new int[length + 1];
        result = new int[length + 1];
    }

    private void startThreads(int threadsNumber) {
        for (int i = 0; i < threadsNumber; i++) {
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initThreads(int threadsNumber, CarryState[] c, int[] number1, int[] number2,
                             int[] carries, int[] result) {
        int step = c.length / threadsNumber;
        int min = 0;
        int max = step - 1;
        threads = new BigIntAdditionThread[threadsNumber];

        processNumbers(number1, number2);

        for (int i = 0; i < threadsNumber; i++) {
            threads[i] = new BigIntAdditionThread(min, max, c, i, sendReceiveTable, threadsNumber,
                    processedNumber1, processedNumber2, carries, result);
            min += step;
            max += step;
            if (i == threadsNumber - 2) {
                max = c.length - 1;
            }
        }
    }

    private void processNumbers(int[] number1, int[] number2) {
        int length = Math.max(number1.length, number2.length);
        int[] a = new int[length];
        int[] b = new int[length];
        System.arraycopy(number1, 0, a, 0, number1.length);
        System.arraycopy(number2, 0, b, 0, number2.length);
        processedNumber1 = a;
        processedNumber2 = b;
    }

    private void initSendReceiveTable(int threadsNumber) {
        sendReceiveTable = new Cell[threadsNumber][threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            for (int j = 0; j < threadsNumber; j++) {
                sendReceiveTable[i][j] = new Cell<>();
            }
        }
    }

    public int[] getResult() {
        return result;
    }
}
