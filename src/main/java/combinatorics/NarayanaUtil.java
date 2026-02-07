/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static combinatorics.CombinatoricsUtil.getPrintIntArrayFunction;
import static linear.spatial.VectorUtil.swap;

/**
 * A utility class for generating a series of all possible permutations of indices
 * for an array of a given length, in lexicographical order.
 *
 * @author Viacheslav Mikhailov
 */
public class NarayanaUtil {

    private NarayanaUtil() {
        // static context only
    }

    /**
     * Generate all the permutations for a given size of a sequence of indices starting at 0, at once.
     *
     * @param size the size given
     * @return all the permutations for the given size
     */
    public static List<int[]> generate(final int size) {
        var list = new ArrayList<int[]>();
        new NarayanaFirstNPermutations(size).forEach(list::add);
        return list;
    }

    /**
     * Print permutations for a size of array to the given OutputStream.
     *
     * @param size a size of an array of indices, starting with 0;
     * @param out  an implementation of the OutputStream
     */
    public static void print(final OutputStream out, final int size) {
        print(out, size, Arrays::toString);
    }

    public static void print(final OutputStream out, final int size, final Function<int[], String> format) {
        var gen = new NarayanaPermutationsGenerator(size);
        while (gen.hasNext()) {
            getPrintIntArrayFunction(out, format).accept(gen.next());
        }
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
     * TODO doc
     */
    public static class NarayanaFirstNPermutations implements Iterable<int[]> {

        private final Iterator<int[]> iterator;

        public NarayanaFirstNPermutations(int sequenceLength, int limit) {
            var gen = new NarayanaPermutationsGenerator(sequenceLength);
            List<int[]> permutations = new ArrayList<>();
            while (gen.hasNext() && limit-- > 0) {
                permutations.add(gen.next());
            }
            iterator = permutations.iterator();
        }

        public NarayanaFirstNPermutations(int sequenceLength) {
            this(sequenceLength, sequenceLength);
        }

        @Override
        public Iterator<int[]> iterator() {
            return this.iterator;
        }
    }
}
