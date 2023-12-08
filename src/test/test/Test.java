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

        double[][] matrix = new double[][]{
                {2.0d, 1.0d, 3.0d},
                {1.0d, -1.0d, 7.0d},
                {0.0d, 2.0d, -7.0d}
        };

        double det = DoubleMatrixCalc.det(matrix);
        double[][] revMatrix = DoubleMatrixCalc.multiplyMatrixOnNumber(
                DoubleMatrixCalc.transposeMatrix(
                        DoubleMatrixCalc.cofactorsMatrix(matrix)), det);
        double[] col = new double[]{2.0d, 2.0d, -1.0d};

        System.out.println("det = " + det);
        System.out.println("REV MATRIX");
        System.out.println(DoubleMatrixCalc.print(revMatrix));
        System.out.println("VECTOR");
        System.out.println(DoubleMatrixCalc.print(col));
        col = DoubleMatrixCalc.multiplyMatrixOnColumn(revMatrix, col);
        System.out.println("X = " + col[0]);
        System.out.println("Y = " + col[1]);
        System.out.println("Z = " + col[2]);

    }
}
