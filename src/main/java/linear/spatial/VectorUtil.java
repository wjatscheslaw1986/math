/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.spatial;

import linear.equation.CramerLinearEquationSystem;
import linear.equation.LinearEquationSystemUtil;
import linear.matrix.MatrixCalc;

import java.util.Arrays;
import java.util.Objects;

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
     * Express the vector in a new basis.
     *
     * @param vector the given vector
     * @param basis the given basis
     * @return the vector with new coordinates, as they render in the given basis
     */
    public static Vector transformToBasis(final Vector vector, final Vector... basis) {
        if(!isBasis(Objects.requireNonNull(basis)))
            throw new IllegalArgumentException("Not a basis");
        var equationSystem = new CramerLinearEquationSystem(LinearEquationSystemUtil::resolveUsingCramerMethod, toLinearEquationSystem(vector, basis));
        return Vector.of(equationSystem.getResolved());
    }

    static double[][] toLinearEquationSystem(final Vector vector, final Vector... basis) {
        if (vector.coordinates().length != basis.length)
            throw new IllegalArgumentException("Vector length must be of same size as basis.");
        double[][] equationSystem = new double[vector.coordinates().length][basis.length + 1];
        for (int i = 0; i < basis.length; i++) {
            for (int j = 0; j < equationSystem.length; j++) {
                equationSystem[i][j] = basis[j].coordinates()[i];
            }
        }
        for (int i = 0; i < vector.coordinates().length; i++) {
            equationSystem[i][basis.length] =  vector.coordinates()[i];
        }
        return equationSystem;
    }

    /**
     * Converts Vector[] to double[][] datatype.
     *
     * @param basis the given {@link Vector} array
     * @param vertical if true, then the given vectors are columns in the resulting matrix. If false, then the given vectors are rows.
     * @return the double precision matrix
     */
    public static double[][] toMatrix(boolean vertical, final Vector... basis) {
        if (basis.length != basis[0].coordinates().length)
            throw new IllegalArgumentException("Vector length must be of same size as basis.");
        double[][] matrix = new double[basis[0].coordinates().length][basis.length];
        for (int i = 0; i < basis.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (vertical)
                    matrix[i][j] = basis[j].coordinates()[i];
                else
                    matrix[i][j] = basis[i].coordinates()[j];
            }
        }
        return matrix;
    }

    /**
     * Converts Eigenvector[] to double[][] datatype.
     *
     * @param basis the given {@link Eigenvector} array
     * @param vertical if true, then the given vectors are columns in the resulting matrix. If false, then the given vectors are rows.
     * @return the double precision matrix
     */
    public static double[][] toMatrix(boolean vertical, final Eigenvector... basis) {
        return toMatrix(vertical, Arrays.stream(basis).map(Eigenvector::eigenvector).toArray(Vector[]::new));
    }

    /**
     * Returns a diagonal matrix given the diagonal as a double[].
     * Converts double[] to double[][] datatype.
     *
     * @param diagonal the given vector; to be used as the diagonal of the result matrix
     * @return the diagonal matrix
     */
    public static double[][] toDiagonalMatrix(final double[] diagonal) {
        double[][] matrix = new double[diagonal.length][diagonal.length];
        for (int i = 0; i < diagonal.length; i++)
            matrix[i][i] = diagonal[i];
        return matrix;
    }

    /**
     * Checks if the given vectors are linearly independent, i.e. make up a basis in a linear space.
     *
     * @param vectors the given vectors
     * @return true if the given vectors are mutually independent, false otherwise
     */
    public static boolean isBasis(Vector... vectors) {
        return MatrixCalc.det(toMatrix(true, vectors)) != .0d;
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
     * Mutually swap two elements of the given array, by the indices given.
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
