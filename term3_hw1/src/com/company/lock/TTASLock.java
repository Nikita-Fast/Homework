package com.company.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TTASLock implements Lock {

    private final AtomicBoolean isBusy;

    public TTASLock() {
        this.isBusy = new AtomicBoolean(false);
    }

    @Override
    public void lock() {
        while (true) {
            while (isBusy.get()) { }
            if (!isBusy.getAndSet(true)) {
                return;
            }
        }
    }

    @Override
    public void unlock() {
        isBusy.set(false);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

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
