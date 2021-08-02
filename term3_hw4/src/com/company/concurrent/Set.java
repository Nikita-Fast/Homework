package com.company.concurrent;

public interface Set<T> {
    boolean add(T x);
    boolean remove(T x);
    boolean contains(T x);
}
