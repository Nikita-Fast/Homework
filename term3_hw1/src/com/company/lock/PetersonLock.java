package com.company.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PetersonLock implements Lock {

    private final boolean[] flag;
    private int victim;

    public PetersonLock() {
        flag = new boolean[2];
        victim = 0;
    }

    @Override
    public void lock() {
        int i = (int)(Thread.currentThread().getId() % 2);
        int j = 1 - i;
        flag[i] = true;
        victim = i;
        while (flag[j] && victim == i) {
            continue;
        }
    }

    @Override
    public void unlock() {
        int myId = (int)(Thread.currentThread().getId() % 2);
        flag[myId] = false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
