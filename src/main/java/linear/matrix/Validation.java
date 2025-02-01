package linear.matrix;

import static linear.matrix.MatrixUtil.isEmpty;

/**
 * A utility class that provides checks for different matrix properties.
 *
 * @author Wjatscheslaw Michailov
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
     * Checks if the given two matrices are of equal dimensions.
     *
     * @param matrix1 given
     * @param matrix2 given
     * @return true if both matrices are of same dimensions, false otherwise
     */
    public static boolean isEqualDimensions(double[][] matrix1, double[][] matrix2) {
        return isMatrix(matrix1) && isMatrix(matrix2)
                && (matrix1.length == matrix2.length && matrix1[0].length == matrix2[0].length);
    }

    /**
     * Checks if this matrix has the same length for its columns as it has for its rows.
     *
     * @param matrix given
     * @return true if the given matrix is a square matrix, false otherwise
     */
    public static boolean isSquareMatrix(double[][] matrix) {
        return isMatrix(matrix) && (matrix.length == matrix[0].length);
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
        final int cols = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != cols)
                return false;
        }
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
        return isSquareMatrix(matrix) && MatrixCalc.det(matrix) != .0d;
    }
}
