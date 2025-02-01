/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package linear.matrix;

/**
 * A utility class for matrices.
 * 
 * @author Wjatscheslaw Michailov
 */
public final class MatrixUtil {

    public final static double EPS = 1e-8;

    private MatrixUtil() {
        // static context only
    }

    /**
     * Checks the equality of dimensions of the two given valid matrixes.
     * It is up to you to check for the validity of the arguments.
     * 
     * @param a matrix A
     * @param b matrix B
     * @return true if the dimensions are equal, false otherwise
     */
    public static boolean isEqualDimensions(final double[][] a, final double[][] b) {
        return a.length == b.length && a[0].length == b[0].length;
    }

    /**
     * Checks if this matrix is empty.
     * 
     * @param matrix checked
     * @return true if matrix is empty, false otherwise
     */
    public static boolean isEmpty(double[][] matrix) {
        return matrix.length < 1 || matrix[0].length < 1;
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
     * Modifies the given matrix by assigning 0 value to any value less than 1e-10.
     * 
     * @param matrix given
     */
    public static void eliminateEpsilon(final double[][] matrix) {
        for (int r = 0; r < matrix.length; r++)
            for (int c = 0; c < matrix[0].length; c++)
                if (Math.abs(matrix[r][c]) < EPS)
                    matrix[r][c] = .0d;
    }

    /**
     * Modifies the given vector by assigning 0 value to any value less than EPS.
     * 
     * @param vector given
     */
    public static void eliminateEpsilon(final double[] vector) {
        for (int c = 0; c < vector.length; c++)
            if (Math.abs(.0d - vector[c]) < EPS)
                vector[c] = .0d;
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
    public static void swapValue(final double[][] matrix, final int rowA, final int rowB, final int col) {
        final double tempRow = matrix[rowA][col];
        matrix[rowA][col] = matrix[rowB][col];
        matrix[rowB][col] = tempRow;
    }


    
    /*
     * This method returns a matrix 1 row and 1 column lesser than the original one,
     * by excluding the given row and given the column.
     *
     * @param matrix - the original matrix
     * 
     * @param rowNumber - number (index + 1) of a row to exclude
     * 
     * @param columnNumber - number (index + 1) of a column to exclude
     * 
     * @return a matrix one row one column less in size. The row and column to
     * exclude are provided as two arguments
     */
    static double[][] excludeColumnAndRow(double[][] matrix, int rowNumber, int columnNumber) {
        double[][] submatrix = new double[matrix.length - 1][matrix.length - 1];
        int submatrixRow = 1, submatrixCol = 1;
        for (int rowNum = 1; rowNum <= matrix.length; rowNum++) {
            if (rowNum == rowNumber)
                continue;
            for (int colNum = 1; colNum <= matrix.length; colNum++) {
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
     * Produce textual representation of the given matrix.
     * 
     * @param matrix given
     * @return a string representation of the given matrix
     */
    public static String print(double[][] matrix) {
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

    /**
     * Produce textual representation of the given vector.
     * 
     * @param vector given
     * @return a string representation of the given vector
     * @deprecated use method for matrices instead
     */
    @Deprecated(forRemoval = true)
    public static String print(double[] vector) {
        double columnWidth = getPrintedColumnWidths(vector);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vector.length; i++) {
            sb.append("| ");
            sb.append(" ").append(vector[i]).append(" ");
            for (int j = 0; j <= columnWidth - String.valueOf(vector[i]).length(); j++) {
                sb.append(" ");
            }
            sb.append(" |");
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
