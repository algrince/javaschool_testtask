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

        // Validate elements of the input
        if (inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();
        } 

        // Calculate number of rows in function of input length
        int InputSize = inputNumbers.size();
        int rows = getPiramidSize(InputSize);
        int columns = 2 * rows - 1;

        Collections.sort(inputNumbers);
        int[][] matrix = createMatrixWithZeros(rows, columns);
        
        int numberIdx = 0;
        int followingPoint = 0;
        // (number of the row) == number of ints in the row
        for (int i = 1; i <= matrix.length; i++) {
            int numberInRowCounter = 0;
            int rowNumber = i;
            int startingPoint = (int) (columns - 2 * rowNumber + 1) / 2;
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
        /*
         * R = row size; I = input size
         * For finding a relation between a size of given array and rows in piramid:
         * The number of no-zeros in a base row is equal to the number of rows.
         * The piramid is stacking from 1 to R one by one (there is 1 number in the first row, 2 -- in the second, etc...)
         * So it is possible to use a formula for the sum of the first natural numbers:
         * I = R(R+1)/2
         * To find R:
         * 2I = R^2 + R ---> R = sqrt(2I + 0.25) - 0.5
         */
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
        // Create a matrix with defined size and fill it with 0
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            } 
        }
        return matrix;
    }
}
