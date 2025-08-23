package linear.spatial;

import linear.matrix.MatrixCalc;
import linear.matrix.exception.MatrixException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link VectorCalc} class.
 *
 * @author Viacheslav Mikhailov
 */
public class VectorCalcTest {

    @Test
    void givenEndPoint_whenLengthEndCoord_thenExpectedLength() {
        double[] endPointCoordinates = new double[]{-2, 2, 1};

        double expectedLength = 3;

        assertEquals(expectedLength, VectorCalc.length(endPointCoordinates));
        assertEquals(expectedLength, VectorCalc.lengthUsingVectorAPI(endPointCoordinates));
        assertEquals(expectedLength, VectorCalc.lengthUsingVectorAPINoMasking(endPointCoordinates));
    }

    @Test
    void givenStartEndPoints_whenLengthStartEndCoord_thenExpectedLength() {
        double[] startPointCoordinates = new double[]{3, -2, 5};
        double[] endPointCoordinates = new double[]{1, 0, 6};

        double expectedLength = 3;

        assertEquals(expectedLength, VectorCalc.length(startPointCoordinates, endPointCoordinates));
        assertEquals(expectedLength, VectorCalc.lengthUsingVectorAPI(startPointCoordinates, endPointCoordinates));
        assertEquals(expectedLength, VectorCalc.lengthUsingVectorAPINoMasking(startPointCoordinates, endPointCoordinates));
    }

    @Test
    void givenStartEndPoints_whenAngles_thenExpectedCosValuesInRadians() {
        double[] startPointCoordinates = new double[]{3, -2, 5};
        double[] endPointCoordinates = new double[]{1, 0, 6};
        double[] expectedAnglesInRadians = new double[]{-2d/3d, 2d/3d, 1d/3d};

        assertArrayEquals(expectedAnglesInRadians, VectorCalc.angles(startPointCoordinates, endPointCoordinates));
    }

    @Test
    void givenVectorAndRealNumber_whenMultiply_thenExpectedVector() {
        double[] vector = new double[]{3, -2, 5};
        double realNumber = 5.0;
        double[] expectedVector = new double[]{15.0d, -10.0d, 25.0d};

        assertArrayEquals(expectedVector, VectorCalc.multiply(vector, realNumber));
        assertArrayEquals(expectedVector, VectorCalc.multiplyUsingVectorAPINoMasking(vector, realNumber));
        assertArrayEquals(expectedVector, VectorCalc.multiplyUsingVectorAPI(vector, realNumber));
    }

    /**
     * <p>
     * Найти образ вектора x = {3, 5, -2} в 3х-мерном пространстве при преобразовании А,
     * которое задано в базисе е1 = (1,0,0), е2 = (0,1,0), е3 = (0,0,1) матрицей.
     * </p>
     * <p>
     * А = {
     *       {2, 1, 7},
     *       {-1, 1, -3},
     *       {-2, -4, 0}
     *     }
     * </p>
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая Геометрия" - 2006
     *
     * @throws MatrixException
     */
    @Test
    void givenVectorAndTransformation_whenTransform_thenExpectedVector() throws MatrixException {
        var givenVector = Vector.of(3, 5, -2);
        var transformation = new double[][] {
                {2, 1, 7},
                {-1, 1, -3},
                {-2, -4, 0},
        };
        var expectedVector = Vector.of(5, 16, 6);
        assertEquals(expectedVector, VectorCalc.transform(givenVector, transformation));
    }

    @Test
    void givenTwoVectors_whenSum_thenExpectedVector() {
        var givenVector1 = Vector.of(3, -4, 1);
        var givenVector2 = Vector.of(2, 0, 4);
        var expectedVector = Vector.of(5, -4, 5);
        assertArrayEquals(expectedVector.coordinates(), VectorCalc.sum(givenVector1.coordinates(), givenVector2.coordinates()));
    }

    @Test
    void givenTwoVectors_whenSumUsingVectorApiWithoutMasking_thenExpectedVector() {
        var givenVector1 = Vector.of(3, -4, 1);
        var givenVector2 = Vector.of(2, 0, 4);
        var expectedVector = Vector.of(5, -4, 5);
        assertArrayEquals(expectedVector.coordinates(), VectorCalc.sumUsingVectorAPINoMasking(givenVector1.coordinates(), givenVector2.coordinates()));
    }

    @Test
    void givenTwoVectors_whenSumUsingVectorApi_thenExpectedVector() {
        var givenVector1 = Vector.of(3, -4, 1);
        var givenVector2 = Vector.of(2, 0, 4);
        var expectedVector = Vector.of(5, -4, 5);
        assertArrayEquals(expectedVector.coordinates(), VectorCalc.sumUsingVectorAPI(givenVector1.coordinates(), givenVector2.coordinates()));
    }

    @Test
    void givenTwoVectors_whenDotProduct_thenExpectedRealNumber() {
        var givenVector1 = Vector.of(3, -4, 1);
        var givenVector2 = Vector.of(2, 0, 4);
        double expectedRealNumber = 10;
        assertEquals(expectedRealNumber, VectorCalc.dot(givenVector1.coordinates(), givenVector2.coordinates()));
        assertEquals(expectedRealNumber, VectorCalc.dotUsingVectorAPINoMasking(givenVector1.coordinates(), givenVector2.coordinates()));
        assertEquals(expectedRealNumber, VectorCalc.dotUsingVectorAPI(givenVector1.coordinates(), givenVector2.coordinates()));
    }

//    @Test
    void notATest() {
        var matrix1 = new double[][] {
                {12, 4, 0},
                {10, 1, 2},
                {-2, 5, -2},
        };
        var matrix2 = new double[][] {
                {2, 12,  0},
                {3, 10,  2},
                {-1, -2,  -2},
        };
        var matrix3 = new double[][] {
                {2, 4, 12,},
                {3, 1, 10,},
                {-1, 5, -2},
        };
        var matrix = new double[][] {
                {2, 4, 0,},
                {3, 1, 2,},
                {-1, 5, -2},
        };
        var det1 = MatrixCalc.det(matrix1);
        var det2 = MatrixCalc.det(matrix2);
        var det3 = MatrixCalc.det(matrix3);
        var det = MatrixCalc.det(matrix);

        var a = det1 / det;
        var b = det2 / det;
        var c = det3 / det;
    }

}
