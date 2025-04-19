/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.equation;

import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.RowEchelonFormUtil;
import linear.matrix.Validation;

import java.util.Arrays;

import static linear.equation.SolutionsCount.*;
import static linear.matrix.MatrixUtil.*;

/**
 * A utility class for solving linear equation systems.
 *
 * @author Viacheslav Mikhailov
 */
public final class LinearEquationSystemUtil {

    private LinearEquationSystemUtil() {
        // static context only
    }

    /**
     * Resolve linear equation system using Cramer method.
     * <p>Only applicable to Cramer matrices. Use {@link linear.matrix.Validation}.isCramer() method
     * beforehand to make sure you aren't getting wrong results.</p>
     *
     * @param coefficients matrix made of left part of the equations
     * @param freeMembers  a vector made of right part of the equations
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     */
    public static double[] resolveUsingCramerMethod(final double[][] coefficients, final double[] freeMembers) {
        final var resolved = new double[freeMembers.length];
        final double determinant = MatrixCalc.det(coefficients);
        for (int i = 0; i < freeMembers.length; i++)
            resolved[i] = MatrixCalc.det(substituteColumn(coefficients, freeMembers, i))
                    / determinant;
        return resolved;
    }

    /**
     * Resolve given linear equations system using reverse matrix method.
     * <p>Only applicable to Cramer matrices. Use {@link linear.matrix.Validation}.isCramer() method
     * beforehand to be sure you aren't getting wrong results.</p>
     *
     * @param coefficients a square matrix of coefficients of the left parts of each equation
     * @param freeMembers  a vector of free members (i.e. right parts of each equation)
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     */
    public static double[] resolveUsingReverseMatrixMethod(double[][] coefficients, double[] freeMembers) {
        var solution = MatrixCalc.multiply(MatrixCalc.inverse(coefficients), freeMembers);
        MatrixUtil.eliminateEpsilon(solution);
        return solution;
    }

    /**
     * Resolve given linear equations system using Jordan-Gauss method.
     * <p>
     *     <b>IMPORTANT:</b> this method modifies the argument as it performs
     *     its job. Use {@link MatrixUtil.copy()} on the argument you pass to this method if
     *     you need it for further calculations.
     * </p>
     *
     * @param equations linear equations matrix including both sides of each equation
     * @return number of solutions for the system. Integer.MAX_VALUE for infinite number of solutions.
     */
    public static Solution resolveUsingJordanGaussMethod(final double[][] equations) {
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

            if (Math.abs(equations[sel][col]) < EPS) {
                equations[sel][col] = .0d;
                continue;
            }

            for (int i = col; i <= colsLeftSideCount; ++i)
                swapInColumn(equations, sel, row, i);

            addresses[col] = row;

            for (int i = 0; i < rowsCount; ++i)
                if (i != row) {
                    double c = equations[i][col] / equations[row][col];
                    for (int j = col; j <= colsLeftSideCount; ++j)
                        equations[i][j] -= equations[row][j] * c;
                }

            ++row;
        }

        final double[] solution = new double[equations[0].length - 1];
        for (int i = 0; i < colsLeftSideCount; ++i)
            if (addresses[i] != -1) {
                solution[i] = equations[addresses[i]][colsLeftSideCount] / equations[addresses[i]][i];
                MatrixUtil.eliminateEpsilon(solution);
            }

        for (int i = 0; i < rowsCount; ++i) {
            double sum = 0;
            for (int j = 0; j < colsLeftSideCount; ++j)
                sum += solution[j] * equations[i][j];

            if (Math.abs(sum - equations[i][colsLeftSideCount]) > EPS)
                return new Solution(ZERO, null, addresses); // No solutions
        }

        for (int i = 0; i < colsLeftSideCount; ++i)
            if (addresses[i] == -1)
                return new Solution(INFINITE, solution, addresses); // Infinite solutions

        return new Solution(SINGLE, solution, addresses); // Unique solution
    }

    /**
     * TODO
     *
     * @param equations
     * @return
     */
//    public static Solution resolve(final double[][] equations) {
//        var rref = MatrixUtil.copy(equations);
//        RowEchelonFormUtil.toRREF(rref);
//        int rowIndex = rref.length - 1;
//        double[] row = rref[rowIndex];
//        var addresses = new int[rref[0].length];
//        for (int i = 0; i < addresses.length; i++) addresses[i] = i;
//        while (rowIndex > -1 && allZeroes(row)) {
//            --rowIndex;
//        }
//        if (rowIndex == rref.length - 1) {
//            if (!Validation.isCramer(rref)) throw new RuntimeException("Supposedly not RREF.");
//            var solution = LinearEquationSystemUtil.resolveUsingCramerMethod(MatrixUtil.removeMarginalColumn(rref, false), MatrixUtil.getColumn(rref, rref[0].length));
//            return new Solution(SINGLE, solution, addresses);
//        }
//        var solutions = new double[equations.length];
//        for (int i = rowIndex; i < equations.length; i++) {
//            solutions[i] = 1.0d; // since a free member can be any we choose it to be one.
//        }
//        int j = rowIndex - 1;
//        while (j > -1) {
//            solutions[j] = rref[j]
//        }
//        return null;
//    }



    private static boolean allZeroes(final double[] numbers) {
        for (double d : numbers) {
            if (d != .0d) {
                return false;
            }
        }
        return true;
    }

    /*
     * This method substitutes one column of a given matrix with a given vector,
     * at a certain position 'index'.
     *
     * @param matrix  the original matrix
     * @param column  the given vector
     * @param index   column index
     * @return        new matrix of same size but with one of its columns substituted by <i>column</i>
     */
    static double[][] substituteColumn(final double[][] matrix, final double[] column, final int index) {
        if (matrix[0].length <= index)
            throw new ArrayIndexOutOfBoundsException(String
                    .format("Column index %d is out of matrix columns size of %d", index, matrix[0].length));
        final double[][] result = MatrixUtil.copy(matrix);
        for (int row = 0; row < matrix.length; row++)
            result[row][index] = column[row];
        return result;
    }

    /**
     * Check if the given system of linear equations solvable,
     * using Rouché–Capelli (Kronecker–Capelli) theorem:
     * <p>A system of linear equations has a solution if and only if its
     * coefficient matrix A and its augmented matrix [A|b] have the same rank.</p>
     *
     * @param augmentedMatrix a matrix of vectors. Each vector represents an equation
     *                        with its last element being the right side of the equation
     * @return true if the given system of linear equations is solvable, false otherwise
     */
    public static boolean isSolvable(final double[][] augmentedMatrix) {
        return MatrixCalc.rank(MatrixUtil.removeMarginalColumn(augmentedMatrix, false)) == MatrixCalc.rank(augmentedMatrix);
    }
}
