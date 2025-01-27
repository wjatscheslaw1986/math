/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import linear.matrix.MatrixCalc;
import linear.matrix.exception.MatrixException;

/**
 * @author vaclav
 */
public final class IntegrationTest {

    @BeforeAll
    static void before() {
        System.out.println(String.format("Running tests in %s.class", IntegrationTest.class));
    }

    /**
     * Даны две матрицы A и B, найти матрицу AB - BA.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     * 
     * @throws MatrixException в случае неправильных матриц
     */
    @Test
    public void matricesMultiplicationAndSubtraction() throws MatrixException {
        double[][] A = new double[][]{{3.0d, -4.0d}, {2.0d, 5.0d}};
        double[][] B = new double[][]{{-1.0d, 5.0d}, {-2.0d, -3.0d}};

        double[][] AB = new double[][]{{5.0d, 27.0d}, {-12.0d, -5.0d}};
        // System.out.println(DoubleMatrixCalc.print(AB));
        // System.out.println(DoubleMatrixCalc.print(DoubleMatrixCalc.multiplyMatrices(A, B)));
        Assertions.assertTrue(MatrixCalc.equals(AB, MatrixCalc.multiplyMatrices(A, B)));

        double[][] BA = new double[][]{{7.0d, 29.0d}, {-12.0d, -7.0d}};
        Assertions.assertTrue(MatrixCalc.equals(BA, MatrixCalc.multiplyMatrices(B, A)));

        double[][] AB_minus_BA = new double[][]{{-2.0d, -2.0d}, {.0d, 2.0d}};
        Assertions.assertTrue(MatrixCalc.equals(AB_minus_BA, MatrixCalc.subtract(AB, BA)));
    }

    /**
     * Даны две матрицы A и B, найти определитель матрицы 3*(B^2) - 2*A.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     * 
     * @throws MatrixException в случае неправильных матриц
     */
    @Test
    public void findDeterminantOfSubtractedMulitpliedByNumberMatrix() throws MatrixException {
        double[][] A = new double[][]{{-9.0d, -22.0d}, {15.0d, -25.0d}};
        double[][] B = new double[][]{{3.0d, -4.0d}, {5.0d, 1.0d}};

        double[][] BB = new double[][]{{-11.0d, -16.0d}, {20.0d, -19.0d}};
        assertTrue(MatrixCalc.equals(BB, MatrixCalc.multiplyMatrices(B, B)));

        double[][] B_by_B_by_3_minus_A_by_2 = new double[][]{{-15.0d, -4.0d}, {30.0d, -7.0d}};
        assertTrue(MatrixCalc
            .equals(B_by_B_by_3_minus_A_by_2,
                    MatrixCalc.subtract(MatrixCalc.multiplyMatrixByNumber(MatrixCalc.multiplyMatrices(B, B), 3.0d),
                                        MatrixCalc.multiplyMatrixByNumber(A, 2.0d))));
        // System.out.println(DoubleMatrixCalc.print(B_by_B_by_3_minus_A_by_2));
        double det = 225;
        assertEquals(det, MatrixCalc.det(B_by_B_by_3_minus_A_by_2));

    }

    /**
     * Транспонирование произведения любых двух матриц должно быть равно
     * произведению транспонированных этих двух матриц, т.е. (A * B)_t == B_t * A_t.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     * 
     * @throws MatrixException в случае неправильных матриц
     */
    @Test
    public void transposedProductOfMatricesEqualsToProductOfTransposedMatrices() throws MatrixException {
        final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();

        for (int i = 0; i < 100; i++) {
            final int n = rnd.nextInt(1, 100);
            final int m = rnd.nextInt(1, 100);
            final double[][] a = MatrixGenerator.generateRandomDoubleMatrix(n, m);
            final double[][] b = MatrixGenerator.generateRandomDoubleMatrix(m, n);
            final double[][] abt = MatrixCalc.transposeMatrix(MatrixCalc.multiplyMatrices(a, b));
            final double[][] at_bt = MatrixCalc.multiplyMatrices(MatrixCalc.transposeMatrix(b),
                                                                 MatrixCalc.transposeMatrix(a));

            assertTrue(MatrixCalc.equals(abt, at_bt));
            assertTrue(MatrixCalc.equals(at_bt, abt));
        }
    }

