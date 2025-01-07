/*
 * Copyright (c) 2022. This code has an author and the author is Viacheslav Mikhailov, which is me.
 * You may contact me via EMail taleskeeper@yandex.ru
 */

package linear.equation;

import linear.equation.exception.LinearEquationException;
import linear.matrix.DoubleMatrixCalc;
import linear.matrix.exception.MatrixException;

public class DoubleLinearEquationCalc {
    public static void validateCramer(double[][] matrix) throws LinearEquationException {
        try {
            DoubleMatrixCalc.validateSquareMatrix(matrix);
            if (DoubleMatrixCalc.det(matrix) == .0d)
                throw new LinearEquationException("Cramer Linear Equations System's coefficient matrix shouldn't be degenerated");
        } catch (MatrixException me) {
            throw new LinearEquationException("Cramer Linear Equations System must have same amount of equations as number of its' unknowns\n" + me.getLocalizedMessage());
        }
    }

    public static double[] resolveByCramerRule(double[][] coefficients, double[] freeMembers) throws MatrixException {
        double determinant = DoubleMatrixCalc.det(coefficients);
        double[] resolved = new double[freeMembers.length];
        for (int i = 0; i < freeMembers.length; i++)
            resolved[i] = DoubleMatrixCalc.det(DoubleMatrixCalc.substituteColumn(coefficients, freeMembers, i+1)) / determinant;
        return resolved;
    }

    public static double[] resolveByReverseMatrixMethod(double[][] coefficients, double[] freeMembers) throws MatrixException {
        return DoubleMatrixCalc.multiplyMatrixByColumn(DoubleMatrixCalc.reverse(coefficients), freeMembers);
    }
}
