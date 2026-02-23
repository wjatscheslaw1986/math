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
public final class VariationsWithRepetitions {

    private VariationsWithRepetitions() {
        // static context only
    }

    /**
     * Generate a list of variations for K elements of N elements.
     * Elements may repeat in each variation.
     * A variation with same elements but different ordering of the elements counts
     * as a separate (i.e. distinct) variation.
     * The elements are represented by their indices in an array that may contain them.
     *
     * @param n cardinality
     * @param k size of each variation
     * @return list of variations with repetitions for K elements of N
     */
    public static List<int[]> generate(final int n, final int k) {
        final var list = new ArrayList<int[]>();
        var gen = new VariationsWithRepetitionsGenerator(n, k);
        while (gen.hasNext()) list.add(gen.next());
        return list;
    }

    /**
     * Count generated variations.
     * <p>
     *     It is a number of ways to type <b>k</b> times any letter of an alphabet of <b>n</b> letters.
     * </p>
     *
     * @param n cardinality
     * @param k size of a variation
     * @return number of all possible variations
     */
    public static int count(final int n, final int k) {
        return CombinatoricsCalc.countVariationsWithRepetition(n, k);
    }
}
