/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.spatial;

import algebra.Complex;
import algebra.EquationUtil;
import algebra.Letter;
import algebra.Term;
import linear.equation.LinearEquationSystemUtil;
import linear.equation.Solution;
import linear.matrix.exception.MatrixException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static algebra.EquationUtil.distinct;
import static linear.equation.LinearEquationSystemUtil.convertLinearEquationSystem;

/**
 * A utility class for calculations of vectors.
 *
 * @author Viacheslav Mikhailov
 */
public final class VectorCalc {

    private VectorCalc() {
        // static context only
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
     * The method multiplies the given vector on a rational number
     * The method does not modify the original Vector object
     * 
     * @param vector - the original Vector object
     * @param number - the rational number to multiply the vector on
     * @return a new vector which is a result of the multiplication
     */
    public static double[] multiply(double[] vector, double number) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++)
            result[i] = vector[i] * number;
        return result;
    }

    /**
     * The method sums up two vectors of same spatial base (same amount of coordinate axes).
     * The method does not modify the given vectors.
     * <b>IMPORTANT:</b> to sum up vectors correctly they need to be in the same-dimensional space.
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