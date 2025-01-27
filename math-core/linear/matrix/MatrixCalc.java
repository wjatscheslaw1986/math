/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.matrix;

import linear.matrix.exception.MatrixException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import combinatoric.IndexCombinationsGenerator;
import static linear.matrix.MatrixUtil.*;

public class MatrixCalc {

    /**
     * The method multiplies the argument matrix on a 64-bit floating point number
     * The method does not modify the original matrix object
     *
     * @param matrix     - the original matrix object
     * @param multiplier - the number to multiply the matrix on
     * @return a new matrix object which is a result of multiplication
     * @throws MatrixException - if matrix is malformed
     */
    public static double[][] multiplyMatrixByNumber(double[][] matrix, double multiplier) throws MatrixException {
        validateMatrix(matrix);
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            System.arraycopy(matrix[rowNum], 0, result[rowNum], 0, matrix[rowNum].length);
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                result[rowNum][colNum] = result[rowNum][colNum] * multiplier;
            }
        }
        return result;
    }

    /**
     * The method multiplies the argument matrix (on the left) on a vector-column (a
     * single column matrix, on the right) The method does not modify the original
     * matrix object
     *
     * @param matrix - the original matrix object
     * @param column - the vector-column to multiply on
     * @return a new matrix object which is a result of multiplication
     * @throws MatrixException - if matrix is malformed
     */
    public static double[] multiplyMatrixByColumn(double[][] matrix, double[] column) throws MatrixException {
        validateMatrix(matrix);
        if (matrix[0].length != column.length)
            throw new MatrixException("If you're about to multiply a matrix from the left on a vector-column from the right, the matrix should have same row length as the vector-column's amount of elements ");
        double[] result = new double[matrix[0].length];
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                result[rowNum] = result[rowNum] + matrix[rowNum][colNum] * column[colNum];
            }
        }
        return result;
    }

    /**
     * The method multiplies the two argument matrices The method does not modify
     * the original matrix object
     *
     * @param matrixLeft   - the matrix on the left of the multiplication equation
     * @param matrixRight- the matrix on the right of the multiplication equation
     * @return a new matrix object which is a result of multiplication
     * @throws MatrixException - if the matrix is malformed
     */
    public static double[][] multiplyMatrices(double[][] matrixLeft, double[][] matrixRight) throws MatrixException {
        if (!canMultiply(matrixLeft, matrixRight))
            throw new MatrixException("In order to multiply matrices, the one on the left should have same row length as the column length of the one on the right");
        double[][] result = new double[matrixLeft.length][matrixRight[0].length];
        for (int rowNumLeft = 0; rowNumLeft < matrixLeft.length; rowNumLeft++) {
            for (int colNumRight = 0; colNumRight < matrixRight[0].length; colNumRight++) {
                for (int colNumLeft = 0; colNumLeft < matrixLeft[0].length; colNumLeft++) {
                    result[rowNumLeft][colNumRight] = result[rowNumLeft][colNumRight]
                            + matrixLeft[rowNumLeft][colNumLeft] * matrixRight[colNumLeft][colNumRight];
                }
            }
        }
        return result;
    }

    /**
     * The method transposes the matrix passed as an argument The method does not
     * modify the original matrix object
     *
     * @param matrix - the original matrix object to transpose
     * @return a new matrix object which is a result of matrix transposition
     * @throws MatrixException - if the matrix is malformed
     */
    public static double[][] transposeMatrix(double[][] matrix) throws MatrixException {
        validateMatrix(matrix);
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++)
            for (int col = 0; col < matrix[0].length; col++)
                result[col][row] = matrix[row][col];
        return result;
    }

    /**
     * The method calculates a determinant of the given matrix.
     *
     * @param matrix - the given matrix
     * @return the determinant of the matrix
     * @throws MatrixException - if the matrix is malformed
     */
    public static double det(double[][] matrix) throws MatrixException {
        validateSquareMatrix(matrix);
        double result = Double.MAX_VALUE;
        if (matrix.length < 2)
            return matrix[0][0];
        if (matrix.length == 2)
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        if (matrix.length == 3)
            return detSarrus(matrix);
        if (matrix.length > 3) {
            result = 0;
            for (int col = 0; col < matrix[0].length; col++)
                if (matrix[0][col] != 0)
                    result = result + matrix[0][col] * cofactor(matrix, 1, col + 1);
        }
        return result;
    }

    /**
     * This method calculates a determinant of the 3x3 matrix by Sarrus method
     *
     * @param matrix - the original matrix object
     * @return the determinant of the matrix
     * @throws MatrixException - if the matrix is malformed
     */
    public static double detSarrus(double[][] matrix) throws MatrixException {
        validateSquareMatrix(matrix);
        if (matrix.length != 3 || matrix[2].length != 3)
            throw new MatrixException("Sarrus method is only applicable to 3x3 matrices");
        // System.out.println(
        // "DET: " + matrix[0][0] * matrix[1][1] * matrix[2][2] + " + " +
        // matrix[0][1] * matrix[1][2] * matrix[2][0] + " + " +
        // matrix[1][0] * matrix[2][1] * matrix[0][2] + " - " +
        // matrix[0][2] * matrix[1][1] * matrix[2][0] + " - " +
        // matrix[1][0] * matrix[0][1] * matrix[2][2] + " - " +
        // matrix[2][1] * matrix[1][2] * matrix[0][0]);
        return matrix[0][0] * matrix[1][1] * matrix[2][2] + matrix[0][1] * matrix[1][2] * matrix[2][0]
                + matrix[1][0] * matrix[2][1] * matrix[0][2] - matrix[0][2] * matrix[1][1] * matrix[2][0]
                - matrix[1][0] * matrix[0][1] * matrix[2][2] - matrix[2][1] * matrix[1][2] * matrix[0][0];

    }

    /**
     * The method calculates a reversed matrix of the provided one as an argument
     * The method does not modify the original matrix object
     *
     * @param matrix - the original matrix object
     * @return a new matrix object which is a reversed matrix passed as an argument
     * @throws MatrixException - if the matrix is malformed
     */
    public static double[][] reverse(double[][] matrix) throws MatrixException {
        double det = validateCramerAndReturnDeterminant(matrix);
        return multiplyMatrixByNumber(transposeMatrix(cofactorsMatrix(matrix)), 1.0d / det);
    }

    /**
     * The method calculates a cofactor for an element of the given matrix which is
     * situated on the intersection of the given row and column The method does not
     * modify the original matrix object BEWARE: row and col are not Java array's
     * indices but math matrix row and column numbers instead, from 1 to
     * 'matrix.length', inclusive
     *
     * @param matrix - the original matrix object
     * @param row    - the given row number (index + 1) of the element
     * @param col    - the given column number (index + 1) of the element
     * @return - the cofactor for the element of the matrix which is on the
     *         intersection of the given row and column
     * @throws MatrixException - if the matrix is malformed
     */
    public static double cofactor(double[][] matrix, int row, int col) throws MatrixException {
        if (matrix.length == 1)
            return matrix[0][0];
        return (Math.pow(-1.0f, row + col)) * det(excludeColumnAndRow(matrix, row, col));
    }

    /**
     * This method calculates a cofactor matrix for all the elements of the original
     * one
     *
     * @param matrix - the original matrix object
     * @return a new matrix object with cofactors instead of the original elements
     * @throws MatrixException - if the matrix is malformed
     */
    public static double[][] cofactorsMatrix(double[][] matrix) throws MatrixException {
        double[][] result = new double[matrix.length][matrix.length];
        for (int row = 1; row <= matrix.length; row++)
            for (int col = 1; col <= matrix.length; col++)
                result[row - 1][col - 1] = cofactor(matrix, row, col);
        return result;
    }

    /**
     * This method returns a matrix 1 row and 1 column lesser than the original one,
     * excluding the row and the column
     *
     * @param matrix - the original matrix
     * @param row    - number (index + 1) of a row to exclude
     * @param col    - number (index + 1) of a column to exclude
     * @return a matrix one row one column less in size. The row and column to
     *         exclude are provided as two arguments
     */
    public static double[][] excludeColumnAndRow(double[][] matrix, int row, int col) {
        double[][] submatrix = new double[matrix.length - 1][matrix.length - 1];
        int submatrixRow = 1, submatrixCol = 1;
        for (int rowNum = 1; rowNum <= matrix.length; rowNum++) {
            if (rowNum == row)
                continue;
            for (int colNum = 1; colNum <= matrix.length; colNum++) {
                if (colNum == col)
                    continue;
                submatrix[submatrixRow - 1][submatrixCol - 1] = matrix[rowNum - 1][colNum - 1];
                submatrixCol++;
            }
            submatrixCol = 1;
            submatrixRow++;
        }
        return submatrix;
    }

    /**
     * This method returns a matrix 1 column lesser than the original one, excluding
     * one column.
     *
     * @param matrix - the original matrix
     * @param col    - number (index + 1) of a column to exclude
     * @return a matrix one row one column less in size. The column to exclude are
     *         provided as an argument
     */
    public static double[][] excludeColumn(double[][] matrix, int col) {
        double[][] submatrix = new double[matrix.length][matrix[0].length - 1];
        int submatrixRow = 1, submatrixCol = 1;
        for (int rowNum = 1; rowNum <= matrix.length; rowNum++) {
            for (int colNum = 1; colNum <= matrix[0].length; colNum++) {
                if (colNum == col)
                    continue;
                submatrix[submatrixRow - 1][submatrixCol - 1] = matrix[rowNum - 1][colNum - 1];
                submatrixCol++;
            }
            submatrixCol = 1;
            submatrixRow++;
        }
        return submatrix;
    }

    /**
     * This method substitutes one column of a given matrix with another one,
     * at a certain position 'colNum'.
     *
     * @param matrix - the original matrix
     * @param column - an array of same column values, from top to bottom
     * @param colNum - column number (index + 1) of the original matrix to
     *               substitute with 'column'
     * @return new matrix of same size but with one of its columns substituted by
     *         'column'
     */
    public static double[][] substituteColumn(final double[][] matrix, final double[] column, final int colNum) {
        if (matrix[0].length <= colNum)
            throw new ArrayIndexOutOfBoundsException(String
                .format("Column index %n is out of matrix columns size of %n", colNum - 1, matrix[0].length));
        final double[][] result = MatrixUtil.copy(matrix);
        for (int row = 0; row < matrix.length; row++)
            result[row][colNum - 1] = column[row];
        return result;
    }

    /**
     * Вырезать квадратную подматрицу (не путать с алгебраическим дополнением!) для данной матрицы.
     * Подходит для оконного алгоритма.
     * 
     * @param matrix         исходная матрица
     * @param rowOffset      индекс ряда исходной матрицы, который будет нулевым для вырезаемой
     * @param colOffset      индекс колонки исходной матрицы, который будет нулевым для вырезаемой
     * @param squareSideSize размер стороны вырезаемой квадратной матрицы
     * @return вырезанная квадратная матрица
     */
    public static double[][] squareSubmatrix(double[][] matrix, int rowOffset, int colOffset, int squareSideSize) {
        if (matrix.length < rowOffset + squareSideSize || matrix[0].length < colOffset + squareSideSize) {
            throw new ArrayIndexOutOfBoundsException("Искомая подматрица вышла за рамки основной.");
        }
        double[][] result = new double[squareSideSize][squareSideSize];
        for (int row = rowOffset, resultRow = 0; resultRow < squareSideSize; row++, resultRow++) {
            for (int col = colOffset, columnRow = 0; columnRow < squareSideSize; col++, columnRow++) {
                result[resultRow][columnRow] = matrix[row][col];
            }
        }
        return result;
    }

    /**
     * Сложить матрицы 'b' и 'a'.
     * 
     * @param a первая матрица
     * @param b вторая матрица
     * @return результат сложения матриц 'b' и 'a'
     * @throws MatrixException если какая-то из матриц неправильная
     */
    public static double[][] sum(double[][] a, double[][] b) throws MatrixException {
        if (!isEqualDimensions(a, b))
            throw new MatrixException("Matrices need to be of equal size if you want to get their sum");
        double[][] result = new double[a.length][a[0].length];
        for (int row = 0; row < a.length; row++)
            for (int col = 0; col < a[0].length; col++)
                result[row][col] = a[row][col] + b[row][col];
        return result;
    }

    /**
     * Вычесть матрицу 'b' из матрицы 'a'.
     * 
     * @param a первая матрица
     * @param b вторая матрица
     * @return результат вычитания матрицы 'b' из матрицы 'a'
     * @throws MatrixException если какая-то из матриц неправильная
     */
    public static double[][] subtract(double[][] a, double[][] b) throws MatrixException {
        return sum(a, multiplyMatrixByNumber(b, -1.0d));
    }

    /**
     * Calculate rank of a given matrix.
     * Source: {@link https://cp-algorithms.com/linear_algebra/rank-matrix.html}
     * 
     * @param originalMatrix is the given matrix for finding its rank
     * @return rank
     * @throws MatrixException if given matrix is malformed
     */
    public static int rank(final double[][] originalMatrix) throws MatrixException {
        validateMatrix(originalMatrix);
        final double[][] matrix = MatrixUtil.copy(originalMatrix);
        final double eps = 1e-9;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int rank = 0;

        final boolean[] rowSelection = new boolean[rows];

        for (int i = 0; i < cols; ++i) {
            int j;
            for (j = 0; j < rows; ++j) {
                if (!rowSelection[j] && Math.abs(matrix[j][i]) > eps) {
                    break;
                }
            }

            if (j != rows) {
                ++rank;
                rowSelection[j] = true;
                for (int p = i + 1; p < cols; ++p)
                    matrix[j][p] /= matrix[j][i];
                for (int k = 0; k < rows; ++k) {
                    if (k != j && Math.abs(matrix[k][i]) > eps) {
                        for (int p = i + 1; p < cols; ++p) {
                            matrix[k][p] -= matrix[j][p] * matrix[k][i];
                        }
                    }
                }
            }
        }
        return rank;
    }

    /**
     * Calculate a rank of a given matrix using a determinant
     * method (i.e. rank equals to the max non-degenerate minor, or zero).
     * The most straightforward approach.
     * 
     * @param matrix is the given matrix for finding its rank
     * @return rank a rank of the given matrix
     * @throws MatrixException if given matrix is malformed
     * @deprecated the approach from {@linkplain https://cp-algorithms.com/linear_algebra/rank-matrix.html} seems
     *             to be more preferable
     */
    @Deprecated
    public static int rankByMinors(final double[][] matrix) throws MatrixException {
        validateMatrix(matrix);
        int rows = matrix.length, cols = matrix[0].length;
        int squareSideSize = Math.min(rows, cols);
        int currentMinorSize = 1;

        final Deque<double[][]> minorsToCheckStack = new ArrayDeque<double[][]>();

        while (currentMinorSize <= squareSideSize) {
            final var listOfRowIndicesCombinations = IndexCombinationsGenerator.generate(rows, currentMinorSize);
            final var listOfColumnIndicesCombinations = IndexCombinationsGenerator.generate(cols, currentMinorSize);
            for (var rowArray : listOfRowIndicesCombinations)
                for (var colArray : listOfColumnIndicesCombinations) {
                    final double[][] minor = new double[currentMinorSize][currentMinorSize];
                    int mr = 0, mc = 0;
                    for (int r : rowArray) {
                        for (int c : colArray) {
                            minor[mr][mc++] = matrix[r][c];
                        }
                        mr++;
                        mc = 0;
                    }
                    minorsToCheckStack.push(minor);
                }
            currentMinorSize++;
        }
        while (!minorsToCheckStack.isEmpty()) {
            final double[][] checkedMinor = minorsToCheckStack.pop();
            if (det(checkedMinor) != 0) {
                return checkedMinor.length;
            }
        }
        return 0;
    }

    /**
     * Checks if this matrix is a trapezoid.
     * Generated by ChatGPT.
     *
     * @param matrix - the supposed trapezoid matrix
     * @return true if none of main diagonal's values are zero and values under main
     *         diagonal values are all zeroes, otherwise false
     * @throws MatrixException if argument matrix is malformed
     */
    public static boolean isTrapezoidForm(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Keeps track of the number of leading zeros in the previous row
        int previousLeadingZeros = -1;

        for (int i = 0; i < rows; i++) {
            int currentLeadingZeros = 0;

            // Count leading zeros in the current row
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    currentLeadingZeros++;
                } else {
                    break;
                }
            }

            // Check if the current row has at least as many leading zeros as the previous row
            if (currentLeadingZeros < previousLeadingZeros) {
                return false;
            }

            // Update the number of leading zeros in the previous row
            previousLeadingZeros = currentLeadingZeros;
        }

        return true;
    }

    /**
     * This method produces a trapezoid matrix of any matrix or the original one.
     * This method does not modify the original matrix object.
     *
     * @param matrix - a link to original matrix argument object
     * @return a trapezoid form of a given <i>matrix</i> argument, or the original matrix if trapezoid
     *         can not be found for this matrix.
     * @throws MatrixException
     */
    public static double[][] tryTrapezoid(double[][] matrix) throws MatrixException {
        double[][] result = MatrixUtil.copy(matrix);
        int shortestSideLength = Math.min(result.length, result[0].length);
        for (int i = 0; i < shortestSideLength; i++) {
            int maxSwitchRowNumber = result.length - 1, maxSwitchColNumber = result[0].length - 1;
            while (maxSwitchRowNumber + maxSwitchColNumber > 0 && result[i][i] == .0d) {
                if (maxSwitchRowNumber > 0)
                    result = swapRows(result, i + 1, (maxSwitchRowNumber--) + 1);
                else if (maxSwitchColNumber > 0)
                    result = swapColumns(result, i + 1, (maxSwitchColNumber--) + 1);
            }
            if (result[i][i] == .0d)
                return matrix; // current matrix may not have a trapezoid form. Return the original one
            for (int j = i + 1; j < result.length; j++)
                if (result[j][i] != .0d)
                    result = addMultipliedRow(result, j + 1, i + 1, (-1) * result[j][i] / result[i][i]);
            result = MatrixUtil.shrinkZeroColumns(matrix);
            shortestSideLength = Math.min(result.length, result[0].length);
        }
        if (isRowEchelonForm(result))
            return result;
        return matrix;
    }

    /**
     * Produce a row echelon form matrix of a given one.
     * The matrix is in row echelon form, as it satisfies the next necessary conditions:
     * <ul>
     * <li>The leading entries move progressively to the right.</li>
     * <li>Entries below each leading entry are zero.</li>
     * <li>There are no zero rows, so no violation occurs.</li>
     * </ul>
     * This method does not modify the original matrix object.
     *
     * @param matrix given
     * @return a new object which is a row echelon form <i>matrix</i> for the original one
     */
    public static double[][] toRowEchelonForm(final double[][] matrix) {

        if (matrix.length < 2)
            return matrix;

        int firstNonZeroColumnIndex = -1;

        Outer : for (int col = 0; col < matrix[0].length; col++) {
            for (int row = 0; row < matrix.length; row++) {
                if (matrix[row][col] != .0d) {
                    firstNonZeroColumnIndex = col;
                    break Outer;
                }
            }
        }

        // Zero matrix is a row echelon form matrix
        if (firstNonZeroColumnIndex < 0)
            return matrix;

        double[][] result = MatrixUtil.copy(matrix);
        MatrixUtil.clean(result);

        // If there are zero rows they must be at the bottom
        for (int row = 0, end = result.length; row < end; row++) {
            if (isZeroRow(result, row)) {
                try {
                    result = swapRows(result, row + 1, end--);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("dslkfj");
                }
            }
        }

        for (int col = firstNonZeroColumnIndex, row = 0; col < result[0].length;) {

            // if the presumably pivot element is zero, we need to perform Gaussian elimination until it is not
            if (result[row][col] == .0d) {

                // find a non-zero value in this column
                int nonZeroValueRowIndex = -1;
                for (int r = result.length - 1; r >= 0; r--) {
                    if (result[r][col] != .0d) {
                        nonZeroValueRowIndex = r;
                        break;
                    }
                }

                // this column consists of zeroes only, we go one step to the right
                if (nonZeroValueRowIndex < 0) {
                    col++;
                    continue;
                }

                result = swapRows(result, row + 1, nonZeroValueRowIndex + 1);

            }

            // All entries below each pivot must be zeroes. The pivot != 0
            if (result[row][col] != .0d) {
                for (int r = row + 1; r < result.length; r++) {
                    if (result[r][col] != .0d) {
                        result = addMultipliedRow(result, r + 1, row + 1, -1 * (result[r][col] / result[row][col]));
                        MatrixUtil.clean(result);
                    }
                }
            }

            col++;
            row++;

            if (row == result.length) {
                break;
            }
        }

        return result;
    }

    /**
     * Checks if this matrix is in the row echelon form.
     * The matrix is in row echelon form, as it satisfies the next necessary conditions:
     * <ul>
     * <li>The leading entries move progressively to the right.</li>
     * <li>Entries below each leading entry are zero.</li>
     * <li>Zero rows must be at the bottom</li>
     * </ul>
     *
     * @param matrix given
     * @return true if the matrix is in row echelon form, otherwise false
     * @throws MatrixException if the given matrix is malformed
     */
    public static boolean isRowEchelonForm(double[][] matrix) throws MatrixException {
        final int rows = matrix.length;
        final int cols = matrix[0].length;

        // Keeps track of the number of leading zeros in the previous row
        int previousLeadingZeros = -1;

        for (int row = 0; row < rows; row++) {
            int currentLeadingZeros = 0;
            boolean pivotFound = false;

            int col = 0;
            // Count leading zeros in the current row
            for (; col < cols; col++) {
                if (matrix[row][col] == 0) {
                    currentLeadingZeros++;
                } else {
                    pivotFound = true;
                    break;
                }
            }

            // In row echelon form, each pivot must be strictly to the right of the pivot in the row above.
            if (pivotFound && currentLeadingZeros <= previousLeadingZeros) {
                return false;
            }

            // Update the number of leading zeros in the previous row
            previousLeadingZeros = currentLeadingZeros;

        }

        // In row echelon form, all zero rows must be at the bottom.
        int lastZeroRow = Integer.MAX_VALUE;
        for (int row = 0; row < rows; row++) {
            if (isZeroRow(matrix, row))
                lastZeroRow = row;
            else if (row > lastZeroRow)
                return false;
        }

        // In row echelon form, all entries below each pivot must be zeroes.
        int lastPivotRow = -1;
        for (int c = 0; c < cols; c++) {
            boolean pivotFound = false;

            for (int r = lastPivotRow + 1; r < rows; r++) {
                if (matrix[r][c] != 0) {
                    if (pivotFound)
                        return false;
                    else {
                        pivotFound = true;
                        lastPivotRow = r;
                    }
                }
            }

            pivotFound = false;
        }

        return true;
    }

    /**
     * Adds values of one row to another one, previously multiplied by a floating
     * point number.
     *
     * @param matrix              - the original matrix
     * @param rowNumber           - the row number (index + 1) of a row which gets
     *                            summed with rowNumberMultiplied
     * @param rowNumberMultiplied - the row number (index + 1) of a row which gets
     *                            multiplied and then added to row #rowNumber
     * @param multiplicator       - a floating point number to multiply values of row
     *                            #rowNumberMultiplied
     * @return a new matrix which is the original one after the operation
     */
    public static double[][] addMultipliedRow(double[][] matrix, int rowNumber, int rowNumberMultiplied,
            double multiplicator) {
        double[][] result = MatrixUtil.copy(matrix);
        for (int col = 0; col < result[rowNumber - 1].length; col++)
            result[rowNumber - 1][col] = result[rowNumber - 1][col]
                    + result[rowNumberMultiplied - 1][col] * multiplicator;
        return result;
    }

    /**
     * Adds values of one column to another one, previously multiplied by a floating
     * point number
     *
     * @param matrix              - the original matrix
     * @param colNumber           - column number (index + 1) of a column which gets
     *                            summed with colNumberMultiplied
     * @param colNumberMultiplied - col number (index + 1) of a column which gets
     *                            multiplied and then added to column #colNumber
     * @param multiplicator       - floating point number to multiply values of
     *                            column #columnNumberMultiplied
     * @return new matrix which is the original one after the operation
     */
    public static double[][] addMultipliedColumn(double[][] matrix, int colNumber, int colNumberMultiplied,
            double multiplicator) {
        double[][] result = MatrixUtil.copy(matrix);
        for (int row = 0; row < matrix.length; row++) {
            result[row][colNumber - 1] = result[row][colNumber - 1]
                    + result[row][colNumberMultiplied - 1] * multiplicator;
        }
        return result;
    }

    /**
     * Swaps columns of the matrix. Does not change the original matrix object,
     * but returns a new one instead.
     *
     * @param matrix  - original matrix
     * @param colNum1 - column 1 number (index + 1)
     * @param colNum2 - column 2 number (index + 1)
     * @return matrix with switched columns
     */
    public static double[][] swapColumns(double[][] matrix, int colNum1, int colNum2) {
        double[][] result = MatrixUtil.copy(matrix);
        for (int row = 0; row < matrix.length; row++) {
            var temp = result[row][colNum1 - 1];
            result[row][colNum1 - 1] = matrix[row][colNum2 - 1];
            result[row][colNum2 - 1] = temp;
        }
        return result;
    }

    /**
     * Swaps rows of the matrix. Does not change the original matrix object, but
     * returns a new one instead.
     *
     * @param matrix  - original matrix
     * @param rowNum1 - row 1 number (index + 1)
     * @param rowNum2 - row 2 number (index + 1)
     * @return matrix with switched columns
     */
    public static double[][] swapRows(double[][] matrix, int rowNum1, int rowNum2) {
        double[][] result = MatrixUtil.copy(matrix);
        for (int c = 0; c < matrix[0].length; c++) {
            var temp = result[rowNum1 - 1][c];
            result[rowNum1 - 1][c] = matrix[rowNum2 - 1][c];
            result[rowNum2 - 1][c] = temp;
        }
        return result;
    }

    public static String print(double[][] matrix) throws MatrixException {
        double[] columnWidths = getPrintedColumnWidths(matrix);
        StringBuilder sb = new StringBuilder();
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                if (colNum == 0)
                    sb.append("| ");
                sb.append(" ").append(matrix[rowNum][colNum]).append(" ");
                for (int j = 0; j <= columnWidths[colNum] - String.valueOf(matrix[rowNum][colNum]).length(); j++) {
                    sb.append(" ");
                }
                if (colNum == matrix[rowNum].length - 1)
                    sb.append(" |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String print(double[] column) {
        double columnWidth = getPrintedColumnWidths(column);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < column.length; i++) {
            sb.append("| ");
            sb.append(" ").append(column[i]).append(" ");
            for (int j = 0; j <= columnWidth - String.valueOf(column[i]).length(); j++) {
                sb.append(" ");
            }
            sb.append(" |");
            sb.append("\n");
        }
        return sb.toString();
    }

    public static boolean isSquare(double[][] matrix) {
        return !isEmpty(matrix) && matrix.length == matrix[0].length;
    }

    public static void validateSquareMatrix(double[][] matrix) throws MatrixException {
        validateMatrix(matrix);
        if (matrix.length != matrix[0].length) {
            throw new MatrixException("Not a square matrix");
        }
    }

    /**
     * Checks if the given matrix object is valid.
     * 
     * @param matrix given
     * @return true if the matrix is valid, false otherwise.
     */
    public static boolean isValid(double[][] matrix) {
        if (isEmpty(matrix))
            return false;
        int colCount = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != colCount)
                return false;
        }
        return true;
    }

    public static void validateMatrix(double[][] matrix) throws MatrixException {
        if (isEmpty(matrix))
            throw new MatrixException("Not a matrix argument. A matrix must have at least one element");
        int colCount = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != colCount)
                throw new MatrixException("Malformed matrix argument. All rows of the matrix must be of equal length");
        }
    }

    public static boolean isEmpty(double[][] matrix) {
        return matrix.length < 1 || matrix[0].length < 1;
    }

    public static boolean canMultiply(double[][] matrixLeft, double[][] matrixRight) throws MatrixException {
        validateMatrix(matrixLeft);
        validateMatrix(matrixRight);
        return matrixLeft[0].length == matrixRight.length;
    }

    public static boolean isEqualDimensions(double[][] matrix1, double[][] matrix2) throws MatrixException {
        validateMatrix(matrix1);
        validateMatrix(matrix2);
        return matrix1.length == matrix2.length && matrix1[0].length == matrix2[0].length;
    }

    public static double validateCramerAndReturnDeterminant(double[][] matrix) throws MatrixException {
        validateSquareMatrix(matrix);
        double det = det(matrix);
        if (det == .0d)
            throw new MatrixException("The matrix is degenerate, so cannot have an inverted one");
        return det;
    }

    /**
     * Сравнивает между собой две матрицы.
     * 
     * @param a первая матрица
     * @param b вторая матрица
     * @return true если матрицы равны, иначе false
     */
    public static boolean equals(final double[][] a, final double[][] b) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                if (Double.compare(a[i][j], b[i][j]) != 0)
                    return false;
        return true;
    }

    /**
     * Checks the equality of dimensions of the two given valid matrixes.
     * It is up to you to check for the validity of the arguments.
     * 
     * @param a matrix A
     * @param b matrix B
     * @return true if the dimensions are equal, false otherwise
     */
    public static boolean equalDimensions(final double[][] a, double[][] b) {
        return a.length == b.length && a[0].length == b[0].length;
    }

    private static double[] getPrintedColumnWidths(double[][] matrix) throws MatrixException {
        validateMatrix(matrix);
        double[] result = new double[matrix[0].length];
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                int stringLength = String.valueOf(matrix[rowNum][colNum]).length();
                if (stringLength > result[colNum])
                    result[colNum] = stringLength;
            }
        }
        return result;
    }

    private static double getPrintedColumnWidths(double[] matrix) {
        double result = 0.0d;
        for (int i = 0; i < matrix.length; i++) {
            int stringLength = String.valueOf(matrix[i]).length();
            if (stringLength > result)
                result = stringLength;
        }
        return result;
    }

}