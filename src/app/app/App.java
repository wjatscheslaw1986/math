package app;

import equation.DoubleKramerLinearEquationSystem;
import equation.exception.LinearEquationException;
import matrix.DoubleMatrixCalc;
import matrix.exception.MatrixException;

public class App {
    public static void main(String... argz) throws MatrixException {

        double[][] matrix2 = {{2.0d, 2.0d, 1.0d, 1.0d}, {5.0d, 1.0d, 3.0d, 1.0d}, {-7.0d, 1.0d, 1.0d, 5.0d}};
        System.out.println("");
        System.out.println("MATRIX");
        System.out.print(DoubleMatrixCalc.print(matrix2));
        System.out.println("MATRIX 2");
        System.out.print(DoubleMatrixCalc.print(DoubleMatrixCalc.excludeColumn(matrix2, 1)));
        System.out.println("DET " + DoubleMatrixCalc.det(DoubleMatrixCalc.excludeColumn(matrix2, 1)));
        System.out.println("RANK ");
        System.out.print(DoubleMatrixCalc.rank(matrix2));
        System.out.println("");
        try {
            DoubleKramerLinearEquationSystem s = new DoubleKramerLinearEquationSystem(matrix2);
            for (int i = 0; i < matrix2.length; i++) {
                System.out.println("X" + String.valueOf(i + 1) + " = " + s.getResolved()[i]);
            }
        } catch (LinearEquationException lee) {
            System.out.println(lee.getLocalizedMessage());
        }

    }
}