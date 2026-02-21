/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static linear.spatial.VectorUtil.swap;

/**
 * A utility class for generating series of all possible permutations without of indices
 * for an array of indices of a given length (which is also a cardinality), in lexicographical order.
 *
 * @author Viacheslav Mikhailov
 */
public final class Narayana {

    private Narayana() {
        // static context only
    }

    /**
     * Generate all the possible permutations for the given size of a sequence of indices, starting at 0, all at once.
     *
     * @param size the size given
     * @return all the permutations for the given size of a sequence of indices
     */
    public static List<int[]> generate(final int size) {
        var gen = new NarayanaPermutationsGenerator(size);
        List<int[]> permutations = new ArrayList<>();
        while (gen.hasNext()) {
            permutations.add(gen.next());
        }
        return permutations;
    }

    /**
     * Generate first <i>n</i> permutations for a given size of a sequence of indices starting at 0, at once.
     *
     * @param size the size given
     * @param n the number of the first permutations to generate.
     * @return all the permutations for the given size
     */
    public static List<int[]> generate(final int size, final int n) {
        List<int[]> permutations = new ArrayList<>();
        for (int[] p : new NarayanaFirstNPermutations(size, n))
            permutations.add(p);
        return permutations;
    }

    /**
     * Modifies the given array by reversing order of its elements strictly after
     * the given index (i.e. not including it).
     */
    static void reverseOrderAfterIndex(final int[] permutation, final int index) {
        int shift = index + 1;
        for (int i = 0; i < (permutation.length - shift) / 2; i++) {
            swap(permutation, shift + i, permutation.length - i - 1);
        }
    }

    /**
     * @return max index of element whose value is bigger than <i>currentElementValue</i>.
     */
    static int findMaxIndexOfElementBiggerThanGivenValue(final int[] array, final int currentElementValue) {
        for (int i = array.length - 1; i >= 0; i--)
            if (array[i] > currentElementValue)
                return i;
        return -1;
    }

    /**
     * @return max index of element whose value is smaller than the value of its right neighbour.
     */
    static int findMaxIndexOfElementSmallerThanItsRightNeighbour(final int[] array) {
        for (int i = array.length - 2; i >= 0; i--)
            if (array[i] < array[i + 1])
                return i;
        return -1;
    }

    /**
     * Count generated permutations.
     * <p>
     *     It is a number of ways to click <b>n</b> distinct keys, each exactly 1 time.
     *     I.e. only the order makes distinct permutations.
     * </p>
     *
     * @param n cardinality
     * @return number of permutations without repetitions
     */
    public static int count(int n) {
        return CombinatoricsCalc.countPermutationsNoRepetitions(n);
    }

    /**
     * An iterator incapsulation for a list of the first <i>n</i> permutations generated
     * by Narayana permutations generator {@link NarayanaPermutationsGenerator}.
     */
    private static class NarayanaFirstNPermutations implements Iterable<int[]> {

        private final Iterator<int[]> iterator;

        /**
         * Constructor.
         *
         * @param sequenceLength cardinality
         * @param n first <i>n</i> elements limit
         */
        private NarayanaFirstNPermutations(final int sequenceLength, int n) {
            final var gen = new NarayanaPermutationsGenerator(sequenceLength);
            final var permutations = new ArrayList<int[]>();
            while (gen.hasNext() && n-- > 0) {
                permutations.add(gen.next());
            }
            iterator = permutations.iterator();
        }

        @Override
        public Iterator<int[]> iterator() {
            return this.iterator;
        }
    }
}
