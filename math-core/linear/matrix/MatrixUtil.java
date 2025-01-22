/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package linear.matrix;

/**
 * A utility class for matrices.
 * 
 * @author vaclav
 */
public final class MatrixUtil {

    private MatrixUtil() {
        // static context only
    }

    /**
     * Copy the given matix. The given matrix must have the same amount of columns for each row.
     * 
     * @return the copy of the matrix passed as an argument
     */
    public static double[][] copy(final double[][] matrix) {
        final double[][] copy = new double[matrix.length][matrix[0].length];
        for (int r = 0; r < matrix.length; r++) {
            final double row[] = new double[matrix[r].length];
            System.arraycopy(matrix[r], 0, row, 0, matrix[r].length);
            copy[r] = row;
        }
        return copy;

    }

}
