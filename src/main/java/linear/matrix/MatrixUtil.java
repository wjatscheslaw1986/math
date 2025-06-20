/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.matrix;

import approximation.RoundingUtil;

/**
 * A utility class for matrices.
 *
 * @author Viacheslav Mikhailov
 */
public final class MatrixUtil {

    public final static double EPS = 1e-8;

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
            final double[] row = new double[matrix[r].length];
            System.arraycopy(matrix[r], 0, row, 0, matrix[r].length);
            copy[r] = row;
        }
        return copy;
    }

    /**
     * Modifies the given matrix by approximating any value that is less
     * than 1e-8 to the closest integer value.
     *
     * @param matrix given
     */
    public static void eliminateEpsilon(final double[]... matrix) {
        for (int r = 0; r < matrix.length; r++)
            for (int c = 0; c < matrix[0].length; c++) {
                var abs = Math.abs(matrix[r][c]);
                var absRound = Math.round(abs);
                if (Math.abs(abs - absRound) < EPS) {
                    matrix[r][c] = Math.round(matrix[r][c]);
                }
            }
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
            result = removeMarginalColumn(result, true);
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
     * Checks if all elements in the given column of the given matrix are zeroes.
     *
     * @param matrix given
     * @param col    index
     * @return true if all elements in the given column are zeroes, false otherwise
     */
    public static boolean isZeroCol(final double[][] matrix, int col) {
        for (double[] row : matrix)
            if (row[col] != .0d)
                return false;
        return true;
    }

    /**
     * Remove a column either from the left or from the right side of this matrix.
     * <p>This method is safe i.e. does not modify the given {@code matrix} argument.</p>
     *
     * @param matrix given
     * @param left   true if the left column to be removed, false if the right column should be removed
     * @return the modified matrix
     */
    public static double[][] removeMarginalColumn(final double[][] matrix, final boolean left) {
        if (matrix[0].length < 2) {
            return new double[][]{{}};
        }
        final double[][] copy = new double[matrix.length][matrix[0].length - 1];
        for (int r = 0; r < matrix.length; r++) {
            final double[] row = new double[matrix[r].length - 1];
            System.arraycopy(matrix[r], left ? 1 : 0, row, 0, matrix[r].length - 1);
            copy[r] = row;
        }
        return copy;
    }

    /**
     * Create a copy of the given matrix, but exclude rows where all elements are zeroes.
     *
     * @param matrix given
     * @return the copy of the given matrix, but without all-zeroes rows
     */
    public static double[][] removeAllZeroesRows(final double[][] matrix) {
        double[][] result = copy(matrix);
        int rowNum = 1;
        while (rowNum <= result.length) {
            if (isZeroRow(result, rowNum - 1)) {
                result = removeNthRow(result, rowNum);
                rowNum = 1;
            } else {
                rowNum++;
            }
        }
        return result;
    }

    /**
     * Remove Nth row from this matrix.
     * <p>This method does not modify the given matrix.</p>
     *
     * @param n the ordinal of the row, counting downwards
     * @param matrix given
     * @return the modified matrix
     */
    public static double[][] removeNthRow(final double[][] matrix, final int n) {
        if (matrix.length < n) {
            throw new ArrayIndexOutOfBoundsException(String.format("No %sth row in the given matrix", n));
        }
        if (matrix.length < 2) {
            return new double[][]{{}};
        }
        final double[][] copy = new double[matrix.length - 1][matrix[0].length];
        for (int i1 = 1, i2 = 1; i1 <= matrix.length; i1++) {
            if (i1 == n) continue;
            final double[] row = new double[matrix[i1 - 1].length];
            System.arraycopy(matrix[i1 - 1], 0, row, 0, matrix[i1 - 1].length);
            copy[i2 - 1] = row;
            i2++;
        }
        return copy;
    }

    /**
     * This method returns a matrix 1 column lesser than the original one, excluding
     * one column.
     *
     * @param matrix - the original matrix
     *
     * @param columnNumber - number (index + 1) of a column to exclude
     *
     * @return a matrix one row one column less in size. The column to exclude are
     * provided as an argument
     */
    static double[][] removeNthColumn(double[][] matrix, int columnNumber) {
        double[][] submatrix = new double[matrix.length][matrix[0].length - 1];
        int submatrixRow = 1, submatrixCol = 1;
        for (int rowNum = 1; rowNum <= matrix.length; rowNum++) {
            for (int colNum = 1; colNum <= matrix[0].length; colNum++) {
                if (colNum == columnNumber)
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
     * Swap two values in two different rows but in the same column in the given matrix.
     *
     * @param matrix the given matrix
     * @param rowA   first swapper row index
     * @param rowB   second swapper row index
     * @param col    the column index of the swapped value which stays the same after the swap
     */
    public static void swapInColumn(final double[][] matrix, final int rowA, final int rowB, final int col) {
        final double tempRow = matrix[rowA][col];
        matrix[rowA][col] = matrix[rowB][col];
        matrix[rowB][col] = tempRow;
    }

    /**
     * This method returns a matrix 1 row and 1 column lesser than the original one.
     * The ordinals of the row and the column to exclude are provided as <i>rowNumber</i>
     * and <i>columnNumber</i>, respectively.
     *
     * @param matrix       - the original matrix
     * @param rowNumber    - number (index + 1) of a row to exclude
     * @param columnNumber - number (index + 1) of a column to exclude
     * @return a matrix one row one column less in size.
     */
    public static double[][] excludeColumnAndRow(double[][] matrix, int rowNumber, int columnNumber) {
        double[][] submatrix = new double[matrix.length - 1][matrix[0].length - 1];
        int submatrixRow = 1, submatrixCol = 1;
        for (int rowNum = 1; rowNum <= matrix.length; rowNum++) {
            if (rowNum == rowNumber)
                continue;
            for (int colNum = 1; colNum <= matrix[0].length; colNum++) {
                if (colNum == columnNumber)
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
     * Returns the column by its index of the given matrix, counting from left to right.
     *
     * @param matrix        the given matrix
     * @param column  index of the column to return
     * @return the column as an array
     */
    public static double[] getColumn(double[][] matrix, int column) {
        if (matrix.length < 1) throw new IllegalArgumentException("The given matrix shouldn't be empty.");
        if (matrix[0].length <= column) throw new ArrayIndexOutOfBoundsException("No column of index " + column
                + " could be found in the " + matrix.length + "x" + matrix[0].length + " matrix.");
        var resultColumn = new double[matrix[0].length];
        int i = 0;
        for (var row : matrix) {
            resultColumn[i++] = row[column];
        }
        return resultColumn;
    }

    /**
     * This method substitutes one column of a given matrix with a given vector,
     * at a certain position 'index'.
     *
     * @param matrix  the original matrix
     * @param column  the given vector
     * @param index   column index
     * @return        new matrix of same size but with one of its columns substituted by <i>column</i>
     */
    public static double[][] substituteColumn(final double[][] matrix, final double[] column, final int index) {
        if (matrix[0].length <= index)
            throw new ArrayIndexOutOfBoundsException(String
                    .format("Column index %d is out of matrix columns size of %d", index, matrix[0].length));
        final double[][] result = MatrixUtil.copy(matrix);
        for (int row = 0; row < matrix.length; row++)
            result[row][index] = column[row];
        return result;
    }

    /**
     * Round values in the given matrix or vector up to 12 places after the dot.
     * This method <b>does modify</b> the argument, so be careful.
     *
     * @param decimalPlaces decimal places right to the dot to round up to
     * @param matrix the given matrix
     */
    public static void roundValues(int decimalPlaces, double[]... matrix) {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                matrix[r][c] = RoundingUtil.roundToNDecimals(matrix[r][c], decimalPlaces);
            }
        }
    }

    /**
     * Produce textual representation of the given matrix.
     *
     * @param matrix given
     * @return a string representation of the given matrix
     */
    public static String print(double[]... matrix) {
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

    private static double[] getPrintedColumnWidths(double[][] matrix) {
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
