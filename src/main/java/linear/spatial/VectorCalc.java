/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.spatial;

import algebra.Complex;
import algebra.EquationUtil;
import algebra.Letter;
import algebra.Term;
import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;
import linear.equation.LinearEquationSystemUtil;
import linear.equation.Solution;
import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.exception.MatrixException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static algebra.EquationUtil.distinct;
import static linear.equation.LinearEquationSystemUtil.convertLinearEquationSystem;
import static linear.spatial.VectorUtil.toMatrix;

/**
 * A utility class for calculations of vectors.
 *
 * @author Viacheslav Mikhailov
 */
public final class VectorCalc {

    private static final VectorSpecies<Double> SPECIES = DoubleVector.SPECIES_PREFERRED;

    private VectorCalc() {
        // static context only
    }

    /**
     * Transform the given vector with the given transformation.
     * Both the vector and the transformation must be expressed in the same basis.
     *
     * @param vector the given vector
     * @param transformation the given transformation
     * @return transformed vector in the same basis
     */
    public static Vector transform(final Vector vector, final double[][] transformation) throws MatrixException {
        return Vector.of(MatrixCalc.multiply(vector, transformation)[0]);
    }

    /**
     * Find eigenvectors for the given linear transformation.
     *
     * @param transformationMatrix the given linear transformation
     * @return list of eigenvectors
     */
    public static List<Vector> eigenvectors(final double[][] transformationMatrix) throws MatrixException {
        var eigenvalues = eigenvalues(transformationMatrix);
        List<Vector> eigenvectors = new ArrayList<>();
        for (var eigenvalue : eigenvalues) {
            List<List<Term>> linearEquationSystem = new ArrayList<>();
            for (int j = 0; j < transformationMatrix.length; j++) {
                List<Term> linearEquationTerms = new ArrayList<>();
                for (int k = 0; k < transformationMatrix[j].length; k++) {
                    linearEquationTerms.add(Term.builder()
                            .power(1.0d)
                            .coefficient(transformationMatrix[j][k])
                            .letter(Letter.of("x", k + 1))
                            .value(Double.NaN)
                            .build());
                }
                linearEquationTerms.add(Term.builder()
                        .power(1.0d)
                        .coefficient(-eigenvalue.real())
                        .letter(Letter.of("x", j + 1))
                        .value(Double.NaN)
                        .build());
                linearEquationSystem.add(distinct(linearEquationTerms));
            }
            Solution coords = LinearEquationSystemUtil.resolveUsingJordanGaussMethod(convertLinearEquationSystem(linearEquationSystem));
            coords.basis().forEach(basisVector -> eigenvectors.add(Vector.of(basisVector)));
        }
        return eigenvectors;
    }

    /**
     * The roots of a characteristic polynomial, and only those,
     * are the eigenvalues of a linear transformation.
     *
     * @param matrix the given linear transformation
     * @return the eigenvalues
     */
    public static List<Complex> eigenvalues(final double[][] matrix) {
        return EquationUtil.solvePolynomial(EquationUtil.toCharacteristicPolynomial(matrix)).roots();
    }

