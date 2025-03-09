/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static combinatorics.CombinatoricsUtil.getPrintArrayFunction;

/**
 * A utility class for generating all possible variations (arrangements) of indices.
 * Variation means order matters (i.e. different ordering makes a different variation).
 *
 * @author Viacheslav Mikhailov
 */
public final class VariationsWithRepetitionsGenerator {

    private VariationsWithRepetitionsGenerator() {
        // static context only
    }

    /**
     * Print variations for K elements of N to the given OutputStream.
     *
     * @param n quantity of possible options
     * @param k size of each variation
     * @param out  an implementation of the OutputStream
     */
    public static void print(final int n, final int k, final OutputStream out) {
        generate(n, k, getPrintArrayFunction(out));
    }

    /**
     * Generate a list of variations for K elements of N.
     * Elements may repeat in each variation.
     * A variation with same elements but different ordering of the elements counts
     * as a separate (i.e. distinct) variation.
     * The elements are represented by their indices in an array that may contain them.
     *
     * @param n power of the given set
     * @param k size of each variation
     * @return list of variations with repetitions for K elements of N
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
        final int[] variations = new int[k];
        for (;;) {
            func.accept(Arrays.copyOf(variations, variations.length));
            int i = k - 1;
            for(; i >= 0; i--) {
              if (variations[i] < n - 1) {
                  break;
              }
            }
            if (i < 0) {
                break;
            }
            variations[i] += 1;
            i = i + 1;
            while (i < k) {
                variations[i] = 0;
                i += 1;
            }
        }
    }
}
