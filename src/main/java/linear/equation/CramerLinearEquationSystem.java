/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.equation;

import linear.matrix.MatrixUtil;
import linear.matrix.Validation;

import java.util.StringJoiner;
import java.util.function.BiFunction;

/**
 * The wrapper object for solution of Cramer Linear Equation Systems.
 * The solution is called eagerly in the constructor.
 *
 * @author Wjatscheslaw Michailov
 */
public class CramerLinearEquationSystem {

    private final double[][] coefficients;
    private final double[] freeMembers;
    private final double[] resolved;

    public double[][] getCoefficients() {
        return coefficients;
    }

    public double[] getFreeMembers() {
        return freeMembers;
    }

    public double[] getResolved() {
        return resolved;
    }

    /**
     * Solve the given Cramer Linear Equation System.
     * The last column of the given matrix is the column of free members (after the 'equals' math symbol).
     *
     * @param method the solving algorithm
     * @param rows the given equations. The last element of each 'row' is the right side of the equation
     */
    public CramerLinearEquationSystem(final BiFunction<double[][], double[], double[]> method, final double[]... rows) {
        this.freeMembers = new double[rows.length];
        this.coefficients = new double[rows.length][rows[0].length - 1];
        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[0].length; col++) {
                if (col == rows[0].length - 1)
                    this.freeMembers[row] = rows[row][col];
                else
                    this.coefficients[row][col] = rows[row][col];
            }
        }
        if (!Validation.isCramer(this.coefficients))
            throw new IllegalArgumentException(String
                    .valueOf(new StringJoiner(System.lineSeparator())
                            .add("Not a Cramer linear equation system")
                            .add(MatrixUtil.print(this.coefficients))));

        this.resolved = method.apply(coefficients, freeMembers);
        MatrixUtil.eliminateEpsilon(this.resolved);
    }
}