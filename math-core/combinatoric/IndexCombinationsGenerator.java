/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatoric;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * A utility class for generating all possible combinations of values of a given array.
 * 
 * @author Wjatscheslaw Michailov
 */
public final class IndexCombinationsGenerator {

    private IndexCombinationsGenerator() {
        // static context only
    }

    /**
     * Print permutations for a size of array to the given OutputStream.
     * 
     * @param n   size of a given set of all elements
     * @param k   size of a single choice, i.e. a subset of <b>n</b>
     * @param out an implementation of the OutputStream
     */
    public static void print(final OutputStream out, final int n, final int k) {
        process(n, k, getPrintArrayFromToFunction(out));
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
        final List<int[]> result = new ArrayList<int[]>();
        process(n, k, getStoreInListFunction(result));
        return result;
    }

    /**
     * Generates a full set of sets of <b>k</b> elements of <b>n</b> elements,
     * having each mutual permutation of the chosen <b>k</b> elements as a distinct set to add it
     * to the resulting collection.
     * 
     * @param n    size of a given set of all elements
     * @param k    size of a single choice, i.e. a subset of <b>n</b>
     * @param func a function to perform over each distinct set of elements
     */
    public static void process(final int n, final int k, final BiConsumer<int[], Integer> func) {
        final int[] comb = new int[k + 2];
        for (int i = 0; i < k; i++) {
            comb[i] = i;
        }
        comb[k] = n;
        comb[k + 1] = 0;
        for (;;) {
            func.accept(comb, k);
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

    private final static BiConsumer<int[], Integer> getStoreInListFunction(final List<int[]> resultList) {
        return (final int[] currentCombination, final Integer toIndex) -> {
            resultList.add(subArrayFromZeroTo(currentCombination, toIndex));
        };
    };

    private final static BiConsumer<int[], Integer> getPrintArrayFromToFunction(final OutputStream o) {
        return (final int[] currentCombination, final Integer toIndex) -> {
            try {
                o.write(Arrays.toString(subArrayFromZeroTo(currentCombination, toIndex)).getBytes());
                o.write(System.lineSeparator().getBytes());
            } catch (IOException e) {
                System.err.print(e.getLocalizedMessage());
            }
        };
    }

    private static int[] subArrayFromZeroTo(final int[] array, final int toIndex) {
        final int[] subArray = new int[toIndex];
        System.arraycopy(array, 0, subArray, 0, toIndex);
        return subArray;
    }
}
