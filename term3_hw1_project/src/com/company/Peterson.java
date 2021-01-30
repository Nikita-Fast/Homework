package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Peterson implements Lock {

    private boolean[] flag = new boolean[2]; //пока что без volatile, хотя в книге используют с ним.
    private int victim;

    public int getThreadID() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            return 0;
        }
        else {
            return 1;
        }
    }
    @Override
    public void lock() {
        int i = getThreadID();
        int j = 1 - i;
        //System.out.println("i = " + i + " j = " + j);
        flag[i] = true;
        victim = i;
        while (flag[j] && victim == i) {
            //System.out.println(Thread.currentThread().getName() + " waits!!!");
            //wait
            //System.out.println(Thread.currentThread().getName() + "  - waits");
        }
    }

    @Override
    public void unlock() {
        int i = getThreadID();
        flag[i] = false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
