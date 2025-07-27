/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.spatial;

import linear.equation.LinearEquationSystemUtil;
import linear.matrix.exception.MatrixException;

import static linear.equation.SolutionsCount.SINGLE;

/**
 * A utility class for vectors.
 *
 * @author Viacheslav Mikhailov
 */
public final class VectorUtil {

    private VectorUtil() {
        // static context only
    }

    /**
     * Checks if the given vectors make a basis in a linear space.
     *
     * @param vectors the given vectors
     * @return true if the given vectors are mutually independent, false otherwise
     */
    public static boolean isBasis(Vector... vectors) throws MatrixException {
        double[][] vectorsArray = new double[vectors.length][vectors[0].coordinates().length + 1];
        for (int i = 0; i < vectors[0].coordinates().length; i++) {
            double[] equation = new double[vectors[0].coordinates().length + 1];
            for (int j = 0; j < vectors.length; j++) {
                equation[j] = vectors[j].coordinates()[i];
                equation[equation.length - 1] = .0d;
            }
            vectorsArray[i] = equation;
        }
        var solution = LinearEquationSystemUtil.resolveUsingJordanGaussMethod(vectorsArray);
        return solution.solutionsCount() == SINGLE;
    }

    /**
     * Mutually swap two elements of a double array found by their indices.
     *
     * @param vector given
     * @param i index
     * @param j index
     */
    public static void swap(final double[] vector, final int i, final int j) {
        final double temp = vector[i];
        vector[i] = vector[j];
        vector[j] = temp;
    }

    /**
     * Mutually swap two elements of an integer array found by their indices.
     *
     * @param vector given
     * @param i index
     * @param j index
     */
    public static void swap(final int[] vector, final int i, final int j) {
        final int temp = vector[i];
        vector[i] = vector[j];
        vector[j] = temp;
    }

}
