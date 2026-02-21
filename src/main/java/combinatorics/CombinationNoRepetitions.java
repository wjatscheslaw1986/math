/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static combinatorics.CombinatoricsUtil.cutFrom;

/**
 * A utility class for generating all possible combinations of indices,
 * without repetition. Order doesn't matter.
 *
 * @author Viacheslav Mikhailov
 */
public final class CombinationNoRepetitions {

    private CombinationNoRepetitions() {
        // static context only
    }

    /**
     * Generate all at once combinations of selections of <b>k</b>
     * of <b>n</b> array indices, starting at 0.
     * These combinations differ only in the elements contained.
     * No repetitions of elements, no different orderings of same set of elements.
     * 
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return list of arrays of all possible combinations of indices for the array of the given size.
     */
    public static List<int[]> generate(final int n, final int k) {
        final var result = new ArrayList<int[]>();
        process(n, k, result::add);
        return result;
    }

    /**
     * Generate a full set of sets of <b>k</b> elements of <b>n</b> elements,
     * having each set of the chosen <b>k</b> elements as a distinct set disregard
     * order of its elements.
     * 
     * @param n    size of a given set of all elements
     * @param k    size of a single choice, i.e. a subset of <b>n</b>
     * @param func a function to perform over each set of the elements
     */
    private static void process(final int n, final int k, final Consumer<int[]> func) {
        final int[] comb = new int[k + 2];
        for (int i = 0; i < k; i++) {
            comb[i] = i;
        }
        comb[k] = n;
        comb[k + 1] = 0;
        for (;;) {
            func.accept(cutFrom(comb, k));
            int j = 0;
            for (; comb[j] + 1 == comb[j + 1]; j++) {
                comb[j] = j;
            }
            if (j < k) {
                comb[j]++;
            } else {
                break;
            }
        }
    }

    public static int count(final int n, final int k) {
        return Math.toIntExact(CombinatoricsCalc.binomialCoefficient(n, k));
    }
}
