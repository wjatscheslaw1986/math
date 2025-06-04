/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.matrix;

import org.junit.jupiter.api.Test;

import static linear.matrix.MatrixCalc.areEqual;
import static linear.matrix.MatrixUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@linkplain linear.matrix.MatrixUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class MatrixUtilTest {

    @Test
    void removeMarginalColumnTest() {
        final double[][] matrix = new double[][]{
                {1.0d, -3.0d, -5.0d, -3.0d},
                {0.0d, 4.0d, 11.0d, 7.0d},
                {0.0d, 0.0d, 2.0d, 0.0d}};
        final double[][] matrixWithLeftMarginalColumnRemoved
                = new double[][]{
                {-3.0d, -5.0d, -3.0d},
                {4.0d, 11.0d, 7.0d},
                {0.0d, 2.0d, 0.0d}
        };
        final double[][] matrixWithRightMarginalColumnRemoved
                = new double[][]{
                {1.0d, -3.0d, -5.0d},
                {0.0d, 4.0d, 11.0d},
                {0.0d, 0.0d, 2.0d}
        };
        assertTrue(areEqual(matrixWithLeftMarginalColumnRemoved, removeMarginalColumn(matrix, true)));
        assertFalse(areEqual(matrixWithRightMarginalColumnRemoved, removeMarginalColumn(matrix, true)));
        assertTrue(areEqual(matrixWithRightMarginalColumnRemoved, removeMarginalColumn(matrix, false)));
        assertFalse(areEqual(matrixWithLeftMarginalColumnRemoved, removeMarginalColumn(matrix, false)));
    }

    @Test
    void given_matrix_copy_it_assert_different_objects_same_contents() {
        final double[][] givenMatrix = new double[][]{
                {1.0d, -3.0d, -5.0d},
                {0.0d, 4.0d, 11.0d},
                {0.0d, 0.0d, 2.0d}
        };
        final double[][] copyMatrix = copy(givenMatrix);
        assertNotEquals(givenMatrix, copyMatrix);
        int i = 0;
        for (var row : givenMatrix) {
            assertArrayEquals(row, copyMatrix[i++]);
        }
    }

    @Test
    void given_matrix_extract_one_column_by_index() {
        final double[][] givenMatrix = new double[][]{
                {1.0d, -3.0d, -5.0d},
                {0.0d, 4.0d, 11.0d},
                {0.0d, 0.0d, 2.0d}
        };

        final double[] col1 = {1.0d, .0d, .0d};
        final double[] col2 = {-3.0d, 4.0d, .0d};
        final double[] col3 = {-5.0d, 11.0d, 2.0d};

        assertArrayEquals(col1, MatrixUtil.getColumn(givenMatrix, 0));
        assertArrayEquals(col2, MatrixUtil.getColumn(givenMatrix, 1));
        assertArrayEquals(col3, MatrixUtil.getColumn(givenMatrix, 2));
    }

    @Test
    void given_matrix_remove_all_zeroes_rows() {
        final double[][] givenMatrix = new double[][]{
                {1.0d, -3.0d, -5.0d},
                {0.0d, 0.0d, 0.0d},
                {0.0d, 4.0d, 11.0d},
                {0.0d, 0.0d, 2.0d},
                {0.0d, 0.0d, 0.0d},
                {0.0d, 0.0d, 0.0d},
                {0.0d, 0.0d, 0.0d}
        };
        final double[][] expectedResult = new double[][]{
                {1.0d, -3.0d, -5.0d},
                {0.0d, 4.0d, 11.0d},
                {0.0d, 0.0d, 2.0d}
        };
        var result = removeAllZeroesRows(givenMatrix);
        assertEquals(3, result.length);
        for (int i = 0; i < result.length; i++) {
            assertArrayEquals(result[i], expectedResult[i]);
        }
        assertTrue(areEqual(expectedResult, result));
    }

    @Test
    void given_matrix_remove_nth_row() {
        final double[][] given = new double[][]{
                {1.0d, -3.0d, -5.0d, -3.0d},
                {0.0d, 4.0d, 11.0d, 7.0d},
                {0.0d, 0.0d, 2.0d, 0.0d}};
        final double[][] expectedResult1 = new double[][]{
                {1.0d, -3.0d, -5.0d, -3.0d},
                {0.0d, 4.0d, 11.0d, 7.0d}};
        final double[][] expectedResult2 = new double[][]{
                {1.0d, -3.0d, -5.0d, -3.0d},
                {0.0d, 0.0d, 2.0d, 0.0d}};
        final double[][] expectedResult3 = new double[][]{
                {0.0d, 4.0d, 11.0d, 7.0d},
                {0.0d, 0.0d, 2.0d, 0.0d}};
        assertTrue(areEqual(expectedResult3, MatrixUtil.removeNthRow(given, 1)));
        assertTrue(areEqual(expectedResult2, MatrixUtil.removeNthRow(given, 2)));
        assertTrue(areEqual(expectedResult1, MatrixUtil.removeNthRow(given, 3)));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MatrixUtil.removeNthRow(given, 0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MatrixUtil.removeNthRow(given, 4));
    }

    @Test
    void given_matrix_remove_nth_column() {
        final double[][] given = new double[][]{
                {1.0d, -3.0d, -5.0d, -3.0d},
                {0.0d, 4.0d, 11.0d, 7.0d},
                {0.0d, 0.0d, 2.0d, 0.0d}};
        final double[][] expectedResult1 = new double[][]{
                {-3.0d, -5.0d, -3.0d},
                {4.0d, 11.0d, 7.0d},
                {0.0d, 2.0d, 0.0d}};
        final double[][] expectedResult2 = new double[][]{
                {1.0d, -5.0d, -3.0d},
                {0.0d, 11.0d, 7.0d},
                {0.0d, 2.0d, 0.0d}};
        final double[][] expectedResult3 = new double[][]{
                {1.0d, -3.0d, -3.0d},
                {0.0d, 4.0d, 7.0d},
                {0.0d, 0.0d, 0.0d}};
        final double[][] expectedResult4 = new double[][]{
                {1.0d, -3.0d, -5.0d},
                {0.0d, 4.0d, 11.0d},
                {0.0d, 0.0d, 2.0d}};
        assertTrue(areEqual(expectedResult1, MatrixUtil.removeNthColumn(given, 1)));
        assertTrue(areEqual(expectedResult2, MatrixUtil.removeNthColumn(given, 2)));
        assertTrue(areEqual(expectedResult3, MatrixUtil.removeNthColumn(given, 3)));
        assertTrue(areEqual(expectedResult4, MatrixUtil.removeNthColumn(given, 4)));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MatrixUtil.removeNthColumn(given, 0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> MatrixUtil.removeNthColumn(given, 5));
    }

    @Test
    void given_matrix_substitute_one_column_for_another_one() {
        var matrix = new double[][]{
                {1, 2, 3, 4, 0},
                {3, -5, 1, -2, 0},
                {4, -3, 4, 2, 0}
        };
        var column = new double[]{55,44,33};
        var expectedResult1 = new double[][]{
                {55, 2, 3, 4, 0},
                {44, -5, 1, -2, 0},
                {33, -3, 4, 2, 0}
        };
        var expectedResult2 = new double[][]{
                {1, 55, 3, 4, 0},
                {3, 44, 1, -2, 0},
                {4, 33, 4, 2, 0}
        };
        var expectedResult3 = new double[][]{
                {1, 2, 55, 4, 0},
                {3, -5, 44, -2, 0},
                {4, -3, 33, 2, 0}
        };
        var expectedResult4 = new double[][]{
                {1, 2, 3, 55, 0},
                {3, -5,1, 44, 0},
                {4, -3,4, 33, 0}
        };
        var expectedResult5 = new double[][]{
                {1, 2, 3, 4,  55 },
                {3, -5, 1, -2, 44},
                {4, -3, 4, 2, 33}
        };
        assertTrue(MatrixCalc.areEqual(expectedResult1, substituteColumn(matrix, column, 0)));
        assertTrue(MatrixCalc.areEqual(expectedResult2, substituteColumn(matrix, column, 1)));
        assertTrue(MatrixCalc.areEqual(expectedResult3, substituteColumn(matrix, column, 2)));
        assertTrue(MatrixCalc.areEqual(expectedResult4, substituteColumn(matrix, column, 3)));
        assertTrue(MatrixCalc.areEqual(expectedResult5, substituteColumn(matrix, column, 4)));
    }
}
