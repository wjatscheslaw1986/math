/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
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

public class MatrixCalcTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s.class", MatrixCalcTest.class);
    }

    @Test
    public void equals() {
        double[][] a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}, {0.0d, 2.0d, -7.0d}};
        double[][] b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}, {0.0f, 2.0f, -7.0f}};
        Assertions.assertTrue(MatrixCalc.equals(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertTrue(MatrixCalc.equals(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {3, -7}};
        Assertions.assertFalse(MatrixCalc.equals(a1, b1));
        a1 = new double[][]{{2.0d, -1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertFalse(MatrixCalc.equals(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{-2, 1}, {2, -7}};
        Assertions.assertFalse(MatrixCalc.equals(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {2.0d, 7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertFalse(MatrixCalc.equals(a1, b1));
        a1 = new double[][]{{2.0d, 3.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertFalse(MatrixCalc.equals(a1, b1));
        a1 = new double[][]{{2.1d, 1.0d}, {2.0d, -7.0d}};
        b1 = new double[][]{{2, 1}, {2, -7}};
        Assertions.assertFalse(MatrixCalc.equals(a1, b1));
    }

    @Test
    public void equalDimensions() {
        double[][] a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}, {0.0d, 2.0d, -7.0d}};
        double[][] b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}, {0.0f, 2.0f, -7.0f}};
        Assertions.assertTrue(MatrixCalc.equalDimensions(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}, {0.0d, 2.0d, -7.0d}};
        b1 = new double[][]{{2.0f, 1.0f}, {1.0f, -1.0f}, {0.0f, 2.0f}};
        Assertions.assertFalse(MatrixCalc.equalDimensions(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d}, {-1.0d, 7.0d}, {0.0d, -7.0d}};
        b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}, {0.0f, 2.0f, -7.0f}};
        Assertions.assertFalse(MatrixCalc.equalDimensions(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}};
        b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}, {0.0f, 2.0f, -7.0f}};
        Assertions.assertFalse(MatrixCalc.equalDimensions(a1, b1));
        a1 = new double[][]{{2.0d, 1.0d, 3.0d}, {1.0d, -1.0d, 7.0d}, {0.0f, 2.0f, -7.0f}};
        b1 = new double[][]{{2.0f, 1.0f, 3.0f}, {1.0f, -1.0f, 7.0f}};
        Assertions.assertFalse(MatrixCalc.equalDimensions(a1, b1));
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
        Assertions.assertDoesNotThrow(() -> MatrixCalc.multiplyMatrices(A, B));
        Assertions.assertTrue(MatrixCalc.equals(result, MatrixCalc.multiplyMatrices(A, B)));

        /*
         * Перемножение матриц требует, чтобы количество колонок матрицы слева равнялось
         * количеству строк матрицы справа.
         */
        final double[][] b2 = new double[][]{{1.0f, 2.0f, 0.0f}, {3.0f, 1.0f, 2.0f}};
        Assertions.assertThrows(MatrixException.class, () -> MatrixCalc.multiplyMatrices(A, b2));
    }

    /**
     * Даны матрицы, найти обратные для каждой из них.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     * 
     * @throws MatrixException в случае неправильных матриц
     */
    @Test
    public void reverse() throws MatrixException {
        final double[][] a = new double[][]{{6.0d, 4.0d}, {-2.0d, -1.0d}};
        final double[][] b = new double[][]{{3.0f, 4.0f}, {9.0f, 12.0f}};
        final double[][] c = new double[][]{{2.0d, 0.0d, 2.0d}, {-3.0d, 2.0d, 0.0d}, {6.0d, -2.0d, 4.0d}};

        final double[][] a_rev = new double[][]{{-.5d, -2.0d}, {1.0d, 3.0d}};
        final double[][] c_rev = new double[][]{{2.0d, -1.0d, -1.0d}, {3.0d, -1.0d, -1.5d}, {-1.5d, 1.0d, 1.0d}};

        Assertions.assertDoesNotThrow(() -> MatrixCalc.reverse(a));
        Assertions.assertTrue(MatrixCalc.equals(MatrixCalc.reverse(a), a_rev));

        /*
         * У матрицы с определителем == 0 не может быть обратной.
         */
        Assertions.assertThrows(MatrixException.class, () -> MatrixCalc.reverse(b));

        Assertions.assertDoesNotThrow(() -> MatrixCalc.reverse(c));
        Assertions.assertTrue(MatrixCalc.equals(MatrixCalc.reverse(c), c_rev));

        // try {
        // DoubleMatrixCalc.reverse(b);
        // } catch(MatrixException e) {
        // System.err.println(e.getLocalizedMessage());
        // }

    }

    /**
     * Given a matrix, calculate it's submatrix.
     * 
     * @throws MatrixException in case of a malformed matrix
     */
    @Test
    public void squareSubmatrix() throws MatrixException {
        final double[][] matrix0 = new double[][]{{0.0d, 2.0d, -4.0d}, {-1.0d, -4.0d, 5.0d}, {3.0d, 1.0d, 7.0d},
                                                  {0.0d, 5.0d, -10.0d}};

        final double[][] matrix1 = new double[][]{{0.0d, 2.0d, -4.0d}, {-1.0d, -4.0d, 5.0d}, {3.0d, 1.0d, 7.0d}};

        final double[][] matrix2 = new double[][]{{-1.0d, -4.0d, 5.0d}, {3.0d, 1.0d, 7.0d}, {0.0d, 5.0d, -10.0d}};

        // System.out.println(DoubleMatrixCalc.print(DoubleMatrixCalc.squareSubmatrix(matrix0, 0, 0, 3)));
        // System.out.println(DoubleMatrixCalc.print(DoubleMatrixCalc.squareSubmatrix(matrix0, 1, 0, 3)));
        assertTrue(MatrixCalc.equals(matrix1, MatrixCalc.squareSubmatrix(matrix0, 0, 0, 3)));
        assertTrue(MatrixCalc.equals(matrix2, MatrixCalc.squareSubmatrix(matrix0, 1, 0, 3)));
    }

    /**
     * Given a matrix, calculate it's rank.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     * 
     * @throws MatrixException in case of a malformed matrix
     */
    @SuppressWarnings("deprecation")
    @Test
    public void rank() throws MatrixException {
        final double[][] matrix = new double[][]{{0.0d, 2.0d, -4.0d}, {-1.0d, -4.0d, 5.0d}, {3.0d, 1.0d, 7.0d},
                                                 {0.0d, 5.0d, -10.0d}};

        assertEquals(2, MatrixCalc.rank(matrix));
        assertEquals(2, MatrixCalc.rankByMinors(matrix));
    }

    /**
     * Given a matrix, convert it to a trapezoidal matrix. TODO
     * 
     * @throws MatrixException in case of a malformed matrix
     */
    @Test
    public void tryTrapezoid() throws MatrixException {
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
     * 
     * @throws MatrixException in case of a malformed matrix
     */
    @Test
    public void isTrapezoid() throws MatrixException {
        final double[][] matrix = new double[][]{{1.0d, -3.0d, -5.0d, -3.0d}, {0.0d, 4.0d, 11.0d, 7.0d},
                                                 {0.0d, 0.0d, 2.0d, 0.0d}};
        assertTrue(MatrixCalc.isTrapezoidForm(matrix));
    }

    /**
     * Given a matrix, check if it is a row echelon form.
     * 
     * @throws MatrixException in case of a malformed matrix
     */
    @Test
    public void isRowEchelonForm() throws MatrixException {
        double[][] matrix = new double[][]{{1.0d, -3.0d, -5.0d, -3.0d}, {0.0d, 4.0d, 11.0d, 7.0d},
                                           {0.0d, 0.0d, 2.0d, 0.0d}};
        var rowEchelonMatrix = MatrixCalc.toRowEchelonForm(matrix);
        assertTrue(MatrixCalc.isRowEchelonForm(matrix));
        assertTrue(MatrixCalc.isRowEchelonForm(rowEchelonMatrix));

        // In general, row echelon form (non-reduced) is not unique. But in the case when matrix is in REF already, it
        // should not change.
        assertTrue(MatrixCalc.equals(rowEchelonMatrix, matrix));

        matrix = new double[][]{{-5.4d, -3.8d, -5.42d}};
        rowEchelonMatrix = MatrixCalc.toRowEchelonForm(matrix);
        assertTrue(MatrixCalc.isRowEchelonForm(matrix));
        assertTrue(MatrixCalc.isRowEchelonForm(rowEchelonMatrix));
        assertTrue(MatrixCalc.equals(rowEchelonMatrix, matrix));

        matrix = new double[][]{{1.1883620478410215d, 4.084346689561142d, 0.175702923175848d, -6.932205740519737}};
        rowEchelonMatrix = MatrixCalc.toRowEchelonForm(matrix);
        assertTrue(MatrixCalc.isRowEchelonForm(matrix));
        assertTrue(MatrixCalc.isRowEchelonForm(rowEchelonMatrix));
        assertTrue(MatrixCalc.equals(rowEchelonMatrix, matrix));

        matrix = new double[][]{{-3.4408949597421845d, 2.94181514630203d}, {2.490922186823001d, -8.99332700307957d},
                                {4.740206117048821d, -6.958867337177139},
                                {8.154390360529227d, -1.0613990534676798}};
        rowEchelonMatrix = new double[][]{{8.154390360529227d, 0.0d}, {0.0d, 2.94181514630203d}, {0.0d, 0.0d},
                                          {0.0d, 0.0d}};
        var rowEchelonMatrix_ = MatrixCalc.toRowEchelonForm(matrix);
        assertFalse(MatrixCalc.isRowEchelonForm(matrix));
        assertTrue(MatrixCalc.isRowEchelonForm(rowEchelonMatrix));
        assertTrue(MatrixCalc.isRowEchelonForm(rowEchelonMatrix_));

        // The row echelon form (non-reduced) is not unique.
        assertFalse(MatrixCalc.equals(rowEchelonMatrix, rowEchelonMatrix_));

        matrix = new double[][]{{1.0096634218430616d, -8.772286757703684d, -0.44083720521676284d, 0.8588440971684381d},
                                {6.418590743167737d, -6.420896947702297d, 3.5253331532074323d, 4.864061213084703d},
                                {4.846333023426332d, -8.178851479847493d, -7.092716530960487d, -4.478958333291305d},
                                {-1.2029012827902505d, 5.915048574621862d, 5.584530663132492d, 2.962818038754156d}};
        rowEchelonMatrix = MatrixCalc.toRowEchelonForm(matrix);
        assertFalse(MatrixCalc.isRowEchelonForm(matrix));
        assertTrue(MatrixCalc.isRowEchelonForm(rowEchelonMatrix));
    }
}
