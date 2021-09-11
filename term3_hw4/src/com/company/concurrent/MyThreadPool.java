package com.company.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPool {

    private final ConcurrentLinkedQueue<Runnable> runnables;
    private final AtomicBoolean execute;
    private final int threadsNumber;
    private final AtomicInteger threadsDone = new AtomicInteger(0);

    public MyThreadPool(int threadCount) {
        threadsNumber = threadCount;
        this.runnables = new ConcurrentLinkedQueue<>();
        this.execute = new AtomicBoolean(true);

        for (int i = 0; i < threadCount; i++) {
            WorkerThread thread = new WorkerThread(this.execute, this.runnables);
            thread.start();
        }
    }

    public void execute(Runnable runnable) {
        if (execute.get()) {
            synchronized (runnables) {
                runnables.add(runnable);
                runnables.notify();
            }
        } else {
            throw new IllegalStateException("ThreadPool terminating, unable to execute runnable");
        }
    }

    public void terminate() {
        runnables.clear();
        stop();
    }

    public void stop() {
        execute.set(false);
    }

    public AtomicInteger getThreadsDone() {
        return threadsDone;
    }

    private class WorkerThread extends Thread{
        private final AtomicBoolean execute;
        private final ConcurrentLinkedQueue<Runnable> runnables;

        public WorkerThread(AtomicBoolean execute, ConcurrentLinkedQueue<Runnable> runnables) {
            this.execute = execute;
            this.runnables = runnables;
        }

        @Override
        public void run() {
            try {
                while (execute.get() || !runnables.isEmpty()) {
                    Runnable runnable;
                    while ((runnable = runnables.poll()) != null) {
                        runnable.run();
                    }
                    if (execute.get()) {
                        try {
                            synchronized (runnables) {
                                runnables.wait();
                            }
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
            //wakes thread in Solution class when all tasks are finished
            /*if (threadsDone.incrementAndGet() == threadsNumber) {
                synchronized (threadsDone) {
                    threadsDone.notify();
                }
            }*/
            synchronized (threadsDone) {
                threadsDone.notify();
            }
        }
    }
}
