/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.matrix;

import linear.MatrixGenerator;
import linear.matrix.exception.MatrixException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import static linear.matrix.MatrixCalc.*;
import static linear.matrix.MatrixUtil.EPS;
import static linear.matrix.RowEchelonFormUtil.*;
import static linear.matrix.Validation.isEqualDimensions;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@linkplain linear.matrix.MatrixCalc} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class MatrixCalcTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s.class%s",
                linear.matrix.MatrixCalcTest.class, System.lineSeparator());
    }

    @Test
    public void equals() {
        double[][] a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}, {0.0d, 2.0d, -7.0d}};
        double[][] b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}, {0.0f, 2.0f, -7.0f}};
        Assertions.assertTrue(areEqual(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertTrue(areEqual(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {3, -7}};
        Assertions.assertFalse(areEqual(a1, b1));
        a1 = new double[][]{{2.0d, -1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertFalse(areEqual(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{-2, 1}, {2, -7}};
        Assertions.assertFalse(areEqual(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {2.0d, 7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertFalse(areEqual(a1, b1));
        a1 = new double[][]{{2.0d, 3.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertFalse(areEqual(a1, b1));
        a1 = new double[][]{{2.1d, 1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertFalse(areEqual(a1, b1));
    }

    @Test
    public void equalDimensionsTest() {
        double[][] a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}, {0.0d, 2.0d, -7.0d}};
        double[][] b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}, {0.0f, 2.0f, -7.0f}};
        Assertions.assertTrue(isEqualDimensions(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}, {0.0d, 2.0d, -7.0d}};
        b1 = new double[][]{{2.0f, 1.0f}, {1.0f, -1.0f}, {0.0f, 2.0f}};
        Assertions.assertFalse(isEqualDimensions(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {-1.0d, 7.0d}, {0.0d, -7.0d}};
        b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}, {0.0f, 2.0f, -7.0f}};
        Assertions.assertFalse(isEqualDimensions(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}};
        b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}, {0.0f, 2.0f, -7.0f}};
        Assertions.assertFalse(isEqualDimensions(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}, {0.0f, 2.0f, -7.0f}};
        b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}};
        Assertions.assertFalse(isEqualDimensions(a1, b1));
    }

    /**
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая Геометрия" - 2006
     *
     * @throws MatrixException в случае неправильных матриц
     */
    @Test
    public void matrixExpressionsTest() throws MatrixException {

        /*
         * Даны две матрицы C и D, найти (3*C + C*D)^T
         */

        var C = new double[][]{
                {3, -1, 1},
                {0, 2, -5},
                {1, -1, 2}
        };

        var D = new double[][]{
                {-2.0d, 2.0d, .0d},
                {4.0d, 1.0d, 3.0d},
                {-3.0d, 2.0d, 1.0d}
        };

        var answer = new double[][]{
                {-4.0, 23.0, -9.0},
                {4.0, -2.0, 2.0},
                {1.0, -14.0, 5.0}};

        var result = MatrixCalc.transpose(
                MatrixCalc.sum(
                        MatrixCalc.multiply(C, 3), MatrixCalc.multiply(C, D)));

        assertTrue(MatrixCalc.areEqual(answer, result));

//        System.out.println(print(result));
    }

    /**
     * Даны две матрицы A и B, найти произведение этих матриц.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     *
     * @throws MatrixException в случае неправильных матриц
     */
    @Test
    public void multiplyMatrices() throws MatrixException {
        final double[][] A = new double[][]{{1.0d, 3.0d, -1.0d}, {0.0d, 5.0d, 2.0d}, {3.0d, -2.0d, 4.0d}};
        final double[][] B = new double[][]{{1.0f, 2.0f, 0.0f}, {3.0f, 1.0f, 2.0f}, {-4.0f, 1.0f, 5.0f}};
        final double[][] result = new double[][]{{14, 4, 1}, {7, 7, 20}, {-19, 8, 16}};
        Assertions.assertDoesNotThrow(() -> multiply(A, B));
        Assertions.assertTrue(areEqual(result, multiply(A, B)));

        /*
         * Перемножение матриц требует, чтобы количество колонок матрицы слева равнялось
         * количеству строк матрицы справа.
         */
        final double[][] b2 = new double[][]{{1.0f, 2.0f, 0.0f}, {3.0f, 1.0f, 2.0f}};
        Assertions.assertThrows(MatrixException.class, () -> multiply(A, b2));
    }

    /**
     * Даны матрицы, найти обратные для каждой из них.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @Test
    public void reverseTest() {
        final double[][] a = new double[][]{{6.0d, 4.0d}, {-2.0d, -1.0d}};
        final double[][] b = new double[][]{{3.0f, 4.0f}, {9.0f, 12.0f}};
        final double[][] c = new double[][]{{2.0d, 0.0d, 2.0d}, {-3.0d, 2.0d, 0.0d}, {6.0d, -2.0d, 4.0d}};
        final double[][] a_rev = new double[][]{{-.5d, -2.0d}, {1.0d, 3.0d}};
        final double[][] c_rev = new double[][]{{2.0d, -1.0d, -1.0d}, {3.0d, -1.0d, -1.5d}, {-1.5d, 1.0d, 1.0d}};

        Assertions.assertDoesNotThrow(() -> reverse(a));
        Assertions.assertTrue(areEqual(reverse(a), a_rev));
        Assertions.assertThrows(IllegalArgumentException.class, () -> reverse(b));
        Assertions.assertDoesNotThrow(() -> reverse(c));
        Assertions.assertTrue(areEqual(reverse(c), c_rev));
    }

    /**
     * Given a matrix, calculate it's submatrix.
     *
     * @throws MatrixException in case of a malformed matrix
     */
    @Test
    public void squareSubmatrixTest() throws MatrixException {
        final double[][] matrix0 = new double[][]{{0.0d, 2.0d, -4.0d}, {-1.0d, -4.0d, 5.0d}, {3.0d, 1.0d, 7.0d},
                {0.0d, 5.0d, -10.0d}};

        final double[][] matrix1 = new double[][]{{0.0d, 2.0d, -4.0d}, {-1.0d, -4.0d, 5.0d}, {3.0d, 1.0d, 7.0d}};
        final double[][] matrix2 = new double[][]{{-1.0d, -4.0d, 5.0d}, {3.0d, 1.0d, 7.0d}, {0.0d, 5.0d, -10.0d}};

        assertTrue(areEqual(matrix1, squareSubmatrix(matrix0, 0, 0, 3)));
        assertTrue(areEqual(matrix2, squareSubmatrix(matrix0, 1, 0, 3)));
    }

    /**
     * Given a matrix, calculate it's rank.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @SuppressWarnings("deprecation")
    @Test
    public void rankTest() {
        double[][] matrix = new double[][]{{0.0d, 2.0d, -4.0d}, {-1.0d, -4.0d, 5.0d}, {3.0d, 1.0d, 7.0d},
                {0.0d, 5.0d, -10.0d}};
        assertEquals(2, rank(matrix));
        assertEquals(2, rankByMinors(matrix));

        matrix = new double[][]{{-2.0d, 3.0d, 1.0d, 4.0}, {4.0d, -6.0d, -2.0d, -8.0d}, {-6.0d, 9.0d, 3.0d, 12.0d}};
        assertEquals(1, rank(matrix));
        assertEquals(1, rankByMinors(matrix));

        matrix = new double[][]{{3.0d, 1.0d}, {2.0d, 5.0d}, {-1.0d, 7.0d}};
        assertEquals(2, rank(matrix));
        assertEquals(2, rankByMinors(matrix));

        matrix = new double[][]{{2.0d, 4.0d, 1.0d, 2.0d}, {1.0d, -3.0d, 0.0d, 4.0d}, {3.0d, 5.0d, 1.0d, 7.0d}};
        assertEquals(3, rank(matrix));
        assertEquals(3, rankByMinors(matrix));

        matrix = new double[][]{{-3.0d, 3.0d, -1.0d, -7.0d, 5.0d}, {2.0d, 3.0d, -5.0d, 26.0d, -4.0d},
                {3.0d, -4.0d, 8.0d, -9.0d, 1.0d}, {-4.0d, 1.0d, -3.0d, -12.0d, 2.0d}};
        assertEquals(3, rank(matrix));
        assertEquals(3, rankByMinors(matrix));

        matrix = new double[][]{{2.0d, 1.0d, -1.0d, -1.0d, 1.0d}, {3.0d, 0.0d, 0.0d, 0.0d, -1.0d},
                {3.0d, 3.0d, -3.0d, -3.0d, 4.0d}, {4.0d, 5.0d, -5.0d, -5.0d, 7.0d}};
        assertEquals(2, rank(matrix));
        assertEquals(2, rankByMinors(matrix));
    }

    /**
     * Given a matrix, convert it to a trapezoidal matrix. TODO
     */
    @Test
    public void tryTrapezoid() {
        final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();
        for (int i = 0; i < 10; i++) {
            var matrix = MatrixGenerator.generateRandomDoubleMatrix(rnd.nextInt(3, 7), rnd.nextInt(3, 7));
            // TODO трапецевидную форму
            // assertFalse(DoubleMatrixCalc.isTrapezoid(matrix));
            // assertTrue(DoubleMatrixCalc.isTrapezoid(DoubleMatrixCalc.tryTrapezoid(matrix)));
        }
    }

    /**
     * Given a matrix, check if it is a trapezoidal one.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @Test
    public void isTrapezoid() {
        final double[][] matrix = new double[][]{{1.0d, -3.0d, -5.0d, -3.0d}, {0.0d, 4.0d, 11.0d, 7.0d},
                {0.0d, 0.0d, 2.0d, 0.0d}};
        assertTrue(isTrapezoidForm(matrix));
    }

    /**
     * Given a matrix, check if it is a row echelon form.
     *
     * @throws MatrixException in case of a malformed matrix
     */
    @Test
    public void isRowEchelonFormTest() throws MatrixException {
        double[][] matrix = new double[][]{{1.0d, -3.0d, -5.0d, -3.0d}, {0.0d, 4.0d, 11.0d, 7.0d},
                {0.0d, 0.0d, 2.0d, 0.0d}};
        var rowEchelonMatrix = toRowEchelonForm(matrix);
        assertTrue(isRowEchelonForm(matrix));
        assertTrue(isRowEchelonForm(rowEchelonMatrix));

        // In general, row echelon form (non-reduced) is not unique. But in the case when matrix is in REF already, it
        // should not change.
        assertTrue(areEqual(rowEchelonMatrix, matrix));

        matrix = new double[][]{{-5.4d, -3.8d, -5.42d}};
        rowEchelonMatrix = toRowEchelonForm(matrix);
        assertTrue(isRowEchelonForm(matrix));
        assertTrue(isRowEchelonForm(rowEchelonMatrix));
        assertTrue(areEqual(rowEchelonMatrix, matrix));

        matrix = new double[][]{{1.1883620478410215d, 4.084346689561142d, 0.175702923175848d, -6.932205740519737}};
        rowEchelonMatrix = toRowEchelonForm(matrix);
        assertTrue(isRowEchelonForm(matrix));
        assertTrue(isRowEchelonForm(rowEchelonMatrix));
        assertTrue(areEqual(rowEchelonMatrix, matrix));

        matrix = new double[][]{{-3.4408949597421845d, 2.94181514630203d}, {2.490922186823001d, -8.99332700307957d},
                {4.740206117048821d, -6.958867337177139},
                {8.154390360529227d, -1.0613990534676798}};
        rowEchelonMatrix = new double[][]{{8.154390360529227d, 0.0d}, {0.0d, 2.94181514630203d}, {0.0d, 0.0d},
                {0.0d, 0.0d}};
        var rowEchelonMatrix_ = toRowEchelonForm(matrix);
        assertFalse(isRowEchelonForm(matrix));
        assertTrue(isRowEchelonForm(rowEchelonMatrix));
        assertTrue(isRowEchelonForm(rowEchelonMatrix_));

        // The row echelon form (non-reduced) is not unique.
        assertFalse(areEqual(rowEchelonMatrix, rowEchelonMatrix_));

        matrix = new double[][]{{1.0096634218430616d, -8.772286757703684d, -0.44083720521676284d, 0.8588440971684381d},
                {6.418590743167737d, -6.420896947702297d, 3.5253331532074323d, 4.864061213084703d},
                {4.846333023426332d, -8.178851479847493d, -7.092716530960487d, -4.478958333291305d},
                {-1.2029012827902505d, 5.915048574621862d, 5.584530663132492d, 2.962818038754156d}};
        rowEchelonMatrix = toRowEchelonForm(matrix);
        assertFalse(isRowEchelonForm(matrix));
        assertTrue(isRowEchelonForm(rowEchelonMatrix));
    }

    @Test
    void isEqualMatrixTest() {
        double[][] matrix1 = {{3, -4}, {2, 5}};
        double[][] matrix3 = {{-1, 5}, {-2, -3}};
        double[][] matrix5 = {{2, 5}, {3, -4}};
        double[][] matrix7 = {{-2, -3}, {-1, 5}};

        assertTrue(areEqual(matrix1, matrix1));
        assertFalse(areEqual(matrix1, matrix3));
        assertTrue(areEqual(matrix3, matrix3));
        assertFalse(areEqual(matrix3, matrix5));
        assertTrue(areEqual(matrix5, matrix5));
        assertFalse(areEqual(matrix5, matrix7));
        assertTrue(areEqual(matrix7, matrix7));

        double[][] matrix8 = {{-2}, {-3}, {-1}, {5}};
        double[][] matrix9 = {{-2}, {-7}, {-1}, {5}};
        assertTrue(areEqual(matrix8, matrix8));
        assertFalse(areEqual(matrix8, matrix9));
        assertTrue(areEqual(matrix9, matrix9));
    }

    @Test
    void isEqualVectorTest() {
        double[] vector1 = {-2, -3, -1, 5};
        double[] vector2 = {-2, -7, -1, 5};
        assertTrue(areEqual(vector1, vector1));
        assertFalse(areEqual(vector1, vector2));
        assertTrue(areEqual(vector2, vector2));
    }

    @Test
    void cofactorsTest() {
        var matrix = new double[][]{{2, -3, -2}, {3, -1, 4}, {3, 1, 5}};
        var cofactorsMatrix = new double[][]{{-9, -3, 6}, {13, 16, -11}, {-14, -14, 7}};
        assertTrue(areEqual(cofactorsMatrix, MatrixCalc.cofactors(matrix)));
    }

    @Test
    void transposeTest() {
        var cofactorsMatrix = new double[][]{{-9, -3, 6}, {13, 16, -11}, {-14, -14, 7}};
        var transposedCofactorsMatrix = new double[][]{{-9, 13, -14}, {-3, 16, -14}, {6, -11, 7}};
        assertTrue(areEqual(transposedCofactorsMatrix, MatrixCalc.transpose(cofactorsMatrix)));
    }

    @Test
    void detTriangularTest() {
        /*
         * Пример взят из Киркинский А.С. "Основы линейной алгебры"
         */

        var matrix = new double[][]{
                {22.0d, 5.0d, 5.0d, 5.0d},
                {0.0d, 2.0d, 0.0d, 0.0d},
                {0.0d, 0.0d, 2.0d, 0.0d},
                {0.0d, 0.0d, 0.0d, 2.0d}};

        assertTrue(Validation.isSquareMatrix(matrix));
        assertEquals(detTriangExample1(matrix.length), detTriangular(matrix));

        var matrix2 = new double[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix2[i][j] = Math.min(i + 1, j + 1);
            }
        }
        assertEquals(detTriangular(RowEchelonFormUtil.toRowEchelonForm(matrix2)), detTriangular(RowEchelonFormUtil.toREF(matrix2)));
        assertEquals(det(RowEchelonFormUtil.toRowEchelonForm(matrix2)), detTriangular(RowEchelonFormUtil.toREF(matrix2)));
        assertEquals(1.0d, detTriangular(RowEchelonFormUtil.toREF(matrix2)));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix2[i][j] = Math.max(i + 1, j + 1);
            }
        }

        assertEquals(detTriangular(RowEchelonFormUtil.toRowEchelonForm(matrix2)), detTriangular(RowEchelonFormUtil.toREF(matrix2)), EPS);
        assertEquals(det(RowEchelonFormUtil.toRowEchelonForm(matrix2)), detTriangular(RowEchelonFormUtil.toREF(matrix2)), EPS);
        assertEquals(5.0d, detTriangular(RowEchelonFormUtil.toREF(matrix2)));
    }

    private double detTriangExample1(int n) {
        return (5.0d * n + 2.0d) * Math.pow(2.0d, ((double) n - 1.0d));
    }
}
