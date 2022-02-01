package com.tsystems.javaschool.tasks.pyramid;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {

        int basis = checkElements(inputNumbers);

        inputNumbers.sort(Comparator.comparingInt(p -> p));

        return createArray(basis, inputNumbers);
    }

    private int checkElements(List<Integer> inputNumbers) {
        for (Integer inputNumber : inputNumbers) {
            if (inputNumber == null)
                throw new CannotBuildPyramidException("Can't build pyramid");
        }

        long count = 0;

        for (int i = 0, size = inputNumbers.size(); size >= count; i++, count += i) {
            if (count == size) {
                return i * 2 - 1;
            } else if (count + i + 1 > size) {
                throw new CannotBuildPyramidException("Can't build pyramid");
            }
        }

        return 0;
    }

    private int[][] createArray(int basis, List<Integer> inputNumbers) {
        int lines = basis / 2 + 1;
        int middleIndex = basis / 2;
        int[][] array = new int[lines][basis];
        Iterator<Integer> iterator = inputNumbers.iterator();

        for (int i = 0; i < lines; i++) {
            int countNumbers = i + 1;
            for (int j = 0; j < basis; j++) {

                if ((j == middleIndex - i || j > 1 && array[i][j - 2] != 0) && iterator.hasNext()) {
                    array[i][j] = iterator.next();
                    countNumbers--;
                }
                if (countNumbers == 0) break;
            }
        }

        return array;
    }

}
