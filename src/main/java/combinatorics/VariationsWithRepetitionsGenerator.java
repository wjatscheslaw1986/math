/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static combinatorics.CombinatoricsUtil.cutFrom;
import static combinatorics.CombinatoricsUtil.getPrintArrayFunction;

/**
 * A utility class for generating all possible variations with repetitions of array indices,
 * for an array of a given size.
 * 
 * @author Wjatscheslaw Michailov
 */
public final class VariationsWithRepetitionsGenerator {

    private VariationsWithRepetitionsGenerator() {
        // static context only
    }

    /**
     * Print permutations for a size of array to the given OutputStream.
     * 
     * @param n   size of a given set of all elements
     * @param k   size of a single choice, i.e. a subset of <b>n</b>
     * @param out an implementation of the OutputStream
     */
    public static void print(final int n, final int k, final OutputStream out) {
        process(n, k, getPrintArrayFunction(out));
    }

    /**
     * Generate all variations of choices of <b>k</b> of <b>n</b> indices for any array starting
     * with index 0. These variations may differ in ordering of their elements as well as in the elements
     * that are contained in them.
     * 
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return list of arrays of all possible permutations of values in the array of the given size.
     */
    public static List<int[]> generate(final int n, final int k) {
        final var result = new ArrayList<int[]>();
        process(n, k, result::add);
        return result;
    }

    /*
     * Generates a full set of sets of <b>k</b> elements of <b>n</b> elements,
     * having each mutual permutation of the chosen <b>k</b> elements as a distinct set to add it
     * to the resulting collection.
     * 
     * @param n    size of a given set of all elements
     * @param k    size of a single choice, i.e. a subset of <b>n</b>
     * @param func a function to perform over each distinct set of elements
     */
    private static void process(final int n, final int k, final Consumer<int[]> func) {
        final int[] comb = new int[k + 2];
        for (int i = 0; i < k; i++) {
            comb[i] = i;
        }
        comb[k] = n;
        comb[k + 1] = 0;
        for (; ; ) {
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
}
