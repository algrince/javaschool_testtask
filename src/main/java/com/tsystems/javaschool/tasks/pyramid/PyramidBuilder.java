package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;
import java.util.Collections;


public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        int InputSize = inputNumbers.size();
        int rows = getPiramidSize(InputSize);
        int columns = 2 * rows - 1;
        if (inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();
        } 
        Collections.sort(inputNumbers);
        int[][] matrix = createMatrixWithZeros(rows, columns);

        
        int numberIdx = 0;
        int followingPoint = 0;
        // (number of the row) == number of ints in the row
        for (int i = 1; i <= matrix.length; i++) {
            int numberInRowCounter = 0;
            int rowNumber = i;
            int startingPoint = (int) (columns - rowNumber - (rowNumber - 1)) / 2;
            followingPoint = startingPoint;
            while (numberInRowCounter < rowNumber) {
                matrix[rowNumber - 1][followingPoint] = inputNumbers.get(numberIdx);
                numberIdx++;
                numberInRowCounter++;
                followingPoint = followingPoint + 2;
            }
        }

        return matrix;
    }

    public int getPiramidSize(int InputSize) throws CannotBuildPyramidException {
        int rows = 0;
         double rowSize = Math.sqrt(2*InputSize + 0.25) - 0.5;
        if (rowSize % 1 != 0) {
            throw new CannotBuildPyramidException();
        } else {
            rows = (int) rowSize;
        }
        if (rows == 0) {
            throw new CannotBuildPyramidException();
        }
        return rows;
    }

    public int[][] createMatrixWithZeros(int rows, int columns) {
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            } 
        }
        return matrix;
    }
}
