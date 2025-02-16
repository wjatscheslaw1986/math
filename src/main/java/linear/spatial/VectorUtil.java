/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.spatial;

/**
 * A utility class for vectors.
 *
 * @author Wjatscheslaw Michailov
 */
public final class VectorUtil {

    private VectorUtil() {
        // static context only
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