    /**
     * The method multiplies the given several vectors (the cross product).
     * The method does not modify the given vectors.
     *
     * @param vector1 - the first given vector
     * @param vectors - the other given vectors
     * @return a new vector which is perpendicular to the given vectors (by the right hand rule)
     */
    public static double[] cross(double[] vector1, double[]... vectors) {
        if (vector1.length != vectors.length + 2)
            throw new IllegalArgumentException("Cross product needs a vector per dimension");
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++)
            result[i] = (i % 2 == 0 ? 1.0d : -1.0d) * MatrixCalc.det(
                    MatrixUtil.excludeColumnAndRow(
                            toMatrix(false, Stream.concat(
                                    Stream.of(new double[vector1.length], vector1),
                                    Arrays.stream(vectors)).map(Vector::of).toArray(Vector[]::new)), 1, i + 1));
        return result;
    }

    /**
     * The method multiplies the given vector by the given real number.
     * The method does not modify the given vector, but produces a new one instead.
     * 
     * @param vector - the given vector
     * @param number - the given real number
     * @return a new vector which is a product of multiplication
     */
    public static double[] multiply(double[] vector, double number) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            result[i] = vector[i] * number;
        return result;
    }

    /**
     * The method multiplies the given vector by the given real number.
     * The method does not modify the given vector, but produces a new one instead.
     *
     * @param vector - the given vector
     * @param number - the given real number
     * @return a new vector which is a product of multiplication
     */
    public static double[] multiplyUsingVectorAPINoMasking(double[] vector, double number) {
        double[] result = new double[vector.length];
        int i = 0;
        for (; i < SPECIES.loopBound(vector.length); i += vector.length)
            DoubleVector.fromArray(SPECIES, vector, i)
                    .mul(number)
                    .intoArray(result, i);
        for (; i < vector.length; i++)
            result[i] = vector[i] * number;
        return result;
    }

    /**
     * The method multiplies the given vector by the given real number.
     * The method does not modify the given vector, but produces a new one instead.
     *
     * @param vector - the given vector
     * @param number - the given real number
     * @return a new vector which is a product of multiplication
     */
    public static double[] multiplyUsingVectorAPI(double[] vector, double number) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i += vector.length) {
            var mask = SPECIES.indexInRange(i, vector.length);
            DoubleVector.fromArray(SPECIES, vector, i, mask)
                    .mul(number)
                    .intoArray(result, i, mask);
        }
        return result;
    }

    /**
     * The method sums up two vectors of same spatial base (same number of coordinate axes).
     * The method does not modify the given vectors.
     * <b>IMPORTANT:</b> to sum up vectors correctly, they need to be in the same-dimensional space.
     *
     * @param vector1 - the first addendum object
     * @param vector2 - the second addendum object
     * @return a new Vector object which is a product of the summation
     */
    public static double[] sum(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length) throw new IllegalArgumentException("In order to sum both vectors must have same number of coordinates.");
        double[] coordinatesSum = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++)
            coordinatesSum[i] = vector1[i] + vector2[i];
        return coordinatesSum;
    }

    /**
     * The method sums up two vectors of same spatial base (same number of coordinate axes).
     * The method does not modify the given vectors.
     * <b>IMPORTANT:</b> to sum up vectors correctly, they need to be in the same-dimensional space.
     *
     * @param vector1 - the first addendum object
     * @param vector2 - the second addendum object
     * @return a new Vector object which is a product of the summation
     */
    public static double[] sumUsingVectorAPINoMasking(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length)
            throw new IllegalArgumentException("In order to sum both vectors must have same number of coordinates.");
        double[] coordinatesSum = new double[vector1.length];
        int i = 0;
        for (; i < SPECIES.loopBound(vector1.length); i += SPECIES.length()) {
            var partOfVector1 = DoubleVector.fromArray(SPECIES, vector1, i);
            var partOfVector2 = DoubleVector.fromArray(SPECIES, vector2, i);
            var partResult = partOfVector1.add(partOfVector2);
            partResult.intoArray(coordinatesSum, i);
        }
        for (int j = i; j < vector1.length; j++)
            coordinatesSum[j] = vector1[j] + vector2[j];
        return coordinatesSum;
    }

    /**
     * The method sums up two vectors of same spatial base (same number of coordinate axes).
     * The method does not modify the given vectors.
     * <b>IMPORTANT:</b> to sum up vectors correctly, they need to be in the same-dimensional space.
     *
     * @param vector1 - the first addendum object
     * @param vector2 - the second addendum object
     * @return a new Vector object which is a product of the summation
     */
    public static double[] sumUsingVectorAPI(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length)
            throw new IllegalArgumentException("In order to sum both vectors must have same number of coordinates.");
        double[] coordinatesSum = new double[vector1.length];
        int i = 0;
        for (; i < vector1.length; i += SPECIES.length()) {
            var mask = SPECIES.indexInRange(i, vector1.length);
            var partOfVector1 = DoubleVector.fromArray(SPECIES, vector1, i, mask);
            var partOfVector2 = DoubleVector.fromArray(SPECIES, vector2, i, mask);
            var partResult = partOfVector1.add(partOfVector2);
            partResult.intoArray(coordinatesSum, i, mask);
        }
        return coordinatesSum;
    }

    public static double[] toVector(double[] startPoint, double[] endPoint) {
        double[] vectorFromZeroCoordinates = new double[startPoint.length];
        for (int i = 0; i < startPoint.length; i++)
            vectorFromZeroCoordinates[i] = endPoint[i] - startPoint[i];
        return vectorFromZeroCoordinates;
    }

    /**
     * This method calculates the length of the given vector.
     *
     * @param vector the given vector coordinates
     * @return the length of the given vector
     */
    public static double length(double[] vector) {
        return Math.sqrt(Arrays.stream(vector).map(d -> d*d).sum());
    }

    /**
     * This method calculates the length of the given vector.
     *
     * @param vector the given vector coordinates
     * @return the length of the given vector
     */
    public static double lengthUsingVectorAPINoMasking(double[] vector) {
        double sumOfSquares = .0d;
        var accumulator = DoubleVector.zero(SPECIES);
        int i = 0;
        for (; i < SPECIES.loopBound(vector.length); i += SPECIES.length()) {
            var partOfVector1 = DoubleVector.fromArray(SPECIES, vector, i);
            var squaredPartOfVectorValues = partOfVector1.mul(partOfVector1);
            accumulator = accumulator.add(squaredPartOfVectorValues);
        }
        sumOfSquares = accumulator.reduceLanes(VectorOperators.ADD);
        for (; i < vector.length; i++)
            sumOfSquares += vector[i]*vector[i];
        return Math.sqrt(sumOfSquares);
    }

    /**
     * This method calculates the length of the given vector.
     *
     * @param vector the given vector coordinates
     * @return the length of the given vector
     */
    public static double lengthUsingVectorAPI(double[] vector) {
        double sumOfSquares = .0d;
        for (int i = 0; i < vector.length; i += SPECIES.length()) {
            var mask = SPECIES.indexInRange(i, vector.length);
            var partOfVector1 = DoubleVector.fromArray(SPECIES, vector, i, mask);
            var squaredPartOfVectorValues = partOfVector1.mul(partOfVector1, mask);
            sumOfSquares += squaredPartOfVectorValues.reduceLanes(VectorOperators.ADD, mask);
        }
        return Math.sqrt(sumOfSquares);
    }

    /**
     * The given vector is represented with its start and end point coordinates in current basis.
     * This method calculates the length of the given vector.
     *
     * @param startPoint vector start point coordinates
     * @param endPoint vector end point coordinates
     * @return the length of the given vector
     */
    public static double length(double[] startPoint, double[] endPoint) {
        return length(toVector(startPoint, endPoint));
    }

    /**
     * The given vector is represented with its start and end point coordinates in current basis.
     * This method calculates the length of the given vector.
     *
     * @param startPoint vector start point coordinates
     * @param endPoint vector end point coordinates
     * @return the length of the given vector
     */
    public static double lengthUsingVectorAPINoMasking(double[] startPoint, double[] endPoint) {
        return lengthUsingVectorAPINoMasking(toVector(startPoint, endPoint));
    }
    /**
     * The given vector is represented with its start and end point coordinates in current basis.
     * This method calculates the length of the given vector.
     *
     * @param startPoint vector start point coordinates
     * @param endPoint vector end point coordinates
     * @return the length of the given vector
     */
    public static double lengthUsingVectorAPI(double[] startPoint, double[] endPoint) {
        return lengthUsingVectorAPI(toVector(startPoint, endPoint));
    }

    /**
     * The given vector is represented with its start and end point coordinates in current basis.
     * The method calculates <i>cos</i> for each angle between the corresponding axis and the
     * given vector.
     *
     * @param startPoint vector start point coordinates
     * @param endPoint vector end point coordinates
     * @return an array of <i>cos</i> for each angle between the corresponding axis and the
     * given vector, in radians
     */
    public static double[] angles(double[] startPoint, double[] endPoint) {
        if (startPoint.length != endPoint.length)
            throw new IllegalArgumentException("Points must have same number of dimensions.");
        double[] angles = new double[startPoint.length];
        double[] vectorFromZeroCoordinates = toVector(startPoint, endPoint);
        double length = length(vectorFromZeroCoordinates);
        for (int i = 0; i < startPoint.length; i++)
            angles[i] = vectorFromZeroCoordinates[i] / length;
        return angles;
    }

    /**
     * Calculates the dot product of the two given vectors.
     *
     * @param vector1 the first given vector
     * @param vector2 the second given vector
     * @return the real value of the dot product
     */
    public static double dot(final double[] vector1, final double[] vector2) {
        double result = 0.0d;
        if (vector1.length != vector2.length)
            throw new IllegalArgumentException("Points must have same number of dimensions.");
        for (int i = 0; i < vector1.length; i++)
            result += vector1[i] * vector2[i];
        return result;
    }

    /**
     * Calculates the dot product of the two given vectors.
     *
     * @param vector1 the first given vector
     * @param vector2 the second given vector
     * @return the real value of the dot product
     */
    public static double dotUsingVectorAPINoMasking(final double[] vector1, final double[] vector2) {
        if (vector1.length != vector2.length)
            throw new IllegalArgumentException("Points must have same number of dimensions.");

        int i = 0;
        DoubleVector sumVec = DoubleVector.zero(SPECIES);
        for (; i < SPECIES.loopBound(vector1.length); i += vector1.length) {
            DoubleVector v1 = DoubleVector.fromArray(SPECIES, vector1, i);
            DoubleVector v2 = DoubleVector.fromArray(SPECIES, vector2, i);
            sumVec = v1.fma(v2, sumVec);
        }

        double result = sumVec.reduceLanes(VectorOperators.ADD);
        for (; i < vector1.length; i++)
            result += vector1[i] * vector2[i];
        return result;
    }

    /**
     * Calculates the dot product of the two given vectors.
     *
     * @param vector1 the first given vector
     * @param vector2 the second given vector
     * @return the real value of the dot product
     */
    public static double dotUsingVectorAPI(final double[] vector1, final double[] vector2) {
        if (vector1.length != vector2.length)
            throw new IllegalArgumentException("Points must have same number of dimensions.");

        DoubleVector sumVec = DoubleVector.zero(SPECIES);
        for (int i = 0; i < vector1.length; i += vector1.length) {
            var mask = SPECIES.indexInRange(i, vector1.length);
            DoubleVector v1 = DoubleVector.fromArray(SPECIES, vector1, i, mask);
            DoubleVector v2 = DoubleVector.fromArray(SPECIES, vector2, i, mask);
            sumVec = v1.fma(v2, sumVec);
        }

        return sumVec.reduceLanes(VectorOperators.ADD);
    }

    /**
     * Checks if the two given vectors are equal.
     *
     * @param a vector A
     * @param b vector B
     * @return true if vectors are equal, false otherwise
     */
    public static boolean areEqual(final double[] a, final double[] b) {
        return Arrays.equals(a, b);
    }
}