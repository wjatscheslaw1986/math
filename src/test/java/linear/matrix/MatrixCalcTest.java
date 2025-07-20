/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.matrix;

import linear.MatrixGenerator;
import linear.matrix.exception.MatrixException;
import linear.spatial.Vector;
import linear.spatial.VectorCalc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static linear.matrix.MatrixCalc.*;
import static linear.matrix.MatrixUtil.EPS;
import static linear.matrix.RowEchelonFormUtil.*;
import static linear.matrix.Validation.isEqualDimensions;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@linkplain linear.matrix.MatrixCalc} class.
 *
 * @author Viacheslav Mikhailov
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

        /*
         * Определитель произведения матриц всегда равен произведению определителей матриц.
         */

        int i = 7;
        do {
            var matrix1 = MatrixGenerator.generateRandomIntMatrix(i, i);
            var matrix2 = MatrixGenerator.generateRandomIntMatrix(i, i);
            Assertions.assertEquals(det(matrix1) * det(matrix2), det(multiply(matrix1, matrix2)));
        } while (--i > 0);
    }

    /**
     * Даны матрица и вектор, найти произведение вектора на матрицу.
     *
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @Test
    void givenMatrixAndVector_whenMultiply_thenReturnExpectedVector() throws MatrixException {
        var expectedVector = new double[]{-33.0d, -50.0d, -45.0d};
        var matrix = new double[][]{{-8.0d, -12.0d, -11.0d}, {7.0d, 10.0d, 9.0d}, {-6.0d, -9.0d, -8.0d}};
        var vector = new double[][]{{2.0d, 1.0d, 4.0d}};
        var result = MatrixCalc.multiply(vector, matrix);
        assertArrayEquals(expectedVector, result[0]);

        // Умножение вектора на матрицу перехода к другому базису производит тот же вектор, но в другом базисе
        result = MatrixCalc.multiply(Vector.of(2.0d, 1.0d, 4.0d), matrix);
        assertArrayEquals(expectedVector, result[0]);

        // Умножение вектора на обратную матрицу перехода возвращает значения его координат в прежний базис
        result = MatrixCalc.multiply(Vector.of(expectedVector), MatrixCalc.inverse(matrix));
        assertArrayEquals(vector[0], result[0]);
    }

    /**
     * Даны матрицы, найти обратные для каждой из них.
     *
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @Test
    public void givenMatrix_whenIsInvertible_thenTrue() {
        final double[][] a = new double[][]{{6.0d, 4.0d}, {-2.0d, -1.0d}};
        final double[][] b = new double[][]{{3.0f, 4.0f}, {9.0f, 12.0f}};
        final double[][] c = new double[][]{{2.0d, 0.0d, 2.0d}, {-3.0d, 2.0d, 0.0d}, {6.0d, -2.0d, 4.0d}};
        final double[][] a_inv = new double[][]{{-.5d, -2.0d}, {1.0d, 3.0d}};
        final double[][] c_inv = new double[][]{{2.0d, -1.0d, -1.0d}, {3.0d, -1.0d, -1.5d}, {-1.5d, 1.0d, 1.0d}};

        Assertions.assertTrue(Validation.isInvertible(a));
        Assertions.assertDoesNotThrow(() -> inverse(a));
        Assertions.assertTrue(areEqual(inverse(a), a_inv));
        Assertions.assertFalse(Validation.isInvertible(b));
        Assertions.assertThrows(IllegalArgumentException.class, () -> inverse(b));
        Assertions.assertDoesNotThrow(() -> inverse(c));
        Assertions.assertTrue(areEqual(inverse(c), c_inv));
    }

    /**
     * Даны матрицы, найти обратные для каждой из них.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @Test
    public void givenMatrices_whenIsInvertible_thenTrueAndDoesNotThrow() {
        final double[][] a = new double[][]{{6.0d, 4.0d}, {-2.0d, -1.0d}};
        final double[][] b = new double[][]{{3.0f, 4.0f}, {9.0f, 12.0f}};
        final double[][] c = new double[][]{{2.0d, 0.0d, 2.0d}, {-3.0d, 2.0d, 0.0d}, {6.0d, -2.0d, 4.0d}};

        Assertions.assertTrue(Validation.isInvertible(a));
        Assertions.assertDoesNotThrow(() -> inverse(a));
        Assertions.assertFalse(Validation.isInvertible(b));
        Assertions.assertThrows(IllegalArgumentException.class, () -> inverse(b));
        Assertions.assertDoesNotThrow(() -> inverse(c));
    }

    /**
     * Даны матрицы, найти обратные для каждой из них.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @Test
    public void givenMatrices_whenInvert_thenEqualToExpected() {
        final double[][] a = new double[][]{{6.0d, 4.0d}, {-2.0d, -1.0d}};
        final double[][] b = new double[][]{{1.0d, 3.0d, 2.0d}, {2.0d, -2.0d, -5.0d}, {-3.0d, .0d, 4.0d}};
        final double[][] c = new double[][]{{2.0d, 0.0d, 2.0d}, {-3.0d, 2.0d, 0.0d}, {6.0d, -2.0d, 4.0d}};
        final double[][] a_inv = new double[][]{{-.5d, -2.0d}, {1.0d, 3.0d}};
        final double[][] b_inv = new double[][]{{-8.0d, -12.0d, -11.0d}, {7.0d, 10.0d, 9.0d}, {-6.0d, -9.0d, -8.0d}};
        final double[][] c_inv = new double[][]{{2.0d, -1.0d, -1.0d}, {3.0d, -1.0d, -1.5d}, {-1.5d, 1.0d, 1.0d}};

        Assertions.assertTrue(areEqual(inverse(a), a_inv));
        Assertions.assertTrue(areEqual(inverse(b), b_inv));
        Assertions.assertTrue(areEqual(inverse(c), c_inv));
    }

    /**
     * Given a matrix, crop it.
     */
    @Test
    public void given_a_matrix_crop_it() {
        final double[][] matrix0 = new double[][]{
                {0.0d, 2.0d, -4.0d},
                {-1.0d, -4.0d, 5.0d},
                {3.0d, 1.0d, 7.0d},
                {0.0d, 5.0d, -10.0d}};

        final double[][] matrix1 = new double[][]{
                {0.0d, 2.0d, -4.0d},
                {-1.0d, -4.0d, 5.0d},
                {3.0d, 1.0d, 7.0d}};
        final double[][] matrix2 = new double[][]{
                {-1.0d, -4.0d, 5.0d},
                {3.0d, 1.0d, 7.0d},
                {0.0d, 5.0d, -10.0d}};

        assertTrue(areEqual(matrix1, crop(matrix0, 0, 0, 3)));
        assertTrue(areEqual(matrix2, crop(matrix0, 1, 0, 3)));
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

        matrix = new double[][]{{5.0d, -2.0d, -3.0d, 4.0d, 7.0d}, {0.0d, 0.0d, -10.0d, 2.0d, 8.0d},
                {0.0d, 0.0d, 5.0d, -1.0d, -4.0d}};
        assertEquals(2, rank(matrix));
        assertEquals(2, rankByMinors(matrix));
    }

    /**
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @Test
    public void givenTrapezoidFormMatrix_whenCheckIsTrapezoid_thenTrue() {
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
        assertTrue(VectorCalc.areEqual(vector1, vector1));
        assertFalse(VectorCalc.areEqual(vector1, vector2));
        assertTrue(VectorCalc.areEqual(vector2, vector2));
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
    void detTriangularTest() throws MatrixException {
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
