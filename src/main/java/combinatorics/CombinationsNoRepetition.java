/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for generating all possible combinations of indices,
 * without repetition. Order doesn't matter.
 *
 * @author Viacheslav Mikhailov
 */
public final class CombinationsNoRepetition {

    private CombinationsNoRepetition() {
        // static context only
    }

    /**
     * Generate all at once combinations of selections of <b>k</b>
     * of <b>n</b> sequential array indices, starting at 0.
     * These combinations differ only in the elements contained.
     * No repetitions of elements, no different orderings of same set of elements.
     * 
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return list of arrays of all possible combinations of indices
     *         for the array of the given size.
     */
    public static List<int[]> generate(final int n, final int k) {
        final var result = new ArrayList<int[]>();
        var generator = new CombinationsNoRepetitionGenerator(n, k);
        while (generator.hasNext()) {
            result.add(generator.next());
        }
        return result;
    }

    /**
     * Generate first <b>c</b> combinations of selections of <b>k</b>
     * of <b>n</b> sequential array indices, starting at 0.
     * These combinations differ only in the elements contained.
     * No repetitions of elements, no different orderings of same set of elements.
     *
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @param c count of elements to generate
     * @return list of arrays of all possible combinations of indices
     *         for the array of the given size.
     */
    public static List<int[]> generate(final int n, final int k, int c) {
        final var result = new ArrayList<int[]>();
        var generator = new CombinationsNoRepetitionGenerator(n, k);
        while (generator.hasNext() && c-- > 0) {
            result.add(generator.next());
        }
        return result;
    }

    public static long count(final int n, final int k) {
        return CombinatoricsCalc.binomialCoefficient(n, k);
    }
}
