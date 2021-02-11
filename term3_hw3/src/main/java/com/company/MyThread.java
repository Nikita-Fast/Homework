package com.company;

public class MyThread extends Thread {
    private static int threadsCreated;

    private int pid;
    private Link[][] links;
    private int[] numberA;
    private int[] numberB;
    private CarryState[] carries;
    private int[] result;

    private int begin;
    private int end;

    private CarryOperator operator;

    public MyThread(int[] numberA, int[] numberB, CarryState[] carries, int[] result, Link[][] links) {
        super();
        setPid();
        this.links = links;
        this.numberA = numberA;
        this.numberB = numberB;
        this.carries = carries;
        this.result = result;
        this.operator = new CarryOperator();
        calculateBoundsOfWorkForThread();
    }

    @Override
    public void run() {
        parallelBigIntAdd();
    }

    private void parallelBigIntAdd() {
        calculateCarries();
        parallelPrefixScanForBigIntAdd();
        calculateResult();
    }

    private void calculateBoundsOfWorkForThread() {
        begin = (carries.length / BigIntegersAdd.THREADS_NUMBER) * pid;
        end = (carries.length / BigIntegersAdd.THREADS_NUMBER) * (pid + 1) - 1;
        if (pid == BigIntegersAdd.THREADS_NUMBER - 1) {
            end = carries.length - 1;
        }
    }

    private void calculateCarries() {
        for (int i = begin; i <= end; i++) {
            int sumOfDigits = 0;
            if (i < numberA.length) {
                sumOfDigits += numberA[i];
            }
            if (i < numberB.length) {
                sumOfDigits += numberB[i];
            }
            carries[i] = calculateCarryState(sumOfDigits);
        }
    }

    private void calculateResult() {
        if (pid == 0) {
            result[0] = (numberA[0] + numberB[0]) % 10;
        }
        for (int i = begin; i <= end; i++) {
            int carry = 0;
            if (carries[i] == CarryState.C) {
                carry = 1;
            }
            if (i == carries.length - 1) {
                result[i + 1] = carry;
            }
            else {
                int a = 0;
                int b = 0;
                if (i + 1 < numberA.length) {
                    a = numberA[i + 1];
                }
                if (i + 1 < numberB.length) {
                    b = numberB[i + 1];
                }
                result[i + 1] = (a + b + carry) % 10;
            }
        }
    }

    private void parallelPrefixScanForBigIntAdd() {
        CarryState total = operator.getIdentityElement();
        for (int i = begin; i <= end; i++) {
            total = operator.calculate(total, carries[i]);
        }
        // perform collect phase
        int k;
        for (k = 1; k < BigIntegersAdd.THREADS_NUMBER; k *= 2) {
            if((pid & k) == 0) {
                links[pid][pid + k].send(total);
                break;
            } else {
                total = operator.calculate(total, (CarryState)links[pid - k][pid].receive());
            }
        }
        // perform distribute phase
        if (pid == BigIntegersAdd.THREADS_NUMBER - 1) {
            total = operator.getIdentityElement(); // reset last processor’s subtotal to 0
        }
        if (k >= BigIntegersAdd.THREADS_NUMBER) {
            k /= 2;
        }
        while (k > 0) {
            if ((pid & k) == 0) {
                links[pid][pid + k].send(total);
                total = (CarryState) links[pid + k][pid].receive();
            }
            else {
                CarryState t = (CarryState) links[pid - k][pid].receive();
                links[pid][pid - k].send(total);
                total = operator.calculate(t, total);
            }
            k /= 2;
        }
        // update array to have the prefix sums
        for (int i = begin; i <= end; i++) {
            total = operator.calculate(total, carries[i]);
            carries[i] = total;
        }
    }

    private CarryState calculateCarryState(int sum) {
        if (sum > 9) {
            return CarryState.C;
        }
        if (sum == 9) {
            return CarryState.M;
        }
        if (sum < 9) {
            return CarryState.N;
        }
        System.exit(11110000);
        return null;
    }

    private void setPid() {
        pid = threadsCreated % BigIntegersAdd.THREADS_NUMBER;
        threadsCreated++;
    }
}
