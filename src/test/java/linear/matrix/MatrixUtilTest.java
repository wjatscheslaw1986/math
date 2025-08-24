/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.matrix;

import linear.matrix.exception.MatrixException;
import org.junit.jupiter.api.Test;

import static approximation.RoundingUtil.roundToNDecimals;
import static linear.matrix.MatrixCalc.areEqual;
import static linear.matrix.MatrixCalc.det;
import static linear.matrix.MatrixUtil.*;
import static linear.matrix.Validation.isInvertible;
import static linear.matrix.Validation.isInvertibleByDeterminant;
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

    @Test
    void givenMatrix_whenLUDecompose_thenExpectedLU() throws MatrixException {
        var given = new double[][]{
                {2, 4, 3, 5},
                {-4, -7, -5, -8},
                {6, 8, 2, 9},
                {4, 9, -2, -14}
        };
        var lu = MatrixUtil.luDecompose(given);

        var expectedL = new double[][]{
                {1.0d, .0d, .0d, .0d},
                {-2.0d, 1.0d, .0d, .0d},
                {3.0d, -4.0d, 1.0d, .0d},
                {2.0d, 1.0d, 3.0d, 1.0d}
        };

        var expectedU = new double[][]{
                {2.0d, 4.0d, 3.0d, 5.0d},
                {.0d, 1.0d, 1.0d, 2.0d},
                {.0d, .0d, -3.0d, 2.0d},
                {.0d, .0d, .0d, -32.0d}
        };

        assertTrue(MatrixCalc.areEqual(expectedL, lu.l()));
        assertTrue(MatrixCalc.areEqual(expectedU, lu.u()));
        assertTrue(MatrixCalc.areEqual(given, MatrixCalc.multiply(lu.l(), lu.u())));

        given = new double[][]{
                {0, 1},
                {1, 0}
        };

        lu = MatrixUtil.luDecompose(given);
        assertTrue(isInvertible(given));
        assertTrue(isInvertibleByDeterminant(given));

//        System.out.println(MatrixUtil.print(lu.l()));
//        System.out.println(MatrixUtil.print(lu.u()));

        given = new double[][]{
                {2, 4, 8},
                {4, 8, 16},
                {1, 1, 1}
        };

        lu = MatrixUtil.luDecompose(given);
        assertFalse(isInvertible(given));
        assertFalse(isInvertibleByDeterminant(given));

//        System.out.println(MatrixUtil.print(lu.l()));
//        System.out.println(MatrixUtil.print(lu.u()));

        given = new double[][]{
                {2, 4, 3, 5},
                {-4, -7, -5, -8},
                {6, 8, 2, 9},
                {4, 9, -2, -14}
        };
        lu = MatrixUtil.luDecompose(given);
//        System.out.println(MatrixUtil.print(lu.l()));
//        System.out.println(MatrixUtil.print(lu.u()));
        assertTrue(MatrixCalc.areEqual(given, MatrixCalc.multiply(lu.l(), lu.u())));
        assertTrue(MatrixCalc.areEqual(MatrixCalc.multiply(MatrixUtil.createIdentityForSize(4), given), MatrixCalc.multiply(lu.l(), lu.u())));
    }

    @Test
    void givenMatrix_whenLUPDecompose_thenExpectedLUP() throws MatrixException {
        var given = new double[][]{
                {2, 4, 3, 5},
                {-4, -7, -5, -8},
                {6, 8, 2, 9},
                {4, 9, -2, -14}
        };
        var lup = MatrixUtil.lupDecompose(given);

        assertTrue(MatrixCalc.areEqual(
                MatrixCalc.multiply(lup.getPermutatedIdentity(), given),
                roundToNDecimals(MatrixCalc.multiply(lup.l(), lup.u()), 10)));

        // Check if holds det(A)=det(P)⋅det(L)⋅det(U)
        assertEquals(det(given), det(lup.getPermutatedIdentity()) * det(lup.l()) * det(lup.u()), 1e-10);

        given = new double[][]{
                {0, 1},
                {1, 0}
        };

        lup = MatrixUtil.lupDecompose(given);
        assertEquals(-1.0d, MatrixCalc.det(given));
        assertTrue(MatrixCalc.areEqual(
                MatrixCalc.multiply(lup.getPermutatedIdentity(), given),
                roundToNDecimals(MatrixCalc.multiply(lup.l(), lup.u()), 10)));

        given = new double[][]{
                {2, 4, 8},
                {4, 8, 16},
                {1, 1, 1}
        };

        double[][] finalGiven = given;
        assertEquals(.0d, MatrixCalc.det(given));
        assertThrows(IllegalArgumentException.class, () -> MatrixUtil.lupDecompose(finalGiven));
    }

    @Test
    void givenMatrix_whenExcludeColumnAndRow_thenExpectedMatrix() {
        var given = new double[][]{
                {2, 4, 8},
                {4, 8, 16},
                {1, 1, 1}
        };

        var expectedExcluded_1_1 = new double[][]{
                {8, 16},
                {1, 1}
        };
        var expectedExcluded_1_2 = new double[][]{
                {4, 16},
                {1, 1}
        };
        var expectedExcluded_1_3 = new double[][]{
                {4, 8},
                {1, 1}
        };
        var expectedExcluded_2_1 = new double[][]{
                {4, 8},
                {1, 1}
        };
        var expectedExcluded_2_2 = new double[][]{
                {2, 8},
                {1, 1}
        };
        var expectedExcluded_2_3 = new double[][]{
                {2, 4},
                {1, 1}
        };
        var expectedExcluded_3_1 = new double[][]{
                {4, 8},
                {8, 16}
        };
        var expectedExcluded_3_2 = new double[][]{
                {2, 8},
                {4, 16}
        };
        var expectedExcluded_3_3 = new double[][]{
                {2, 4},
                {4, 8}
        };

        assertTrue(areEqual(expectedExcluded_1_1, excludeColumnAndRow(given, 1, 1)));
        assertTrue(areEqual(expectedExcluded_1_2, excludeColumnAndRow(given, 1, 2)));
        assertTrue(areEqual(expectedExcluded_1_3, excludeColumnAndRow(given, 1, 3)));
        assertTrue(areEqual(expectedExcluded_2_1, excludeColumnAndRow(given, 2, 1)));
        assertTrue(areEqual(expectedExcluded_2_2, excludeColumnAndRow(given, 2, 2)));
        assertTrue(areEqual(expectedExcluded_2_3, excludeColumnAndRow(given, 2, 3)));
        assertTrue(areEqual(expectedExcluded_3_1, excludeColumnAndRow(given, 3, 1)));
        assertTrue(areEqual(expectedExcluded_3_2, excludeColumnAndRow(given, 3, 2)));
        assertTrue(areEqual(expectedExcluded_3_3, excludeColumnAndRow(given, 3, 3)));
    }
}
