package linear.spatial;

import linear.equation.LinearEquationSystemUtil;
import linear.matrix.exception.MatrixException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static linear.equation.SolutionsCount.INFINITE;
import static linear.equation.SolutionsCount.SINGLE;
import static linear.spatial.VectorUtil.toLinearEquationSystem;
import static linear.spatial.VectorUtil.transformToBasis;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link VectorUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class VectorUtilTest {

    /**
     * Are the given elements x1 = (1,1,1), x2 = (1,2,3) and x3 = (1,4,5) of some 3D space linearly dependent?
     *
     * By definition, linear independence means a1x1 + a2x2 + a3x3 = 0 if at least one of a's is non-zero.
     *
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая Геометрия" - 2006
     *
     * @throws MatrixException if failed to solve linear equations system
     */
    @Test
    void givenBasisVectors_whechCheckIsIndependent_thenTrue() throws MatrixException {
        Vector[] vectors = new Vector[]{
                Vector.of(1, 1, 1),
                Vector.of(1, 2, 3),
                Vector.of(1, 4, 5)
        };
        assertTrue(VectorUtil.isBasis(vectors));

      var solution = LinearEquationSystemUtil.resolveUsingJordanGaussMethod(toLinearEquationSystem(Vector.of(new double[vectors[0].coordinates().length]), vectors));
      assertEquals(SINGLE, solution.solutionsCount());
    }

    /**
     * Are the given elements x1 = (1,2,3), x2 = (3,5,1) and x3 = (5,9,7) of some 3D space linearly dependent?
     *
     * By definition, linear independence means a1x1 + a2x2 + a3x3 = 0 if at least one of a's is non-zero.
     *
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая Геометрия" - 2006
     *
     * @throws MatrixException if failed to solve linear equations system
     */
    @Test
    void givenNonBasisVectors_whechCheckIsIndependent_thenFalse() throws MatrixException {
        Vector[] vectors = new Vector[]{
                Vector.of(1, 2, 3),
                Vector.of(3, 5, 1),
                Vector.of(5, 9, 7)
        };
        assertFalse(VectorUtil.isBasis(vectors));

        var solution = LinearEquationSystemUtil.resolveUsingJordanGaussMethod(toLinearEquationSystem(Vector.of(new double[vectors[0].coordinates().length]), vectors));
        assertEquals(INFINITE, solution.solutionsCount());
    }

    @Test
    void givenVectorAndBasis_whenToLinearEquationSystem_thenGetExpected2DMatrix() throws MatrixException {
        Vector[] vectors = new Vector[]{
                Vector.of(3, -5),
                Vector.of(5, -8)
        };
        var vector = Vector.of(1, -3);

        final double[][] expected = new double[][]{
                {3.0d, 5.0d, 1.0d},
                {-5.0d, -8.0d, -3.0d}
        };

        var result = toLinearEquationSystem(vector, vectors);
        assertArrayEquals(expected, result);
    }

    @Test
    void givenVectorAndAnotherBasis_whenTransformToBasis_thenExpectedVectorExpressedInNewBasis() {
        Vector[] basis = new Vector[]{
                Vector.of(3, -5),
                Vector.of(5, -8)
        };
        var vector = Vector.of(1, -3);
        var expectedVector = Vector.of(7, -4);
        assertEquals(expectedVector, transformToBasis(vector, basis));

        basis = new Vector[]{
                Vector.of(1, 3, 2),
                Vector.of(2, -2, -5),
                Vector.of(-3, 0, 4)
        };

        vector = Vector.of(2, 1, 4);
        expectedVector = Vector.of(-33, -50, -45);

        assertEquals(expectedVector, transformToBasis(vector, basis));
    }

    @Test
    void givenMatrix_whenEigenvectors_thenGetExpectedVectors() throws MatrixException {
        var matrix = new double[][]{
                {17, 6},
                {6, 8}
        };

        List<Vector> eigenvectors = VectorCalc.eigenvectors(matrix);
        assertEquals(Vector.of(2.0d, 1.0d), eigenvectors.getFirst());
        assertEquals(Vector.of(-.5d, 1.0d), eigenvectors.getLast());
    }
}
