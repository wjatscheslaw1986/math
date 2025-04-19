/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.matrix;

import org.junit.jupiter.api.Test;

import static linear.matrix.MatrixCalc.areEqual;
import static linear.matrix.MatrixUtil.copy;
import static linear.matrix.MatrixUtil.removeMarginalColumn;
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
}
