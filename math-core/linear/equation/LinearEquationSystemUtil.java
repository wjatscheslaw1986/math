/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.equation;

import java.util.Arrays;

import linear.equation.exception.LinearEquationException;
import linear.matrix.DoubleMatrixCalc;
import linear.matrix.exception.MatrixException;

/**
 * A utility class for solving linear equation systems.
 * 
 * @author vaclav
 */
public final class LinearEquationSystemUtil {

    private final static double EPS = 1e-9;

    private LinearEquationSystemUtil() {
        // static context only
    }

    /**
     * Checks if the given matrix of linear equations system's coefficients
     * is a square non-degenerate matrix. If it is, then the equations system
     * can be solved using Cramer method.
     * 
     * @param matrix the given matrix
     * @return true if the given matrix is a 'Cramer' matrix, false otherwise
     */
    public static boolean isCramer(final double[][] matrix) throws LinearEquationException {
        try {
            return DoubleMatrixCalc.isSquare(matrix) && DoubleMatrixCalc.det(matrix) == .0d;
        } catch (MatrixException me) {
            throw new LinearEquationException(me.getLocalizedMessage());
        }
    }

    /**
     * @param coefficients
     * @param freeMembers
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     * @throws MatrixException
     */
    public static double[] resolveUsingCramerMethod(final double[][] coefficients, final double[] freeMembers)
            throws MatrixException {
        double determinant = DoubleMatrixCalc.det(coefficients);
        var resolved = new double[freeMembers.length];
        for (int i = 0; i < freeMembers.length; i++)
            resolved[i] = DoubleMatrixCalc.det(DoubleMatrixCalc.substituteColumn(coefficients, freeMembers, i + 1))
                    / determinant;
        return resolved;
    }

    /**
     * Resolve given linear equations system using reverse matrix method.
     * 
     * @param coefficients a square matrix of coefficients of the left parts of each equation
     * @param freeMembers  a vector of free members (i.e. right parts of each equasion)
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     * @throws MatrixException
     */
    public static double[] resolveUsingReverseMatrixMethod(double[][] coefficients, double[] freeMembers)
            throws MatrixException {
        return DoubleMatrixCalc.multiplyMatrixByColumn(DoubleMatrixCalc.reverse(coefficients), freeMembers);
    }

    /**
     * Resolve given linear equations system using Jordan-Gauss method.
     * 
     * @param equationElements equations matrix
     * @param solution         the modifiable vector for a single solution
     * @return number of solutions. Integer.MAX_VALUE value if infinite number of solutions.
     */
    public static int resolveUsingJordanGaussMethod(double[][] equationElements, double[] solution) {
        int rowsCount = equationElements.length;
        int colsLeftSideCount = equationElements[0].length - 1;

        // addresses is an array that tells us at which row we may find a value (an answer) for each variable,
        // by their own index both in the equation and in this array.

        int[] addresses = new int[colsLeftSideCount];
        Arrays.fill(addresses, -1);

        for (int col = 0, row = 0; col < colsLeftSideCount && row < rowsCount; ++col) {
            int sel = row;
            for (int i = row; i < rowsCount; ++i) {
                if (Math.abs(equationElements[i][col]) > Math.abs(equationElements[sel][col])) {
                    sel = i;
                }
            }

            if (Math.abs(equationElements[sel][col]) < EPS) {
                continue;
            }

            for (int i = col; i <= colsLeftSideCount; ++i) {
                swapColumn(equationElements, sel, row, i);
            }

            addresses[col] = row;

            for (int i = 0; i < rowsCount; ++i) {
                if (i != row) {
                    double c = equationElements[i][col] / equationElements[row][col];
                    for (int j = col; j <= colsLeftSideCount; ++j) {
                        equationElements[i][j] -= equationElements[row][j] * c;
                    }
                }
            }

            ++row;
        }

        Arrays.fill(solution, 0);
        for (int i = 0; i < colsLeftSideCount; ++i) {
            if (addresses[i] != -1) {
                solution[i] = equationElements[addresses[i]][colsLeftSideCount] / equationElements[addresses[i]][i];
            }
        }

        for (int i = 0; i < rowsCount; ++i) {
            double sum = 0;
            for (int j = 0; j < colsLeftSideCount; ++j) {
                sum += solution[j] * equationElements[i][j];
            }
            if (Math.abs(sum - equationElements[i][colsLeftSideCount]) > EPS) {
                return 0; // No solutions
            }
        }

        for (int i = 0; i < colsLeftSideCount; ++i) {
            if (addresses[i] == -1) {
                return Integer.MAX_VALUE; // Infinite solutions
            }
        }

        return 1; // Unique solution
    }

    private static void swapColumn(final double[][] matrix, final int a, final int b, final int col) {
        final double temp = matrix[a][col];
        matrix[a][col] = matrix[b][col];
        matrix[b][col] = temp;
    }
}
