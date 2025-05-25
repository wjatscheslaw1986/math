/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import linear.spatial.VectorUtil;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static combinatorics.CombinatoricsUtil.getPrintArrayFunction;

/**
 * A utility class for generating all possible permutations of indices for a given generic array,
 * using Gideon Ehrlich's algorithm.
 * 
 * @author Viacheslav Mikhailov
 */
public final class EhrlichPermutationsGenerator {

    private EhrlichPermutationsGenerator() {
        // static context only
    }

    /**
     * Print permutations for the given array to the given OutputStream.
     *
     * @param initialPermutation the array given to derive all possible permutations of its elements
     *                           from it, including this given one
     * @param <T> a type of objects in the given array
     * @param out  an implementation of the OutputStream
     */
    public static <T> void print(final OutputStream out, final T[] initialPermutation) {
        generate(initialPermutation, getPrintArrayFunction(out));
    }

    /**
     * Generate all possible permutations for the given array, including itself as the first permutation,
     * using Gideon Ehrlich algorithm.
     *
     * @param initialPermutation the generified array given to derive all possible permutations of its elements
     *                           from it, including this given one
     * @param <T> a type of objects in the given array
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T[]> generate(final T[] initialPermutation) {
        final var list = new ArrayList<T[]>(initialPermutation.length);
        generate(initialPermutation, list::add);
        return list;
    }

    /**
     * Generate all possible permutations for the given array, including itself as the first permutation,
     * using Gideon Ehrlich algorithm.
     *
     * @param initialPermutation the array given to derive all possible permutations of its elements
     *                           from it, including this given one
     * @param <T> a type of objects in the given array
     * @param func an operation to perform on each permutation
     */
    private static <T> void generate(final T[] initialPermutation, final Consumer<T[]> func) {
        final T[] aSequence = Arrays.copyOf(initialPermutation, initialPermutation.length);
        final int[] bTable = generateArrayOfIndicesOfSize(initialPermutation.length);
        final int[] cTable = new int[initialPermutation.length + 1];
        int k = 1, j = 1;
        while (true) {
            func.accept(Arrays.copyOf(aSequence, aSequence.length));
            k = 1;
            while (cTable[k] == k) cTable[k++] = 0;
            if (k == aSequence.length) break;
            cTable[k]++;
            swap(aSequence, 0, bTable[k]);
            j = 1;
            --k;
            while (j < k) VectorUtil.swap(bTable, k--, j++);
        }
    }

    private static int[] generateArrayOfIndicesOfSize(final int size) {
        final int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private static <T> void swap(final T[] array, final int i, final int j) {
        final T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
