package com.company.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyThreadPool {

    private final ConcurrentLinkedQueue<Runnable> runnables;
    private final AtomicBoolean execute;

    public MyThreadPool(int threadCount) {
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
        }
    }
}
