/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package linear.matrix;

/**
 * LU decmposition are two matrices whose multiplication results in the decomposed matrix A.
 * <p>A = L*U</p>
 *
 * @param l the lower (maybe only psychologically lower) triangle matrix
 * @param u the upper triangle matrix
 */
public record LUDecomposition(double[][] l, double[][] u) {

    /**
     * Factory method.
     *
     * @param l the lower (maybe only psychologically lower) triangle matrix
     * @param u the upper triangle matrix
     */
    public static LUDecomposition of(double[][] l, double[][] u) {
        return new LUDecomposition(l, u);
    }
}
