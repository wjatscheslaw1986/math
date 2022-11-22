package equation;

import equation.exception.LinearEquationException;
import matrix.DoubleMatrixCalc;
import matrix.exception.MatrixException;

public class DoubleKramerLinearEquationSystem {

    private double[][] coefficients;
    private double[] freeMembers;
    private double[] resolved;

    public double[][] getCoefficients() {
        return coefficients;
    }

    public double[] getFreeMembers() {
        return freeMembers;
    }

    public double[] getResolved() {
        return resolved;
    }

    public DoubleKramerLinearEquationSystem(double[]... rows) throws LinearEquationException {
        freeMembers = new double[rows.length];
        coefficients = new double[rows.length][rows[0].length-1];
        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[0].length; col++) {
                if (col == 0) {
                    freeMembers[row] = rows[row][col];
                    continue;
                }
                coefficients[row][col-1] = rows[row][col];
            }
        }
        validateKramer(coefficients);
        try {
            this.resolveKramer();
        } catch (MatrixException me) {
            throw new LinearEquationException("Failed to resolve linear equations system with Kramer method\n" + me.getLocalizedMessage());
        }
    }

    private void resolveKramer() throws MatrixException {
        double determinant = DoubleMatrixCalc.det(coefficients);
        resolved = new double[freeMembers.length];
        for (int i = 0; i < freeMembers.length; i++)
            resolved[i] = DoubleMatrixCalc.det(DoubleMatrixCalc.substituteColumn(coefficients, freeMembers, i+1))/ determinant;
    }

    public static void validateKramer(double[][] matrix) throws LinearEquationException {
        try {
            DoubleMatrixCalc.validateSquareMatrix(matrix);
            if (DoubleMatrixCalc.det(matrix) == .0d)
                throw new LinearEquationException("Kramer Linear Equations System's coefficient matrix shouldn't be degenerated");
        } catch (MatrixException me) {
            throw new LinearEquationException("Kramer Linear Equations System must have same amount of equations as number of its' unknowns\n" + me.getLocalizedMessage());
        }
    }
}