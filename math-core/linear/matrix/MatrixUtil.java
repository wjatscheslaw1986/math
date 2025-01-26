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

    public final static double EPS = 1e-10;

    private MatrixUtil() {
        // static context only
    }

    /**
     * Copy the given matrix. The given matrix must have the same amount of columns for each row.
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

    /**
     * Cleans the given matrix from double precision limitation noise.
     * 
     * @param matrix given
     */
    public static void clean(final double[][] matrix) {
        for (int r = 0; r < matrix.length; r++)
            for (int c = 0; c < matrix[0].length; c++)
                if (matrix[r][c] < EPS)
                    matrix[r][c] = .0d;
    }

    /**
     * Shrinks the given matrix from left to right, throwing away the all-zero columns.
     * 
     * @param matrix given
     * @return result the new matrix object with the modification applied
     */
    public static double[][] shrinkZeroColumns(final double[][] matrix) {
        boolean modified = false;
        double[][] result = matrix;
        while (isZeroCol(result, 0)) {
            if (!modified) {
                modified = true;
                result = copy(matrix);
            }
            result = removeLeftmostColumn(result);
        }
        return result;
    }

    /**
     * Checks if all elements in the given row in the given matrix are zeroes.
     * 
     * @param matrix given
     * @param row    index
     * @return true if all elements in the given row are zeroes, false otherwise
     */
    public static boolean isZeroRow(final double[][] matrix, int row) {
        for (int i = 0; i < matrix[row].length; i++)
            if (matrix[row][i] != .0d)
                return false;
        return true;
    }

    /**
     * Checks if all elements in the given column in the given matrix are zeroes.
     * 
     * @param matrix given
     * @param col    index
     * @return true if all elements in the given column are zeroes, false otherwise
     */
    public static boolean isZeroCol(final double[][] matrix, int col) {
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i][col] != .0d)
                return false;
        return true;
    }

    /**
     * Remove column on the left from this matrix.
     * 
     * @param matrix given
     * @return the modified matrix
     */
    public static double[][] removeLeftmostColumn(final double[][] matrix) {
        if (matrix[0].length < 2) {
            return new double[][]{{}};
        }
        final double[][] copy = new double[matrix.length][matrix[0].length - 1];
        for (int r = 0; r < matrix.length; r++) {
            final double row[] = new double[matrix[r].length];
            System.arraycopy(matrix[r], 1, row, 0, matrix[r].length - 1);
            copy[r] = row;
        }
        return copy;
    }

    /**
     * Remove row on the bottom from this matrix.
     * 
     * @param matrix given
     * @return the modified matrix
     */
    public static double[][] removeBottomRow(final double[][] matrix) {
        if (matrix.length < 2) {
            return new double[][]{{}};
        }
        final double[][] copy = new double[matrix.length - 1][matrix[0].length];
        for (int r = 0; r < matrix.length - 1; r++) {
            final double row[] = new double[matrix[r].length];
            System.arraycopy(matrix[r], 0, row, 0, matrix[r].length);
            copy[r] = row;
        }
        return copy;
    }

    /**
     * Swap two values in two different rows but in the same column in the given matrix.
     * 
     * @param matrix the given matrix
     * @param rowA   first swapper row index
     * @param rowB   second swapper row index
     * @param col    the column index of the swapped value which stays the same after the swap
     */
    public static void swap(final double[][] matrix, final int rowA, final int rowB, final int col) {
        final double tempRow = matrix[rowA][col];
        matrix[rowA][col] = matrix[rowB][col];
        matrix[rowB][col] = tempRow;
    }
}
