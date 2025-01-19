/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.matrix;

import linear.matrix.exception.MatrixException;

import java.util.LinkedList;
import java.util.List;

public class DoubleMatrixCalc {

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
			throw new MatrixException(
					"If you're about to multiply a matrix from the left on a vector-column from the right, the matrix should have same row length as the vector-column's amount of elements ");
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
			throw new MatrixException(
					"In order to multiply matrices, the one on the left should have same row length as the column length of the one on the right");
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
	 * The method calculates a determinant of the matrix passed as an argument
	 *
	 * @param matrix - the original matrix object
	 * @return the determinant of the matrix
	 * @throws MatrixException - if the matrix is malformed
	 */
	public static double det(double[][] matrix) throws MatrixException {
		if (matrix.length > 1)
			validateSquareMatrix(matrix);
		else
			return matrix[0][0];
		double result = Double.MAX_VALUE;
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
//        System.out.println(
//                "DET: " + matrix[0][0] * matrix[1][1] * matrix[2][2] + " + " +
//                matrix[0][1] * matrix[1][2] * matrix[2][0] + " + " +
//                matrix[1][0] * matrix[2][1] * matrix[0][2] + " - " +
//                matrix[0][2] * matrix[1][1] * matrix[2][0]  + " - " +
//                matrix[1][0] * matrix[0][1] * matrix[2][2] + " - " +
//                matrix[2][1] * matrix[1][2] * matrix[0][0]);
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
	 * one column
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
	 * This method substitutes one column of an original matrix (the one in the
	 * argument) with another one, at a certain position 'colNum'
	 *
	 * @param matrix - the original matrix
	 * @param column - an array of same column values, from top to bottom
	 * @param colNum - column number (index + 1) of the original matrix to
	 *               substitute with 'column'
	 * @return new matrix of same size but with one of its columns substituted by
	 *         'column'
	 */
	public static double[][] substituteColumn(double[][] matrix, double[] column, int colNum) {
		double[][] result = new double[matrix.length][matrix[0].length];
		for (int r = 0; r < matrix.length; r++)
			System.arraycopy(matrix[r], 0, result[r], 0, matrix[0].length);
		for (int row = 0; row < matrix.length; row++)
			for (int col = 0; col < matrix[0].length; col++)
				if (col == colNum - 1)
					result[row][col] = column[row];
		return result;
	}

	public static double[][] squareSubmatrix(double[][] matrix, int rowNum, int colNum, int squareSideSize)
			throws MatrixException {
		double[][] result = new double[squareSideSize][squareSideSize];
		int resultRow = 0, resultCol = 0;
		for (int row = rowNum - 1; row < rowNum + squareSideSize - 1; row++) {
			for (int col = colNum - 1; col < colNum + squareSideSize - 1; col++) {
				try {
					result[resultRow][resultCol] = matrix[row][col];
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {
					System.err.print("=============ERROR====================\n");
					System.err.print("\nresultRow=" + resultRow + "\n");
					System.err.print("resultCol=" + resultCol + "\n");
					System.err.print("squareSideSize=" + squareSideSize + "\n");
					System.err.print("row=" + row + "\n");
					System.err.print("col=" + col + "\n");
					System.err.print("rowNum=" + rowNum + "\n");
					System.err.print("colNum=" + colNum + "\n");
					System.err.print("MATRIX:\n");
					System.err.print(print(matrix));
					System.err.print("RESULT:\n");
					System.err.print(print(result));
					System.err.print("\n=============/ERROR====================\n");
					return result;
				}
				resultCol++;
			}
			resultRow++;
			resultCol = 0;
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
     * @param matrix is the given matrix for finding its rank
     * @return rank
     * @throws MatrixException if given matrix is malformed
     */
	public static int rank(double[][] matrix) throws MatrixException {
	    validateMatrix(matrix);
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
	        
	        if(j != rows) {
	            ++rank;
	            rowSelection[j] = true;
	            for (int p = i + 1; p < cols; ++p)
	                matrix[j][p] /= matrix[j][i];
	            for (int k = 0; k < rows; ++k) {
	                if (k != j && Math.abs(matrix[k][i]) > eps ) {
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
	 * Calculate rank of a given matrix.
	 * My own attempt.
	 * 
	 * @param matrix is the given matrix for finding its rank
	 * @return rank
	 * @throws MatrixException if given matrix is malformed
	 */
	@Deprecated
	public static int rankOld(double[][] matrix) throws MatrixException {
	    
	    // Trying LU decomposition first
	    
		double[][] trapezoid = tryTrapezoid(matrix);
		if (trapezoid != null)
			return trapezoid.length;
		
		// Fallback to determinant method
		
		int rows = matrix.length, cols = matrix[0].length;
		int squareSideSize = Math.min(rows, cols);
		// System.out.println("square size : " + squareSideSize);
		for (int rank = squareSideSize; rank > 1; rank--) {
			// System.out.println("rank : " + rank);
			for (int row = 0; row <= matrix.length - squareSideSize; row++) {
				// System.out.println("row : " + row);
				for (int col = 0; col <= matrix[row].length - squareSideSize; col++) {
					// System.out.println("col : " + col);
					double[][] temp = squareSubmatrix(matrix, row + 1, col + 1, rank);
					// System.out.println(print(temp));
					if (det(temp) != .0d)
						return rank;
				}
			}
		}
		for (int row = 0; row < matrix.length; row++)
			for (int col = 0; col < matrix[row].length; col++)
				if (matrix[row][col] != 0)
					return 1;

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
	public static boolean isTrapezoid(double[][] matrix) {
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
	 * This method produces a trapezoid matrix of any matrix or simply returns null. 
	 * This method does not modify the original matrix object
	 *
	 * @param matrix - a link to original matrix argument object
	 * @return a trapezoid form of a given <i>matrix</i> argument or null if the method failed to form
	 *         a trapezoid matrix
	 * @throws MatrixException in case of a malformed matrix
	 */
	public static double[][] tryTrapezoid(double[][] matrix) throws MatrixException {
		double[][] result = new double[matrix.length][matrix[0].length];
		for (int r = 0; r < matrix.length; r++)
			System.arraycopy(matrix[r], 0, result[r], 0, matrix.length);
		int shortestSideLength = Math.min(result.length, result[0].length);
		for (int i = 0; i < shortestSideLength; i++) {
			int maxSwitchRowNumber = result.length - 1, maxSwitchColNumber = result[0].length - 1;
			while (maxSwitchRowNumber + maxSwitchColNumber > 0 && result[i][i] == .0d) {
				if (maxSwitchRowNumber > 0)
					result = switchRows(result, i + 1, (maxSwitchRowNumber--) + 1);
				else if (maxSwitchColNumber > 0)
					result = switchColumns(result, i + 1, (maxSwitchColNumber--) + 1);
			}
			if (result[i][i] == .0d)
				return matrix; // current matrix may not have a trapezoid form. Return the original one
			for (int j = i + 1; j < result.length; j++)
				if (result[j][i] != .0d)
					result = addMultipliedRow(result, j + 1, i + 1, (-1) * result[j][i] / result[i][i]);
			result = truncateZeroRows(result);
			shortestSideLength = Math.min(result.length, result[0].length);
		}
		if (isTrapezoid(result))
			return result;
		return null;
	}

	/**
	 * Adds values of one row to another one, previously multiplied by a floating
	 * point number
	 *
	 * @param matrix              - the original matrix
	 * @param rowNumber           - row number (index + 1) of a row which gets
	 *                            summed with rowNumberMultiplied
	 * @param rowNumberMultiplied - row number (index + 1) of a row which gets
	 *                            multiplied and then added to row #rowNumber
	 * @param multiplicator       - floating point number to multiply values of row
	 *                            #rowNumberMultiplied
	 * @return new matrix which is the original one after the operation
	 */
	public static double[][] addMultipliedRow(double[][] matrix, int rowNumber, int rowNumberMultiplied,
			double multiplicator) {
		double[][] result = new double[matrix.length][matrix[0].length];
		for (int r = 0; r < matrix.length; r++)
			System.arraycopy(matrix[r], 0, result[r], 0, matrix.length);
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
		double[][] result = new double[matrix.length][matrix[0].length];
		for (int r = 0; r < matrix.length; r++) {
			System.arraycopy(matrix[r], 0, result[r], 0, matrix.length);
			result[r][colNumber - 1] = result[r][colNumber - 1] + result[r][colNumberMultiplied - 1] * multiplicator;
		}
		return result;
	}

	/**
	 * Switches columns of the matrix. Does not change the original matrix object,
	 * but returns a new one instead
	 *
	 * @param matrix  - original matrix
	 * @param colNum1 - column 1 number (index + 1)
	 * @param colNum2 - column 2 number (index + 1)
	 * @return matrix with switched columns
	 */
	public static double[][] switchColumns(double[][] matrix, int colNum1, int colNum2) throws MatrixException {
		validateMatrix(matrix);
		double[][] result = new double[matrix.length][matrix[0].length];
		for (int r = 0; r < matrix.length; r++) {
			System.arraycopy(matrix[r], 0, result[r], 0, matrix[0].length);
			result[r][colNum1 - 1] = matrix[r][colNum2 - 1];
			result[r][colNum2 - 1] = matrix[r][colNum1 - 1];
		}
		return result;
	}

	/**
	 * Switches rows of the matrix. Does not change the original matrix object, but
	 * returns a new one instead
	 *
	 * @param matrix  - original matrix
	 * @param rowNum1 - row 1 number (index + 1)
	 * @param rowNum2 - row 2 number (index + 1)
	 * @return matrix with switched columns
	 */
	public static double[][] switchRows(double[][] matrix, int rowNum1, int rowNum2) throws MatrixException {
		validateMatrix(matrix);
		double[][] result = new double[matrix.length][matrix[0].length];
		for (int r = 0; r < matrix.length; r++)
			System.arraycopy(matrix[r], 0, result[r], 0, matrix[0].length);
		for (int c = 0; c < matrix[0].length; c++) {
			try {
				result[rowNum1 - 1][c] = matrix[rowNum2 - 1][c];
				result[rowNum2 - 1][c] = matrix[rowNum1 - 1][c];
			} catch (ArrayIndexOutOfBoundsException ae) {
				System.err.println("here");
			}
		}
		return result;
	}

	public static double[][] truncateZeroColumns(double[][] matrix) {
		List<Integer> nonZeroColumnIndices = new LinkedList<>();
		Outer: for (int c = 0; c < matrix[0].length; c++) {
			for (int r = 0; r < matrix.length; r++) {
				if (matrix[r][c] != .0d) {
					nonZeroColumnIndices.add(c);
					continue Outer;
				}
			}
		}
		double[][] result = new double[matrix.length][nonZeroColumnIndices.size()];
		int colNum = 0;
		for (Integer col : nonZeroColumnIndices) {
			for (int r = 0; r < matrix.length; r++)
				result[r][colNum] = matrix[r][col];
			colNum++;
		}
		return result;
	}

	public static double[][] truncateZeroRows(double[][] matrix) {
		List<Integer> nonZeroRowIndices = new LinkedList<>();
		Outer: for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[0].length; c++) {
				if (matrix[r][c] != .0d) {
					nonZeroRowIndices.add(r);
					continue Outer;
				}
			}
		}
		double[][] result = new double[nonZeroRowIndices.size()][matrix[0].length];
		int rowNum = 0;
		for (Integer row : nonZeroRowIndices) {
			System.arraycopy(matrix[row], 0, result[rowNum], 0, matrix[0].length);
			rowNum++;
		}
		return result;
	}

	public static String print(double[][] matrix) throws MatrixException {
		double[] columnWiths = getPrintedColumnWiths(matrix);
		StringBuilder sb = new StringBuilder();
		for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
			for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
				if (colNum == 0)
					sb.append("| ");
				sb.append(" ").append(matrix[rowNum][colNum]).append(" ");
				for (int j = 0; j <= columnWiths[colNum] - String.valueOf(matrix[rowNum][colNum]).length(); j++) {
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
		double columnWith = getPrintedColumnWiths(column);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < column.length; i++) {
			sb.append("| ");
			sb.append(" ").append(column[i]).append(" ");
			for (int j = 0; j <= columnWith - String.valueOf(column[i]).length(); j++) {
				sb.append(" ");
			}
			sb.append(" |");
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void validateSquareMatrix(double[][] matrix) throws MatrixException {
		validateMatrix(matrix);
		if (matrix.length < 2 || matrix.length != matrix[0].length) {
			throw new MatrixException("Not a square matrix");
		}
	}

	public static void validateMatrix(double[][] matrix) throws MatrixException {
		if (isEmpty(matrix))
			throw new MatrixException("Not a matrix argument. A matrix must have at least one element");
		int colNum = matrix[0].length;
		for (int i = 1; i < matrix.length; i++) {
			if (matrix[i].length != colNum)
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
	public static boolean equals(final double[][] a, double[][] b) {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[i].length; j++)
				if (Double.compare(a[i][j], b[i][j]) != 0)
					return false;
		return true;
	}

	/**
	 * Сравнивает размерности матриц между собой.
	 * 
	 * @param a первая матрица
	 * @param b вторая матрица
	 * @return true если матрицы равных размерностей, иначе false
	 */
	public static boolean equalDimensions(final double[][] a, double[][] b) {
		if (a.length != b.length)
			return false;
		for (int i = 0; i < a.length; i++)
			if (a[i].length != b[i].length)
				return false;
		return true;
	}

	private static double[] getPrintedColumnWiths(double[][] matrix) throws MatrixException {
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

	private static double getPrintedColumnWiths(double[] matrix) {
		double result = 0.0d;
		for (int i = 0; i < matrix.length; i++) {
			int stringLength = String.valueOf(matrix[i]).length();
			if (stringLength > result)
				result = stringLength;
		}
		return result;
	}

}