/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package linear.matrix;

/**
 * LU decomposition with stored permutations.
 * <p>P*A = L*U</p>
 *
 * @param l the lower (maybe only psychologically lower) triangle matrix
 * @param u the upper triangle matrix
 * @param permutations row permutations history for recovering the matrix equation P*A = L*U
 */
public record LUPDecomposition(double[][] l, double[][] u, int[] permutations) {

    /**
     * Factory method.
     *
     * @param l the lower (maybe only psychologically lower) triangle matrix
     * @param u the upper triangle matrix
     * @param permutations row permutations history for recovering the matrix equation P*A = L*U
     */
    public static LUPDecomposition of(double[][] l, double[][] u, int[] permutations) {
        return new LUPDecomposition(l, u, permutations);
    }

    /**
     * The identity matrix with ones not obligatory on its diagonal.
     *
     * @return the identity matrix for the equation P*A = L*U to hold.
     */
    public double[][] getPermutatedIdentity() {
        double[][] result = new double[permutations.length][permutations.length];
        for (int row = 0; row < permutations.length; row++) {
            result[row][permutations[row]] = 1.0d;
        }
        return result;
    }
}
