/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for generating all possible variations (arrangements) of indices.
 * Order matters (i.e. different ordering makes a different variation).
 *
 * @author Viacheslav Mikhailov
 */
public final class VariationsWithRepetitionsUtil {

    private VariationsWithRepetitionsUtil() {
        // static context only
    }

    /**
     * Generate a list of variations for K elements of N elements.
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
        var gen = new VariationsWithRepetitionsGenerator(n, k);
        while (gen.hasNext()) list.add(gen.next());
        return list;
    }

    /**
     * Returns a number of all possible arrangements of selections of indices, distinct by their order.
     *
     * @param n cardinality
     * @param k size of a selection
     * @return a count of all possible variations of indices, distinctly ordered.
     */
    public static int countVariationsWithRepetitions(int n, int k) {
        return (int) Math.pow(n, k);
    }
}
