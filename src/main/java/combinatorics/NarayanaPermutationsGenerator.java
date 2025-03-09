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
import static linear.spatial.VectorUtil.swap;

/**
 * A utility class for generating a series of all possible permutations of indices
 * for an array of a given length, in lexicographical order.
 *
 * @author Viacheslav Mikhailov
 */
public class NarayanaPermutationsGenerator {

    private NarayanaPermutationsGenerator() {
        // static context only
    }

    /**
     * Print permutations for a size of array to the given OutputStream.
     *
     * @param size a size of an array of indices, starting with 0;
     * @param out  an implementation of the OutputStream
     */
    public static void print(final OutputStream out, final int size) {
        generate(size, getPrintArrayFunction(out));
    }

    /**
     * Generate permutations for a size of array.
     *
     * @param size a size of array of indices, starting with 0;
     * @return list of arrays of all possible permutations of values in the array of the given size.
     */
    public static List<int[]> generate(final int size) {
        final var list = new ArrayList<int[]>();
        generate(size, list::add);
        return list;
    }

    /*
     * Generate permutations for a size of array.
     *
     * @param size a size of array of indices, starting with 0;
     * @param func an operation to perform on each permutation
     * @return list of arrays of all possible permutations of values in the given array.
     */
    private static void generate(final int size, final Consumer<int[]> func) {
        final int[] permutation = new int[size];
        for (int i = 0; i < size; i++) {
            permutation[i] = i;
        }
        func.accept(Arrays.copyOf(permutation, size));
        int index = findMaxIndexOfElementSmallerThanItsRightNeighbour(permutation);
        while (index != -1) {
            final int element = permutation[index];
            int swapIndex = findMaxIndexOfElementBiggerThanGivenValue(permutation, element);
            swap(permutation, index, swapIndex);
            reverseOrderAfterIndex(permutation, index);
            func.accept(Arrays.copyOf(permutation, size));
            index = findMaxIndexOfElementSmallerThanItsRightNeighbour(permutation);
        }
    }

    /*
     * Modifies the given array by reversing order of its elements strictly after
     * the given index (i.e. not including it).
     */
    static void reverseOrderAfterIndex(final int[] permutation, final int index) {
        int shift = index + 1;
        for (int i = 0; i < (permutation.length - shift) / 2; i++) {
            swap(permutation, shift + i, permutation.length - i - 1);
        }
    }

    /*
     * @return max index of element whose value is bigger than <i>currentElementValue</i>.
     */
    private static int findMaxIndexOfElementBiggerThanGivenValue(final int[] array, final int currentElementValue) {
        for (int i = array.length - 1; i >= 0; i--)
            if (array[i] > currentElementValue)
                return i;
        return -1;
    }

    /*
     * @return max index of element whose value is smaller than the value of its right neighbour.
     */
    private static int findMaxIndexOfElementSmallerThanItsRightNeighbour(final int[] array) {
        for (int i = array.length - 2; i >= 0; i--)
            if (array[i] < array[i + 1])
                return i;
        return -1;
    }
}
