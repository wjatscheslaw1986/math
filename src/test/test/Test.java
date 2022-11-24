/*≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈
 ≈ Copyright © 2022. This code has an author and the author is me.
 ≈ My name is Viacheslav Mikhailov.
 ≈ You may contact me via EMail taleskeeper@yandex.ru
 ≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈*/

package test;

import equation.DoubleCramerLinearEquationSystem;
import equation.exception.LinearEquationException;
import matrix.DoubleMatrixCalc;
import matrix.exception.MatrixException;

//This class exists for test purposes only
public class Test {

    public static void main(String... argz) throws MatrixException {

        double[][] matrix2 = {{2.0d, 2.0d, 1.0d, 1.0d}, {5.0d, 1.0d, 3.0d, 1.0d}, {-7.0d, 1.0d, 1.0d, 5.0d}};
        System.out.println();
        System.out.println("MATRIX");
        System.out.print(DoubleMatrixCalc.print(matrix2));
        System.out.println("MATRIX 2");
        System.out.print(DoubleMatrixCalc.print(DoubleMatrixCalc.excludeColumn(matrix2, 1)));
        System.out.println("DET " + DoubleMatrixCalc.det(DoubleMatrixCalc.excludeColumn(matrix2, 1)));
        System.out.println("RANK ");
        System.out.print(DoubleMatrixCalc.rank(matrix2));
        System.out.println();
        try {
            DoubleCramerLinearEquationSystem s = new DoubleCramerLinearEquationSystem(matrix2);
            for (int i = 0; i < matrix2.length; i++) {
                System.out.println("X" + (i + 1) + " = " + s.getResolved()[i]);
            }
        } catch (LinearEquationException lee) {
            System.out.println(lee.getLocalizedMessage());
        }
//
//        double[][] matrix = {{5.0d, 10.0d, -5.0d}, {-1.0d, -11.0d, 7.0d}, {8.0d, 13.0d, -11.0d}};
//        double[] freeMembers = {4.0d, 3.0d, 1.0d};
//
//        double[] ans = DoubleMatrixCalc.multiplyMatrixOnColumn(matrix, freeMembers);
//        for (double n : ans) System.out.println(" " + n);
    }
}
