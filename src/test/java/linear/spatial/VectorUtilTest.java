package linear.spatial;

import algebra.Complex;
import approximation.RoundingUtil;
import linear.equation.LinearEquationSystemUtil;
import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.exception.MatrixException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static linear.equation.SolutionsCount.INFINITE;
import static linear.equation.SolutionsCount.SINGLE;
import static linear.spatial.VectorUtil.*;
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
    void givenMatrix_whenEigenvectors_thenGetExpectedVectors1() throws MatrixException {
        var matrix = new double[][]{
                {16, 45},
                {-6, -17}
        };
        var eigenvalues = VectorCalc.eigenvalues(matrix);
        assertEquals(1.0d, eigenvalues.get(0).real());
        assertEquals(-2.0d, eigenvalues.get(1).real());
        List<Eigenvector> eigenvectors = VectorCalc.eigenvectors(matrix);

        matrix = new double[][]{
                {0, -4, -4},
                {2, 0, 2},
                {0, 6, 4},
        };

        //TODO assertions
        eigenvalues = VectorCalc.eigenvalues(matrix);
        eigenvectors = VectorCalc.eigenvectors(matrix);
    }

    @Test
    void givenMatrix_whenEigenvectors_thenGetExpectedVectors() throws MatrixException {
        var matrix = new double[][]{
                {17, 6},
                {6, 8}
        };

        List<Eigenvector> eigenvectors = VectorCalc.eigenvectors(matrix);
        assertEquals(Vector.of(2.0d, 1.0d), eigenvectors.getFirst().eigenvector());
        assertEquals(Vector.of(-.5d, 1.0d), eigenvectors.getLast().eigenvector());

        matrix = new double[][] {
                {1, -7, -1},
                {-3, -9, -3},
                {5, 25, 7}
        };

        var eigenvalues = VectorCalc.eigenvalues(matrix);
        assertEquals(2.0d, eigenvalues.get(0).real());
        assertEquals(-3.0d, eigenvalues.get(1).real());
        assertEquals(.0d, eigenvalues.get(2).real());

        eigenvectors = VectorCalc.eigenvectors(matrix);

        var expectedTransformationMatrixInEigenvectorBasis = new double[][] {
                {2, 0, 0},
                {0, -3, 0},
                {0, 0, 0},

        };
        var transformationMatrixInEigenvectorBasis = VectorUtil.toDiagonalMatrix(eigenvalues.stream().mapToDouble(Complex::real).toArray());
        assertTrue(MatrixCalc.areEqual(expectedTransformationMatrixInEigenvectorBasis, transformationMatrixInEigenvectorBasis));

        var expectedTransitionToEigenvectorBasisMatrix = new double[][] {
                {-1.0d, RoundingUtil.roundToNDecimals(-(1.0d/3.0d), 12), -(2.0d/5.0d)},
                {0.0d, RoundingUtil.roundToNDecimals(-(1.0d/3.0d), 12), -(1.0d/5.0d)},
                {1.0d, 1.0d, 1.0d},
        };
        var transitionToEigenvectorBasisMatrix = VectorUtil.toMatrix(true, eigenvectors.toArray(new Eigenvector[0]));
        assertTrue(MatrixCalc.areEqual(expectedTransitionToEigenvectorBasisMatrix, transitionToEigenvectorBasisMatrix));

        // Check that the equation S_inverse*A_a*S = A_b holds

        var invertedSByAByS = MatrixCalc.multiply(
                MatrixCalc.multiply(
                        MatrixCalc.inverse(transitionToEigenvectorBasisMatrix),
                        matrix),
                transitionToEigenvectorBasisMatrix);
        MatrixUtil.eliminateEpsilon(invertedSByAByS);
        assertTrue(MatrixCalc.areEqual(transformationMatrixInEigenvectorBasis,invertedSByAByS));

        matrix = new double[][]{
                {0, 1},
                {1, 1}
        };

        var mult = MatrixCalc.multiply(matrix, MatrixCalc.multiply(matrix, MatrixCalc.multiply(matrix, MatrixCalc.multiply(matrix, MatrixCalc.multiply(matrix, matrix)))));

        eigenvectors = VectorCalc.eigenvectors(mult);
        assertEquals(Vector.of(-RoundingUtil.roundToNDecimals((1.0d - Math.sqrt(5d)) / 2d, 11), 1.0d), eigenvectors.getFirst().eigenvector());
        assertEquals(Vector.of(-RoundingUtil.roundToNDecimals((1.0d + Math.sqrt(5d)) / 2d, 11), 1.0d), eigenvectors.getLast().eigenvector());

        System.out.println(MatrixUtil.print(MatrixCalc.multiply(matrix, MatrixCalc.multiply(matrix, MatrixCalc.multiply(matrix, MatrixCalc.multiply(matrix, MatrixCalc.multiply(matrix, matrix)))))));

        var mult2 = MatrixCalc.multiply(
                VectorUtil.toMatrix(true, eigenvectors.getFirst().eigenvector(), eigenvectors.getLast().eigenvector()),
                matrix);

        System.out.println(MatrixUtil.print(mult2));

        var mult3 = MatrixCalc.multiply(
                new double[][]{
                        {2, 2},
                        {RoundingUtil.roundToNDecimals(1.0d + Math.sqrt(5d), 11), RoundingUtil.roundToNDecimals(1.0d - Math.sqrt(5d), 11)}
                },
                new double[][]{
                        {0, 1},
                        {1, 1}
                }
        );

//        -21.66563146
        System.out.println(MatrixUtil.print(MatrixCalc.multiply(mult3, MatrixCalc.multiply(mult3, mult3))));
        System.out.println(MatrixUtil.print(MatrixCalc.multiply(mult3, MatrixCalc.multiply(mult3, MatrixCalc.multiply(mult3, MatrixCalc.multiply(mult3, mult3))))));
    }
}
