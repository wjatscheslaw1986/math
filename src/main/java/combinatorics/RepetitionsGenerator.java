/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static combinatorics.CombinatoricsUtil.getPrintArrayFunction;

/**
 * A utility class for generating all possible repetitions of indices.
 *
 * @author Wjatscheslaw Michailov
 */
public final class RepetitionsGenerator {

    private RepetitionsGenerator() {
        // static context only
    }

    /**
     * Print repetitions for N elements of K to the given OutputStream.
     *
     * @param n quantity of possible options
     * @param k a size of each of the repetitions
     * @param out  an implementation of the OutputStream
     */
    public static void print(final int n, final int k, final OutputStream out) {
        generate(n, k, getPrintArrayFunction(out));
    }

    /**
     * Generate a list of repetitions for N elements of K. The elements are
     * represented by their index.
     *
     * @param n quantity of possible options
     * @param k a size of each of the repetitions
     * @return the list of repetitions for N elements of K
     */
    public static List<int[]> generate(int n, int k) {
        final var list = new ArrayList<int[]>();
        generate(n, k, list::add);
        return list;
    }

    /*
     * Algorithm
     */
    static void generate(final int n, final int k, final Consumer<int[]> func) {
        final int[] permutation = new int[k];
        for (;;) {
            func.accept(Arrays.copyOf(permutation, permutation.length));
            int i = k - 1;
            for(; i >= 0; i--) {
              if (permutation[i] < n - 1) {
                  break;
              }
            }
            if (i < 0) {
                break;
            }
            permutation[i] += 1;
            i = i + 1;
            while (i < k) {
                permutation[i] = 0;
                i += 1;
            }
        }
    }
}
