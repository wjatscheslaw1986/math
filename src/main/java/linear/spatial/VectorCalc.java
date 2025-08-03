/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.spatial;

import algebra.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static algebra.EquationUtil.distinct;

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
     *
     * @param transformationMatrix
     * @return
     */
    public static Vector[] eigenvectors(final double[][] transformationMatrix) {
        EquationRoots<Complex> roots = EquationUtil.solve(EquationUtil.toCharacteristicPolynomial(transformationMatrix));
        List<Vector> vectors = new ArrayList<>();
        int i = 1;
        for (var root : roots.roots()) {
            List<Member> linearEquationMembers = new ArrayList<>();
            for (int j = 1; j <= transformationMatrix[i-1].length; j++) {
                linearEquationMembers.add(Member.builder()
                                .power(1.0d)
                                .coefficient(transformationMatrix[i-1][j-1])
                                .letter(Letter.of("x", j))
                                .value(Double.NaN)
                        .build());
            }
            linearEquationMembers.add(Member.builder()
                    .power(1.0d)
                    .coefficient(-root.real())
                    .letter(Letter.of("x", i))
                    .value(Double.NaN)
                    .build());
            EquationRoots<Complex> coords = EquationUtil.solve(Equation.of(distinct(linearEquationMembers), Member.asRealConstant(.0d)));
            vectors.add(Vector.of(coords.roots().stream().mapToDouble(Complex::real).toArray()));
            // TODO all matrix rows per root
            ++i;
        }
        return vectors.toArray(new Vector[0]);

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