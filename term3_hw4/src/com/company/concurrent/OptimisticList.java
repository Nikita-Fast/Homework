package com.company.concurrent;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class OptimisticList<T> implements Set<T>, Iterable<T>{

    private Node<T> head; //has max int hash
    private AtomicInteger size = new AtomicInteger(0);

    public OptimisticList() {
        head = new Node<>(Integer.MIN_VALUE);
        head.next = new Node<>(Integer.MAX_VALUE);
    }

    public int size() {
        return size.get();
    }

    @Override
    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = pred.next;
            while (curr.key <= key) {
                pred = curr; curr = curr.next;
            }
            pred.lock(); curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (pred.key == key) { //if (curr.key == key) так было в книге
                        return false;
                    }
                    else {
                        Node<T> node = new Node<>(item);
                        node.next = curr;
                        pred.next = node;
                        size.getAndIncrement();
                        return true;
                        }
                    }
            }
            finally {
                pred.unlock(); curr.unlock();
            }
        }
    }

    @Override
    public boolean remove(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = pred.next;
            while (curr.key < key) {
                pred = curr; curr = curr.next;
            }
            pred.lock(); curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (curr.key == key) {
                        pred.next = curr.next;
                        size.getAndDecrement();
                        return true;
                    } else {
                        return false;
                    }
                }
            } finally {
                pred.unlock(); curr.unlock();
            }
        }
    }

    @Override
    public boolean contains(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = this.head; // sentinel node;
            Node<T> curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            try {
                pred.lock(); curr.lock();
                if (validate(pred, curr)) {
                    return (curr.key == key);
                }
            } finally { // always unlock
                pred.unlock(); curr.unlock();
            }
        }
    }

    private boolean validate(Node<T> pred, Node<T> curr) {
        Node<T> node = head;
        while (node.key <= pred.key) {
            if (node == pred) {
                return pred.next == curr;
            }
            node = node.next;
        }
        return false;
    }

    public Node<T> getHead() {
        return head;
    }

    @Override
    public Iterator<T> iterator() {
        return new PlainIterator<>(this);
    }
}
