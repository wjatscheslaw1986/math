/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.matrix;

import combinatorics.CombinationsGenerator;
import linear.matrix.exception.MatrixException;
import linear.spatial.VectorCalc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;

import static linear.matrix.MatrixUtil.copy;
import static linear.matrix.MatrixUtil.excludeColumnAndRow;
import static linear.matrix.Validation.isEqualDimensions;

/**
 * Matrix calculator util class.
 *
 * @author Viacheslav Mikhailov
 */
public final class MatrixCalc {

    private MatrixCalc() {
        // static context only
    }

    /**
     * The method multiplies the argument matrix on a 64-bit floating point number
     * The method does not modify the original matrix object
     *
     * @param matrix     - the original matrix object
     * @param multiplier - the number to multiply the matrix on
     * @return a new matrix object which is a result of multiplication
     */
    public static double[][] multiply(double[][] matrix, double multiplier) {
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
     */
    public static double[] multiply(double[][] matrix, double[] column) {
        if (matrix[0].length != column.length)
            throw new IllegalArgumentException("If you're about to multiply a matrix from the left on a vector-column from the right, the matrix should have same row length as the vector-column's amount of elements ");
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
    public static double[][] multiply(double[][] matrixLeft, double[][] matrixRight) throws MatrixException {
        if (!Validation.canMultiply(matrixLeft, matrixRight))
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
     */
    public static double[][] transpose(double[][] matrix) {
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++)
            for (int col = 0; col < matrix[0].length; col++)
                result[col][row] = matrix[row][col];
        return result;
    }

    /**
     * The method calculates a cofactor for an element of the given matrix which is
     * situated on the intersection of the given row and column.
     * The method does not modify the original matrix object.
     * BEWARE: rowNum and colNum are not indices but row and
     * column <b>ordinals</b> instead, starting from 1 to <b>matrix.length</b>, inclusive.
     *
     * @param matrix - the original matrix object
     * @param rowNum - the given row number (index + 1) of the element
     * @param colNum - the given column number (index + 1) of the element
     * @return - the cofactor for the element of the matrix which is on the
     * intersection of the given row and column
     */
    public static double cofactor(double[][] matrix, int rowNum, int colNum) {
        return (Math.pow(-1.0f, rowNum + colNum)) * det(excludeColumnAndRow(matrix, rowNum, colNum));
    }

    /**
     * This method calculates a cofactor matrix for the given one.
     * Does not modify the original matrix passed as a parameter.
     *
     * @param matrix the original matrix
     * @return a new matrix object with cofactors instead of the original elements
     */
    public static double[][] cofactors(final double[][] matrix) {
        final double[][] result = new double[matrix.length][matrix[0].length];
        for (int row = 1; row <= matrix.length; row++)
            for (int col = 1; col <= matrix.length; col++)
                result[row - 1][col - 1] = cofactor(matrix, row, col);
        return result;
    }

    /**
     * The method calculates a determinant of the given matrix.
     * Only a square matrix may have a determinant.
     *
     * @param matrix - the given matrix
     * @return the determinant of the matrix
     */
    public static double det(final double[][] matrix) {
        double result = Double.NaN;
        if (matrix.length < 2)
            return matrix[0][0];
        if (matrix.length == 2)
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        if (matrix.length == 3)
            return detSarrus(matrix);
        result = 0;
        for (int col = 0; col < matrix[0].length; col++)
            if (matrix[0][col] != 0)
                result = result + matrix[0][col] * cofactor(matrix, 1, col + 1);
        return result;
    }

    /**
     * This method calculates a determinant of the given 3x3 matrix using Sarrus method.
     *
     * @param matrix given
     * @return the determinant of the matrix
     */
    public static double detSarrus(final double[][] matrix) {
        return matrix[0][0] * matrix[1][1] * matrix[2][2] + matrix[0][1] * matrix[1][2] * matrix[2][0]
                + matrix[1][0] * matrix[2][1] * matrix[0][2] - matrix[0][2] * matrix[1][1] * matrix[2][0]
                - matrix[1][0] * matrix[0][1] * matrix[2][2] - matrix[2][1] * matrix[1][2] * matrix[0][0];
    }

    /**
     * This method calculates a determinant of the given triangular matrix.
     * It is up to you to check if the matrix is valid
     * using {@linkplain linear.matrix.Validation} class.
     *
     * @param matrix given
     * @return the determinant of the matrix
     */
    public static double detTriangular(final double[][] matrix) {
        return IntStream.range(0, matrix.length).mapToObj(i -> matrix[i][i])
                .reduce((a, b) -> a*b).orElse(Double.NaN);
    }

    /**
     * The method calculates a reversed matrix of the provided one as an argument
     * The method does not modify the original matrix object
     *
     * @param matrix - the original matrix object
     * @return a new matrix object which is a reversed matrix passed as an argument
     */
    public static double[][] inverse(final double[][] matrix) {
        var det = det(matrix);
        if (det == 0) throw new IllegalArgumentException("You cannot find a reverse matrix for a degenerate one!");
        return multiply(transpose(cofactors(matrix)), 1.0d / det(matrix));
    }


    /*
     * This method returns a matrix 1 column lesser than the original one, excluding
     * one column.
     *
     * @param matrix - the original matrix
     *
     * @param col - number (index + 1) of a column to exclude
     *
     * @return a matrix one row one column less in size. The column to exclude are
     * provided as an argument
     */
    static double[][] excludeColumn(double[][] matrix, int col) {
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
     * Вырезать квадратную субматрицу (не путать с алгебраическим дополнением!) для данной матрицы.
     * Может быть удобно для оконного алгоритма.
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
     * @throws MatrixException если матрицы не одинаковой размерности
     */
    public static double[][] sum(final double[][] a, final double[][] b) throws MatrixException {
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
        return sum(a, multiply(b, -1.0d));
    }

    /**
     * Calculate rank of a given matrix.
     * Source: {@link https://cp-algorithms.com/linear_algebra/rank-matrix.html}
     *
     * @param originalMatrix the given matrix for finding its rank
     * @return rank
     */
    public static int rank(final double[][] originalMatrix) {
        final double[][] matrix = copy(originalMatrix);

        int rows = matrix.length;
        int cols = matrix[0].length;
        int rank = 0;

        final boolean[] rowSelection = new boolean[rows];

        for (int i = 0; i < cols; ++i) {
            int j;
            for (j = 0; j < rows; ++j) {
                if (!rowSelection[j] && Math.abs(matrix[j][i]) > MatrixUtil.EPS) {
                    break;
                }
            }

            if (j != rows) {
                ++rank;
                rowSelection[j] = true;
                for (int p = i + 1; p < cols; ++p)
                    matrix[j][p] /= matrix[j][i];
                for (int k = 0; k < rows; ++k) {
                    if (k != j && Math.abs(matrix[k][i]) > MatrixUtil.EPS) {
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
     * @deprecated use rank() method instead
     */
    @Deprecated
    public static int rankByMinors(final double[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        int squareSideSize = Math.min(rows, cols);
        int currentMinorSize = 1;

        final Deque<double[][]> minorsToCheckStack = new ArrayDeque<double[][]>();

        while (currentMinorSize <= squareSideSize) {
            final var listOfRowIndicesCombinations = CombinationsGenerator.generate(rows, currentMinorSize);
            final var listOfColumnIndicesCombinations = CombinationsGenerator.generate(cols, currentMinorSize);
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
     * Checks if the two given matrices are equal.
     *
     * @param a matrix A
     * @param b matrix B
     * @return true if matrices are equal, false otherwise
     */
    public static boolean areEqual(final double[][] a, final double[][] b) {
        for (int i = 0; i < a.length; i++)
            if (!VectorCalc.areEqual(a[i], b[i])) return false;
        return true;
    }
}