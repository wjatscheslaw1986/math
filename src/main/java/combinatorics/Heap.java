/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import java.util.Arrays;

import static combinatorics.CombinatoricsCalc.factorial;
import static combinatorics.CombinatoricsUtil.getArrayOfOrdinalsForSize;
import static linear.spatial.VectorUtil.swap;

/**
 * Generates all permutations of size {@code k} using Heap's algorithm.
 * <p>
 * The implementation precomputes the total number of permutations ({@code k!})
 * and stores each generated permutation in a 2D array.
 * </p>
 *
 * <p>
 * The algorithm mutates an internal working array and copies each permutation
 * into the result matrix as it is produced.
 * </p>
 */
public class Heap {
    /**
     * Stores all generated permutations.
     * The array size is {@code k! x k}.
     */
    private final int[][] result;

    /**
     * The working array representing the current permutation state.
     */
    private final int[] lastPermutation;

    /**
     * Index used to populate the {@link #result} array.
     */
    private int resultPopulationIndex = 0;

    /**
     * Constructs a permutation generator for permutations of size {@code k}.
     *
     * @param k the size of the permutation set (must be positive)
     * @throws ArithmeticException if {@code k!} exceeds {@link Integer#MAX_VALUE}
     */
    public Heap(final int k) {
        lastPermutation = getArrayOfOrdinalsForSize(k);
        result = new int[Math.toIntExact(factorial(lastPermutation.length))][k];
    }

    /**
     * Generates all permutations of the configured size.
     *
     * @return a 2D array containing all {@code k!} permutations,
     * where each row represents a distinct permutation
     */
    public int[][] generate() {
        generate(lastPermutation.length);
        return result;
    }

    /**
     * Recursively generates permutations using Heap's algorithm.
     *
     * <p>
     * When {@code k == 1}, the current permutation is copied into the result array.
     * Otherwise, the method recursively generates permutations of size {@code k - 1}
     * and performs element swaps depending on whether {@code k} is even or odd.
     * </p>
     *
     * @param k the current recursion depth / subproblem size
     */
    private void generate(final int k) {
        if (k == 1) {
            result[resultPopulationIndex++] = Arrays.copyOf(lastPermutation, lastPermutation.length);
            return;
        }
        for (int i = 0; i < k; i++) {
            generate(k - 1);
            if (k % 2 == 0) {
                swap(lastPermutation, i, k - 1);
            } else {
                swap(lastPermutation, 0, k - 1);
            }
        }
    }
}
