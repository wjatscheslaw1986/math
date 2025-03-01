/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com)  © 2025.
 */
package linear;

import combinatorics.CyclicShiftPermutationsGenerator;
import combinatorics.JohnsonTrotterPermutationsGenerator;
import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.RowEchelonFormUtil;
import linear.matrix.exception.MatrixException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import static linear.matrix.MatrixUtil.print;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests.
 *
 * @author Viacheslav Mikhailov
 */
public final class IntegrationTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s.class%n", IntegrationTest.class);
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
        assertTrue(MatrixCalc.areEqual(AB, MatrixCalc.multiply(A, B)));

        double[][] BA = new double[][]{{7.0d, 29.0d}, {-12.0d, -7.0d}};
        assertTrue(MatrixCalc.areEqual(BA, MatrixCalc.multiply(B, A)));

        double[][] AB_minus_BA = new double[][]{{-2.0d, -2.0d}, {.0d, 2.0d}};
        assertTrue(MatrixCalc.areEqual(AB_minus_BA, MatrixCalc.subtract(AB, BA)));
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
        assertTrue(MatrixCalc.areEqual(BB, MatrixCalc.multiply(B, B)));

        double[][] B_by_B_by_3_minus_A_by_2 = new double[][]{{-15.0d, -4.0d}, {30.0d, -7.0d}};
        assertTrue(MatrixCalc.areEqual(B_by_B_by_3_minus_A_by_2, MatrixCalc
                .subtract(MatrixCalc.multiply(MatrixCalc.multiply(B, B), 3.0d), MatrixCalc.multiply(A, 2.0d))));
        // System.out.println(DoubleMatrixCalc.print(B_by_B_by_3_minus_A_by_2));
        double det = 225;
        assertEquals(det, MatrixCalc.det(B_by_B_by_3_minus_A_by_2));

    }

    /**
     * Транспонирование произведения любых двух матриц должно быть равно
     * произведению транспонированных этих двух матриц, т.е. (A * B)_t == B_t * A_t.
     * <p>
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
            final double[][] abt = MatrixCalc.transpose(MatrixCalc.multiply(a, b));
            final double[][] at_bt = MatrixCalc.multiply(MatrixCalc.transpose(b), MatrixCalc.transpose(a));

            assertTrue(MatrixCalc.areEqual(abt, at_bt));
            assertTrue(MatrixCalc.areEqual(at_bt, abt));
        }
    }

    @Test
    void transformationMatrixInAnotherBasis1() throws MatrixException {
        // System.out.println(Math.cos(Math.toRadians(135)));
        // System.out.println(Math.cos(Math.toRadians(45)));

        double[][] a = new double[][]{{1.0d, 2.0d}, {1.0d, -1.0d}};
        double[][] b = new double[][]{{1.0d, 3.0d}, {-1.0d, 1.0d}};
        double[][] a_in_another_basis = MatrixCalc.multiply(new double[][]{{-7.0d, -1.0d}, {1.0d, 7.0d}}, 0.25d);
        double[][] b_rev = MatrixCalc.reverse(b);

        // System.out.println("B_1 * A = " + DoubleMatrixCalc.print(DoubleMatrixCalc.multiplyMatrices(b_rev, a)));
        // System.out.println("B_1 * A * B = " +
        // DoubleMatrixCalc.print(DoubleMatrixCalc.multiplyMatrices(DoubleMatrixCalc.multiplyMatrices(
        // b_rev, a), b)));
        // System.out.println("B * A * B_1 = " +
        // DoubleMatrixCalc.print(DoubleMatrixCalc.multiplyMatrices(DoubleMatrixCalc.multiplyMatrices(
        // b, a), b_rev)));
        // System.out.println("Answer = " + DoubleMatrixCalc.print(a_in_another_basis));

        assertTrue(MatrixCalc.areEqual(a_in_another_basis, MatrixCalc.multiply(MatrixCalc.multiply(b_rev, a), b)));
        assertFalse(MatrixCalc.areEqual(a_in_another_basis, MatrixCalc.multiply(MatrixCalc.multiply(b, a), b_rev)));

    }

    @Test
    void transformationMatrixInAnotherBasis2() throws MatrixException {
        // System.out.println(Math.cos(Math.toRadians(135)));
        // System.out.println(Math.cos(Math.toRadians(45)));

        double[][] O = new double[][]{{1.0d, 0.0d}, {0.0d, 0.0d}};
        double[][] A = new double[][]{{Math.cos(Math.toRadians(135)), Math.cos(Math.toRadians(45))},
                {Math.cos(Math.toRadians(45)), Math.cos(Math.toRadians(45))}};
        double[][] A_rev = MatrixCalc.reverse(A);

        System.out.println(print(A));
        System.out.println(print(A_rev));
        System.out.println(print(MatrixCalc.multiply(A, O)));
        System.out.println(print(MatrixCalc.multiply(MatrixCalc.multiply(A, O), A_rev)));
        //
        // System.out.println(DoubleMatrixCalc
        // .print(DoubleMatrixCalc.multiplyMatrices(DoubleMatrixCalc.multiplyMatrices(A, O), A_rev)));

    }

    /**
     * @throws MatrixException in case of a malformed matrix
     */
    @Test
    void rowEchelonFormRandomMatrixTest() throws MatrixException {
        final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();
        for (int i = 0; i < 75; i++) {
            final int n = rnd.nextInt(1, 25);
            final int m = rnd.nextInt(1, 25);
            var matrix = MatrixGenerator.generateRandomDoubleMatrix(n, m);
            var refMatrix = RowEchelonFormUtil.toRowEchelonForm(matrix);
//            uncomment for debug
//            if (!RowEchelonFormUtil.isRowEchelonForm(refMatrix)) {
//                System.out.print("stop");
//            }
            assertTrue(RowEchelonFormUtil.isRowEchelonForm(refMatrix));
        }
    }

    @Test
    void rowEchelonFormTest() throws MatrixException {
        var matrix = new double[][]{
                {-3.0d, 3.0d, -1.0d, -7.0d, 5.0d},
                {2.0d, 3.0d, -5.0d, 26.0d, -4.0d},
                {3.0d, -4.0d, 8.0d, -9.0d, 1.0d},
                {-4.0d, 1.0d, -3.0d, -12.0d, 2.0d}
        };
        var refMatrix = RowEchelonFormUtil.toRowEchelonForm(matrix);
        assertTrue(RowEchelonFormUtil.isRowEchelonForm(refMatrix));
        System.out.println(MatrixUtil.print(refMatrix));
    }

    @Test
    void rowEchelonFormTest2() throws MatrixException {
        var matrix = new double[][]{
                {-3.0d, 3.0d, -1.0d, -7.0d, 5.0d},
                {2.0d, 3.0d, -5.0d, 26.0d, -4.0d},
                {3.0d, -4.0d, 8.0d, -9.0d, 1.0d},
                {-4.0d, 1.0d, -3.0d, -12.0d, 2.0d}
        };
        assertFalse(RowEchelonFormUtil.isRowEchelonForm(matrix));
        var refMatrix = RowEchelonFormUtil.toREF(matrix);
        assertTrue(RowEchelonFormUtil.isRowEchelonForm(refMatrix));
        System.out.println(MatrixUtil.print(refMatrix));
    }

    @Test
    void rowEchelonFormTest3() throws MatrixException {
        var linearEquationSystem = new double[][]{
                {2, -1, 3, 4, 5},
                {6, -3, 7, 8, 9},
                {5, -4, 9, 10, 11},
                {4, -2, 5, 6, 7}
        };
        assertFalse(RowEchelonFormUtil.isRowEchelonForm(linearEquationSystem));
        var refMatrix = RowEchelonFormUtil.toRowEchelonForm(linearEquationSystem);
        assertTrue(RowEchelonFormUtil.isRowEchelonForm(refMatrix));
        System.out.println(MatrixUtil.print(refMatrix));
    }

    @Test
    void permutationsGeneratorsTest() {
        for (int i = 0; i < 7; i++) {
            List<int[]> l1 = CyclicShiftPermutationsGenerator.generate(i);
            List<int[]> l2 = JohnsonTrotterPermutationsGenerator.generate(i);
            assertEquals(l1.size(), l2.size());

            for (int[] i1s : l1) {
                var found = false;
                for (int[] i2s : l2) {
                    if (Arrays.equals(i2s, i1s)) {
                        found = true;
                        break;
                    }
                }
                assertTrue(found);
            }
        }
    }
}
