package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.ArrayList;

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
        // Check if any of two lists are null
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Integer> idxs = new ArrayList<>();
        List<String> yString = new ArrayList<String>();
        for (Object i: y) {
            yString.add(i.toString());
        }

        // Find indexes of elements from in x in y
        for (int i=0; i < x.size(); i++) {
            String element = String.valueOf(x.get(i));
            if (yString.contains(element)) {
                idxs.add(yString.indexOf(element));
            }
        }

        // Check if all elements are conteined in y (by length) and their order
        boolean state = false; 
        if (idxs.size() == x.size()) {
            state = true;
            for (int j=0; j < idxs.size() - 1; j++) {
                int currentIdx = (int) idxs.get(j);
                int nextIdx = (int) idxs.get(j+1);
                if (currentIdx > nextIdx) {
                    state = false;
                    break;
                }
            }
        }
        return state;
    }
}
