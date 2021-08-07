package com.company.auxiliaries;

import java.util.ArrayList;

/**
 * Purpose of this class is to avoid code duplication when implementing createTable method
 * in ParallelScanner and ParallelPrefixScanner
 */

public abstract class Scanner<T> {

    protected ArrayList<ArrayList<Cell<T>>> createTable(int size) {
        ArrayList<ArrayList<Cell<T>>> table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Cell<T>> line = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                Cell<T> cell = new Cell<>();
                line.add(cell);
            }
            table.add(line);
        }
        return table;
    }
}
