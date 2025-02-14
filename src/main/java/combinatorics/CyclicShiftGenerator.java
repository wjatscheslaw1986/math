/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static combinatorics.CombinatoricsUtil.getPrintArrayFunction;

/**
 * A utility class for generating all possible permutations of values of a given array.
 * 
 * @author Wjatscheslaw Michailov
 */
public final class CyclicShiftGenerator {

    private CyclicShiftGenerator() {
        super();
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
        if (size < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        final int[] array = generateArrayOfIndicesOfSize(size);
        final int lastIndex = array.length - 1;
        int k = lastIndex;
        func.accept(Arrays.copyOf(array, array.length));
        while (k > 0) {
            leftShift(array, k);
            if (array[k] != k) {
                func.accept(Arrays.copyOf(array, array.length));
                k = lastIndex;
            } else
                k--;
        }
    }

    private static void leftShift(final int[] array, final int k) {
        int temp = array[0];
        for (int i = 0; i < k; i++)
            array[i] = array[i + 1];
        array[k] = temp;
    }

    private static int[] generateArrayOfIndicesOfSize(final int size) {
        final int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
}
