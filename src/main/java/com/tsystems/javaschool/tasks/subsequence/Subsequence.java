package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {

        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }

        if (x.isEmpty()) {
            return true;
        }

        if (x.size() > y.size()) {
            return false;
        }

        List<Integer> indexes = new ArrayList<>();
        int lastEqualElement = 0;
        for (Object o : x) {
            for (int j = lastEqualElement, sizeY = y.size(); j < sizeY; j++) {
                if (o.equals(y.get(j))) {
                    indexes.add(j);
                    lastEqualElement = j + 1;
                    break;
                }
            }
        }

        return indexes.size() == x.size();
    }

}
