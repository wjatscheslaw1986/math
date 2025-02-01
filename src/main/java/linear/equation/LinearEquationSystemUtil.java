/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.equation;

import java.util.Arrays;

import linear.equation.exception.LinearEquationSystemException;
import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.exception.MatrixException;

import static linear.matrix.MatrixUtil.*;
import static linear.matrix.Validation.canMultiply;

/**
 * A utility class for solving linear equation systems.
 *
 * @author Wjatscheslaw Michailov
 */
public final class LinearEquationSystemUtil {

    private LinearEquationSystemUtil() {
        // static context only
    }

    /**
     * Resolve linear equation system using Cramer method.
     *
     * @param coefficients matrix made of left part of equations
     * @param freeMembers vector made of right part of equations
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     */
    public static double[] resolveUsingCramerMethod(final double[][] coefficients, final double[] freeMembers) {
        final var resolved = new double[freeMembers.length];
        final double determinant = MatrixCalc.det(coefficients);
        for (int i = 0; i < freeMembers.length; i++)
            resolved[i] = MatrixCalc.det(substituteColumn(coefficients, freeMembers, i + 1))
                    / determinant;
        return resolved;
    }

    /**
     * Resolve given linear equations system using reverse matrix method.
     *
     * @param coefficients a square matrix of coefficients of the left parts of each equation
     * @param freeMembers  a vector of free members (i.e. right parts of each equasion)
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     */
    public static double[] resolveUsingReverseMatrixMethod(double[][] coefficients, double[] freeMembers) {
return MatrixCalc.multiply(MatrixCalc.reverse(coefficients), freeMembers);
    }

    /**
     * Resolve given linear equations system using Jordan-Gauss method.
     *
     * @param equations linear equations matrix including both sides of each equation
     * @param solution  the modifiable vector for a single solution
     * @return number of solutions for the system. Integer.MAX_VALUE for infinite number of solutions.
     */
    public static int resolveUsingJordanGaussMethod(final double[][] equations, final double[] solution) {
        int rowsCount = equations.length;
        int colsLeftSideCount = equations[0].length - 1;

        // addresses is an array that tells us at which row we may find a value (an answer) for each variable,
        // by their own index both in the equation and in this array.

        int[] addresses = new int[colsLeftSideCount];
        Arrays.fill(addresses, -1);

        for (int col = 0, row = 0; col < colsLeftSideCount && row < rowsCount; ++col) {
            int sel = row;
            for (int i = row; i < rowsCount; ++i)
                if (Math.abs(equations[i][col]) > Math.abs(equations[sel][col]))
                    sel = i;

            if (Math.abs(equations[sel][col]) < EPS)
                continue;

            for (int i = col; i <= colsLeftSideCount; ++i)
                swapValue(equations, sel, row, i);

            addresses[col] = row;

            for (int i = 0; i < rowsCount; ++i)
                if (i != row) {
                    double c = equations[i][col] / equations[row][col];
                    for (int j = col; j <= colsLeftSideCount; ++j)
                        equations[i][j] -= equations[row][j] * c;
                }

            ++row;
        }

        Arrays.fill(solution, 0);
        for (int i = 0; i < colsLeftSideCount; ++i)
            if (addresses[i] != -1)
                solution[i] = equations[addresses[i]][colsLeftSideCount] / equations[addresses[i]][i];

        for (int i = 0; i < rowsCount; ++i) {
            double sum = 0;
            for (int j = 0; j < colsLeftSideCount; ++j)
                sum += solution[j] * equations[i][j];

            if (Math.abs(sum - equations[i][colsLeftSideCount]) > EPS)
                return 0; // No solutions
        }

        for (int i = 0; i < colsLeftSideCount; ++i)
            if (addresses[i] == -1)
                return Integer.MAX_VALUE; // Infinite solutions

        return 1; // Unique solution
    }

    /*
     * This method substitutes one column of a given matrix with another one,
     * at a certain position 'colNum'.
     *
     * @param matrix - the original matrix
     * @param column - an array of same column values, from top to bottom
     * @param colNum - column number (index + 1) of the original matrix to
     *               substitute with 'column'
     * @return new matrix of same size but with one of its columns substituted by
     *         'column'
     */
    static double[][] substituteColumn(final double[][] matrix, final double[] column, final int colNum) {
        if (matrix[0].length < colNum)
            throw new ArrayIndexOutOfBoundsException(String
                    .format("Column index %d is out of matrix columns size of %d", colNum - 1, matrix[0].length));
        final double[][] result = MatrixUtil.copy(matrix);
        for (int row = 0; row < matrix.length; row++)
            result[row][colNum - 1] = column[row];
        return result;
    }
}