    @Test
    void transformationMatrixInAnotherBasis1() throws MatrixException {
        // System.out.println(Math.cos(Math.toRadians(135)));
        // System.out.println(Math.cos(Math.toRadians(45)));

        double[][] a = new double[][]{{1.0d, 2.0d}, {1.0d, -1.0d}};
        double[][] b = new double[][]{{1.0d, 3.0d}, {-1.0d, 1.0d}};
        double[][] a_in_another_basis = MatrixCalc.multiplyMatrixByNumber(new double[][]{{-7.0d, -1.0d}, {1.0d, 7.0d}},
                                                                          0.25d);
        double[][] b_rev = MatrixCalc.reverse(b);

        // System.out.println("B_1 * A = " + DoubleMatrixCalc.print(DoubleMatrixCalc.multiplyMatrices(b_rev, a)));
        // System.out.println("B_1 * A * B = " +
        // DoubleMatrixCalc.print(DoubleMatrixCalc.multiplyMatrices(DoubleMatrixCalc.multiplyMatrices(
        // b_rev, a), b)));
        // System.out.println("B * A * B_1 = " +
        // DoubleMatrixCalc.print(DoubleMatrixCalc.multiplyMatrices(DoubleMatrixCalc.multiplyMatrices(
        // b, a), b_rev)));
        // System.out.println("Answer = " + DoubleMatrixCalc.print(a_in_another_basis));

        assertTrue(MatrixCalc.equals(a_in_another_basis,
                                     MatrixCalc.multiplyMatrices(MatrixCalc.multiplyMatrices(b_rev, a), b)));
        assertFalse(MatrixCalc.equals(a_in_another_basis,
                                      MatrixCalc.multiplyMatrices(MatrixCalc.multiplyMatrices(b, a), b_rev)));

    }

    @Test
    void transformationMatrixInAnotherBasis2() throws MatrixException {
        // System.out.println(Math.cos(Math.toRadians(135)));
        // System.out.println(Math.cos(Math.toRadians(45)));

        double[][] O = new double[][]{{1.0d, 0.0d}, {0.0d, 0.0d}};
        double[][] A = new double[][]{{Math.cos(Math.toRadians(135)), Math.cos(Math.toRadians(45))},
                                      {Math.cos(Math.toRadians(45)), Math.cos(Math.toRadians(45))}};
        double[][] A_rev = MatrixCalc.reverse(A);

        System.out.println(MatrixCalc.print(A));
        System.out.println(MatrixCalc.print(A_rev));
        System.out.println(MatrixCalc.print(MatrixCalc.multiplyMatrices(A, O)));
        System.out.println(MatrixCalc.print(MatrixCalc.multiplyMatrices(MatrixCalc.multiplyMatrices(A, O), A_rev)));
        //
        // System.out.println(DoubleMatrixCalc
        // .print(DoubleMatrixCalc.multiplyMatrices(DoubleMatrixCalc.multiplyMatrices(A, O), A_rev)));

    }

    /**
     * @throws MatrixException in case of a malformed matrix
     */
    @Test
    public void rowEchelonFormTest() throws MatrixException {
        final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();
        for (int i = 0; i < 35; i++) {
            final int n = rnd.nextInt(1, 15);
            final int m = rnd.nextInt(1, 15);
            var matrix = MatrixGenerator.generateRandomDoubleMatrix(n, m);
            var refMatrix = MatrixCalc.toRowEchelonForm(matrix);
            assertTrue(MatrixCalc.isRowEchelonForm(refMatrix));
        }
    }
}
