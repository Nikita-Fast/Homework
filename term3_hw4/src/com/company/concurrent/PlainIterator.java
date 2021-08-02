package com.company.concurrent;

import java.util.Iterator;

public class PlainIterator<T> implements Iterator<T> {
    private Node<T> current;

    public PlainIterator(OptimisticList<T> list) {
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
