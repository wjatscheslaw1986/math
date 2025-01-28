/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.equation;

import linear.equation.exception.LinearEquationSystemException;
import linear.matrix.exception.MatrixException;

public class CramerLinearEquationSystem {

    private final double[][] coefficients;
    private final double[]   freeMembers;
    private final double[]   resolved;

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
     * The last column of the given matrix must consist of the right sides of each equation,
     * i.e. it is the column of free members.
     * 
     * @param rows the given matrix
     * @throws LinearEquationSystemException if the matrix is not a square matrix or is a degenerate one
     */
    public CramerLinearEquationSystem(double[]... rows) throws LinearEquationSystemException {
        this.freeMembers = new double[rows.length];
        this.coefficients = new double[rows.length][rows[0].length - 1];
        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[0].length; col++) {
                if (col == 0) {
                    this.freeMembers[row] = rows[row][col];
                    continue;
                }
                this.coefficients[row][col - 1] = rows[row][col];
            }
        }
        LinearEquationSystemUtil.isCramer(this.coefficients);
        try {
            this.resolved = LinearEquationSystemUtil.resolveUsingCramerMethod(this.coefficients, this.freeMembers);
        } catch (MatrixException me) {
            throw new LinearEquationSystemException("Failed to resolve linear equations system by Cramer method\n"
                    + me.getLocalizedMessage());
        }
    }
}