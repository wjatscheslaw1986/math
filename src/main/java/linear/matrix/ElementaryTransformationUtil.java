package linear.matrix;

import static linear.matrix.MatrixUtil.copy;

/**
 * @author Viacheslav Mikhailov
 */
public final class ElementaryTransformationUtil {

    private ElementaryTransformationUtil() {
        // static context only
    }

    /**
     * Swaps rows of the matrix. Does not change the original matrix object, but
     * returns a new one instead.
     *
     * @param matrix  - original matrix
     * @param row1 - row 1 index
     * @param row2 - row 2 index
     * @return matrix with switched columns
     */
    public static double[][] swapRows(final double[][] matrix, final int row1, final int row2) {
        final double[][] result = MatrixUtil.copy(matrix);
        for (int c = 0; c < matrix[0].length; c++) {
            var temp = result[row1][c];
            result[row1][c] = matrix[row2][c];
            result[row2][c] = temp;
        }
        return result;
    }

    /**
     * Swaps columns of the matrix. Does not change the original matrix object,
     * but returns a new one instead.
     *
     * @param matrix  - original matrix
     * @param col1 - column 1 index
     * @param col2 - column 2 index
     * @return matrix with switched columns
     */
    public static double[][] swapColumns(final double[][] matrix, final int col1, final int col2) {
        final double[][] result = MatrixUtil.copy(matrix);
        for (int row = 0; row < matrix.length; row++) {
            var temp = result[row][col1];
            result[row][col1] = matrix[row][col2];
            result[row][col2] = temp;
        }
        return result;
    }

    /**
     * Adds values of one row to another one, previously multiplied by a floating
     * point number.
     *
     * @param matrix              the original matrix
     * @param rowNumber           the row number (index + 1) of a row which gets
     *                            summed with <i>rowNumberMultiplied</i>
     * @param rowNumberMultiplied the row number (index + 1) of a row which gets
     *                            multiplied and then added to row <i>rowNumber</i>
     * @param multiplicator       a floating point number to multiply values of row
     *                            <i>rowNumberMultiplied</i>
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
     * TODO tests!
     * Adds values of one column to another one, previously multiplied by a floating
     * point number.
     *
     * @param matrix              the original matrix
     * @param colNumber           column number (index + 1) of a column which gets
     *                            summed with <i>colNumberMultiplied</i>
     * @param colNumberMultiplied column number (index + 1) of a column which gets
     *                            multiplied and then added to column #colNumber
     * @param multiplicator       floating point number to multiply values of
     *                            column <i>columnNumberMultiplied</i>
     * @return new matrix which is the original one after the operation
     */
    public static double[][] addMultipliedColumn(double[][] matrix, int colNumber, int colNumberMultiplied,
                                                 double multiplicator) {
        double[][] result = copy(matrix);
        for (int row = 0; row < matrix.length; row++) {
            result[row][colNumber - 1] = result[row][colNumber - 1]
                    + result[row][colNumberMultiplied - 1] * multiplicator;
        }
        return result;
    }
}
