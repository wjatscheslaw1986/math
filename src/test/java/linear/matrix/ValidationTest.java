/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.matrix;

import linear.MatrixGenerator;
import org.junit.jupiter.api.Test;

import static linear.matrix.Validation.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link linear.matrix.Validation} class.
 *
 * @author Viacheslav Mikhailov
 */
public class ValidationTest {

    @Test
    void canMultiplyTest() {
        double[][] matrix1 = {{3, -4}, {2, 5}};
        double[][] matrix2 = {{-1, 5}, {-2, -3}};
        assertTrue(canMultiply(matrix1, matrix2));
        double[][] matrix3 = {{-1, 5}};
        assertFalse(canMultiply(matrix1, matrix3));
    }

    @Test
    void isEqualDimensionsTest() {
        double[][] matrix1 = {{3, -4}, {2, 5}};
        double[][] matrix2 = {{-1, 5}, {-2, -3}};
        assertTrue(isEqualDimensions(matrix1, matrix2));
        double[][] matrix3 = {{-1, 5}};
        assertFalse(isEqualDimensions(matrix1, matrix3));

        // Using this lib, a user must check beforehand that they aren't doing anything pointless.
        assertThrows(IllegalArgumentException.class, () ->
                isEqualDimensions(
                        MatrixGenerator.generateRandomDoubleMatrix(0, 1),
                        MatrixGenerator.generateRandomDoubleMatrix(0, 1)));
        assertThrows(IllegalArgumentException.class, () ->
                isEqualDimensions(
                        MatrixGenerator.generateRandomDoubleMatrix(1, 0),
                        MatrixGenerator.generateRandomDoubleMatrix(1, 0)));

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (j == i)
                    assertTrue(isEqualDimensions(
                            MatrixGenerator.generateRandomDoubleMatrix(i, j),
                            MatrixGenerator.generateRandomDoubleMatrix(j, i)));
                else
                    assertFalse(
                            isEqualDimensions(
                                    MatrixGenerator.generateRandomDoubleMatrix(i, j),
                                    MatrixGenerator.generateRandomDoubleMatrix(j, i)));
            }
        }
    }

    @Test
    void isSquareMatrixTest() {
        assertThrows(IllegalArgumentException.class, () ->
                isSquareMatrix(MatrixGenerator.generateRandomDoubleMatrix(0, 1)));
        assertThrows(IllegalArgumentException.class, () ->
                isSquareMatrix(MatrixGenerator.generateRandomDoubleMatrix(1, 0)));
        for (int i = 1; i < 10; i++) {
            var matrix = MatrixGenerator.generateRandomDoubleMatrix(i, i);
            assertTrue(isSquareMatrix(matrix));
            for (int j = 1; j < 10; j++) {
                if (j == i) continue;
                assertFalse(
                        isSquareMatrix(
                                MatrixGenerator.generateRandomDoubleMatrix(i, j)));
            }
        }
    }

    @Test
    void isMatrixTest() {
        double[][] notMatrix1 = {{3, -4}, {2}};
        double[][] notMatrix2 = {{-1}, {-2, -3}};
        assertFalse(isMatrix(notMatrix1));
        assertFalse(isMatrix(notMatrix2));
        assertFalse(isMatrix(MatrixGenerator.generateRandomDoubleMatrix(1, 0)));
        assertFalse(isMatrix(MatrixGenerator.generateRandomDoubleMatrix(0, 1)));
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                assertTrue(isMatrix(MatrixGenerator.generateRandomDoubleMatrix(i, j)));
            }
        }
    }

    @Test
    void isCramerTest() {
        for (int i = 1; i < 10; i++) {
            var matrix = MatrixGenerator.generateRandomDoubleMatrix(i, i);
            var det = MatrixCalc.det(matrix);
            if (det == .0d)
                assertFalse(isCramer(matrix));
            else
                assertTrue(isCramer(matrix));
        }
    }
}
