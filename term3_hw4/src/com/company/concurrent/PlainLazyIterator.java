package com.company.concurrent;

import java.util.Iterator;

public class PlainLazyIterator<T> implements Iterator<T> {
    Node<T> current;

    public PlainLazyIterator(LazyList<T> list) {
        current = list.getHead();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        T item = current.item;
        current = current.next;
        return item;
    }
}
