package com.company.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node<T> {
    public T item;
    public int key;
    public Node<T> next;
    private final Lock lock = new ReentrantLock();
    public volatile boolean marked;

    public Node(int key) {
        this.key = key;
    }

    public Node(T item) {
        this.item = item;
        this.key = item.hashCode();
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
