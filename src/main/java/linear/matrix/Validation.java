package linear.matrix;

import static linear.matrix.MatrixUtil.isEmpty;

import linear.matrix.exception.MatrixException;

/**
 * 
 */
public final class Validation {
    
    private Validation() {
        // static context only
    }
    
    /**
     * Checks if the given matrix is valid.
     * 
     * @param matrix given
     * @return true if the matrix is valid, false otherwise.
     */
    public static boolean isValid(final double[][] matrix) {
        if (isEmpty(matrix))
            return false;
        final int cols = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != cols)
                return false;
        }
        return true;
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
    
    public static void validateSquareMatrix(double[][] matrix) throws MatrixException {
        validateMatrix(matrix);
        if (matrix.length != matrix[0].length) {
            throw new MatrixException("Not a square matrix");
        }
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
}
