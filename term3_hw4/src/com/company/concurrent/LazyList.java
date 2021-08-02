package com.company.concurrent;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class LazyList<T> implements Iterable<T> {

    private final Node<T> head;
    private final AtomicInteger size = new AtomicInteger(0);

    public LazyList() {
        head = new Node<T>(Integer.MIN_VALUE);
        head.next = new Node<T>(Integer.MAX_VALUE);
    }

    private boolean validate(Node<T> pred, Node<T> curr) {
        return !pred.marked && !curr.marked && pred.next == curr;
    }

    public boolean add(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key == key) {
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
                    curr.unlock();
                }
            }
            finally {
                pred.unlock();
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        while (true) {
            Node<T> pred = head;
            Node<T> curr = head.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        if (curr.key != key) {
                            return false;
                        }
                        else {
                            curr.marked = true;
                            pred.next = curr.next;
                            size.getAndDecrement();
                            return true;
                        }
                    }
                }
                finally {
                    curr.unlock();
                }
            }
            finally {
                pred.unlock();
            }
        }
    }

    public boolean contains(T item) {
        int key = item.hashCode();
        Node<T> curr = head;
        while (curr.key < key) {
            curr = curr.next;
        }
        return curr.key == key && !curr.marked;
    }

    public Node<T> getHead() {
        return head;
    }

    public int size() {
        return size.get();
    }

    @Override
    public Iterator<T> iterator() {
        return new PlainLazyIterator<>(this);
    }
}
