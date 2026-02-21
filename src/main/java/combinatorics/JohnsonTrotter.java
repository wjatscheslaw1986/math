/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A utility class for generating all possible permutations of indices for an array of a
 * given length (i.e. cardinality), without repetitions.
 *
 * @author Viacheslav Mikhailov
 */
public final class JohnsonTrotter {

    private JohnsonTrotter() {
        // static context only
    }

    /**
     * Generate permutations of indices, for a set of <b>n</b> first array indices,
     * starting at 0, returning them as a list of permutations.
     * <p>
     *     No repetitions of indices are allowed.
     * </p>
     *
     * @param n length of an array of sequential indices, starting at 0 (also, cardinality)
     * @return list of all possible permutations of <b>n</b> indices
     */
    public static List<int[]> generate(int n) {
        final var list = new ArrayList<int[]>();
        final var gen = new JohnsonTrotterPermutationsGenerator(n);
        while (gen.hasNext()) {
            list.add(gen.next());
        }
        return list;
    }

    /**
     * Generate first <i>n</i> permutations for a given cardinality of a sequence of indices starting at 0, at once.
     *
     * @param cardinality count of distinct elements in the set of indices
     * @param n the number of the first permutations to generate.
     * @return all the permutations for the given size
     */
    public static List<int[]> generate(final int cardinality, final int n) {
        final var list = new ArrayList<int[]>();
        for (int[] p : new JohnsonTrotterFirstNPermutations(cardinality, n)) {
            list.add(p);
        }
        return list;
    }

    static int maxMobileElementIndex(int[] permutation, int[] direction) {
        int index = -1;
        for (int i = 0; i < permutation.length; i++) {
            int nextIndex = i + direction[i];
            if (nextIndex >= 0 && nextIndex < permutation.length) {
                if (permutation[i] > permutation[nextIndex]) {
                    if (index == -1) {
                        index = i;
                    } else {
                        if (permutation[i] > permutation[index]) {
                            index = i;
                        }
                    }
                }
            }
        }
        return index;
    }

    static void changeDirection(int[] permutation, int[] direction, int mobileElement) {
        for (int i = 0; i < permutation.length; i++) {
            if (permutation[i] > mobileElement) {
                direction[i] = direction[i] * (-1);
            }
        }
    }

    static void swap(int[] permutation, int[] direction, int i, int j) {
        int temp = permutation[i];
        permutation[i] = permutation[j];
        permutation[j] = temp;

        int temp2 = direction[i];
        direction[i] = direction[j];
        direction[j] = temp2;
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
     * An iterator encapsulation for a list of the first <i>n</i> permutations generated
     * by Johnson Trotter permutations generator {@link JohnsonTrotterPermutationsGenerator}.
     */
    private static class JohnsonTrotterFirstNPermutations implements Iterable<int[]> {

        private final Iterator<int[]> iterator;

        /**
         * Constructor.
         *
         * @param sequenceLength cardinality
         * @param n first <i>n</i> elements limit
         */
        private JohnsonTrotterFirstNPermutations(final int sequenceLength, int n) {
            final var gen = new JohnsonTrotterPermutationsGenerator(sequenceLength);
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
