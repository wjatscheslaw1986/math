/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.matrix;

import algebra.Term;
import linear.matrix.exception.MatrixException;

import static linear.matrix.MatrixCalc.*;

/**
 * A utility class that provides checks for different matrix properties.
 *
 * @author Viacheslav Mikhailov
 */
public final class Validation {

    private Validation() {
        // static context only
    }

    /**
     * Checks if the given two matrices can be multiplied in the provided order of parameters.
     *
     * @param matrixLeft  given
     * @param matrixRight given
     * @return true if the matrix on the left has as many columns as the matrix on the right has rows, false otherwise
     */
    public static boolean canMultiply(double[][] matrixLeft, double[]... matrixRight) {
        return isMatrix(matrixLeft) && isMatrix(matrixRight)
                && matrixLeft[0].length == matrixRight.length;
    }

    /**
     * Checks if the given two matrices can be multiplied in the provided order of parameters.
     *
     * @param matrixLeft  given
     * @param matrixRight given
     * @return true if the matrix on the left has as many columns as the matrix on the right has rows, false otherwise
     */
    public static boolean canMultiply(Term[][] matrixLeft, Term[]... matrixRight) {
        return isMatrix(matrixLeft) && isMatrix(matrixRight)
                && matrixLeft[0].length == matrixRight.length;
    }

    /**
     * Checks if the given two matrices are of equal dimensions.
     *
     * @param matrix1 given
     * @param matrix2 given
     * @return true if both matrices are of same dimensions, false otherwise
     */
    public static boolean isEqualDimensions(double[][] matrix1, double[][] matrix2) {
        if (!(isMatrix(matrix1) && isMatrix(matrix2)))
            throw new IllegalArgumentException("Not a matrix");
        return matrix1.length == matrix2.length && matrix1[0].length == matrix2[0].length;
    }

    /**
     * Checks if this matrix has the same length for its columns as it has for its rows.
     *
     * @param matrix given
     * @return true if the given matrix is a square matrix, false otherwise
     */
    public static boolean isSquareMatrix(double[][] matrix) {
        if (!isMatrix(matrix))
            throw new IllegalArgumentException("Empty matrices may not be checked for being a square matrix.");
        return matrix.length == matrix[0].length;
    }

    /**
     * Checks if the given double array is a matrix. We consider a matrix
     * to have the same length for all of its columns.
     *
     * @param matrix an array of arrays of equal length
     * @return true if the given array of arrays is a matrix, false otherwise
     */
    public static boolean isMatrix(double[][] matrix) {
        if (isEmpty(matrix)) {
            return false;
        }
        final int length = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != length)
                return false;
        }
        return true;
    }

    /**
     * Checks if the given double array is a matrix. We consider a matrix
     * to have the same length for all of its columns.
     *
     * @param matrix an array of arrays of equal length
     * @return true if the given array of arrays is a matrix, false otherwise
     */
    public static boolean isMatrix(Term[][] matrix) {
        if (isEmpty(matrix)) {
            return false;
        }
        final int length = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != length)
                return false;
        }
        return true;
    }

    /**
     * Checks if this matrix is empty.
     *
     * @param matrix checked
     * @return true if matrix is empty, false otherwise
     */
    public static boolean isEmpty(double[][] matrix) {
        if (matrix.length < 1) return true;
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i].length > 0) return false;
        return true;
    }

    /**
     * Checks if this matrix is empty.
     *
     * @param matrix checked
     * @return true if matrix is empty, false otherwise
     */
    public static boolean isEmpty(Term[][] matrix) {
        if (matrix.length < 1) return true;
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i].length > 0) return false;
        return true;
    }

    /**
     * Checks if the given matrix of linear equations system's coefficients
     * as a square non-degenerate matrix. If it is, then the equations system
     * can be solved using Cramer method.
     *
     * @param matrix the given matrix
     * @return true if the given matrix is a Cramer matrix, false otherwise
     */
    public static boolean isCramer(final double[][] matrix) {
        return isSquareMatrix(matrix) && !isDegenerate(matrix);
    }

    /**
     * Checks if the given matrix is a degenerate one.
     *
     * @param matrix the given matrix
     * @return true if the given matrix is degenerate, false otherwise
     */
    public static boolean isDegenerate(final double[][] matrix) {
        return Math.abs(MatrixCalc.det(matrix)) < 1e-10d;
    }

    /**
     * Checks if the given matrix is invertible.
     * <p>
     *     A matrix is invertible only in case it is a square one, a not degenerate one (i.e. has a full rank).
     * </p>
     *
     * @param matrix the given matrix
     * @return true if the given matrix is degenerate, false otherwise
     * @deprecated use LU decomposition based isInvertible() method instead
     */
    @Deprecated
    public static boolean isInvertibleByDeterminant(final double[][] matrix) {
        return isSquareMatrix(matrix) && Math.abs(MatrixCalc.det(matrix)) > MatrixUtil.EPS;
    }

    /**
     * Checks if the given matrix is invertible.
     * <p>
     *     A matrix is invertible only in case
     *     it is not degenerate (i.e. all pivots of the LU decomposition after partial pivoting are non-zero).
     * </p>
     *
     * @param matrix the given matrix
     * @return true if the given matrix is degenerate, false otherwise
     */
    public static boolean isInvertible(final double[][] matrix) {
        if (!isSquareMatrix(matrix)) {
            return false;
        }

        // Make a copy so we don't modify the original
        double[][] a = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, a[i], 0, matrix.length);
        }

        // Perform LU decomposition with partial pivoting
        for (int col = 0; col < matrix.length; col++) {
            // Pivot: find row with max absolute value in column k
            int pivotRow = col;
            double max = Math.abs(a[col][col]);
            for (int i = col + 1; i < matrix.length; i++) {
                double val = Math.abs(a[i][col]);
                if (val > max) {
                    max = val;
                    pivotRow = i;
                }
            }

            // If pivot is tiny, matrix is singular
            if (max < MatrixUtil.EPS) {
                return false;
            }

            // Swap rows if needed
            if (pivotRow != col) {
                double[] temp = a[col];
                a[col] = a[pivotRow];
                a[pivotRow] = temp;
            }

            gaussianEliminationForColumn(a, col);
        }

        return true; // All pivots were > EPSILON, so matrix is invertible
    }

    static void gaussianEliminationForColumn(double[][] a, int col) {
        for (int row = col + 1; row < a.length; row++) {
            a[row][col] /= a[col][col];
            for (int col_ = col + 1; col_ < a.length; col_++) {
                a[row][col_] -= a[row][col] * a[col][col_];
            }
        }
    }

    /**
     * Checks if the given two matrices {@code matrixA} and {@code matrixB} are similar
     * against the given transformation matrix {@code matrixC}.
     * <p>
     * Note: linear transformation matricies are similar across different bases.
     *
     * @param matrixA the given matrix
     * @param matrixB the given matrix
     * @param matrixC the given matrix
     * @return true if the given two matrices {@code matrixA} and {@code matrixB} are similar, false otherwise
     */
    public static boolean areSimilar(final double[][] matrixA, final double[][] matrixB, final double[][] matrixC)
            throws MatrixException {
        if (isDegenerate(matrixC))
            throw new IllegalArgumentException("The given transformation matrix is degenerate.");
        return areEqual(matrixA, multiply(multiply(matrixC, matrixB), inverse(matrixC)));
    }
}
