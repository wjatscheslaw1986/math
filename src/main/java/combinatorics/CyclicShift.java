/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static combinatorics.CombinatoricsUtil.getArrayOfIndicesForSize;
import static combinatorics.CombinatoricsUtil.getPrintIntArrayFunction;

/**
 * A utility class for generating all possible permutations of indices for an array of a
 * given length.
 * 
 * @author Viacheslav Mikhailov
 */
public final class CyclicShift {

    private CyclicShift() {
        // static context only
    }

    /**
     * Print permutations for a size of array to the given OutputStream.
     * 
     * @param size a size of an array of indices, starting with 0;
     * @param out  an implementation of the OutputStream
     */
    public static void print(final OutputStream out, final int size) {
        generate(size, getPrintIntArrayFunction(out, Arrays::toString));
    }

    /**
     * Print permutations for a size of array to the given OutputStream.
     *
     * @param size a size of an array of indices, starting with 0;
     * @param out  an implementation of the OutputStream
     */
    public static void printf(final OutputStream out, final int size, Function<int[], String> format) {
        generate(size, getPrintIntArrayFunction(out, format));
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
     */
    private static void generate(final int size, final Consumer<int[]> func) {
        if (size < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        final int[] array = getArrayOfIndicesForSize(size);
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

    static void leftShift(final int[] array, final int k) {
        int temp = array[0];
        for (int i = 0; i < k; i++)
            array[i] = array[i + 1];
        array[k] = temp;
    }

    public static int count(int n) {
        return CombinatoricsCalc.countPermutationsNoRepetition(n);
    }

}
