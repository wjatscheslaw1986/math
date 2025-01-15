/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatoric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class for generating all possible permutations of values of a given array.
 * 
 * @author Wjatscheslaw Michailov
 */
public final class CyclicShiftIndexPermutationsGenerator {

    private CyclicShiftIndexPermutationsGenerator() {
        super();
    }

    /**
     * @return Number of possible permutations.
     */
    public static long getNumberOfPossiblePermutations(int arrayLength) {
        return factorial(arrayLength);
    }

    /**
     * Print permutations for a size of array.
     * 
     * @param size a size of array of indices, starting with 0;
     * @return list of arrays of all possible permutations of values in the given array.
     */
    public static void print(final int size) {
        var result = new ArrayList<int[]>();
        final int[] array = generateArrayOfSize(size);
        int k = array.length - 1;
        int n = k;
        System.out.println(Arrays.toString(array));
        for (; k > 0;) {
            leftShift(array, k);
            if (array[k] != k) {
                System.out.println(Arrays.toString(array));
                k = n;
            } else
                k--;
        }
    }
    
    /**
     * Generate permutations for a size of array.
     * 
     * @param size a size of array of indices, starting with 0;
     * @return list of arrays of all possible permutations of values in the given array.
     */
    public static List<int[]> generate(final int size) {
        var result = new ArrayList<int[]>();
        final int[] array = generateArrayOfSize(size);
        int k = array.length - 1;
        int n = k;
        storePermutationInList(result, array);
        for (; k > 0;) {
            leftShift(array, k);
            if (array[k] != k) {
                storePermutationInList(result, array);
                k = n;
            } else
                k--;
        }
        return result;
    }

    private static void leftShift(int[] array, int k) {
        int temp = array[0];
        for (int i = 0; i < k; i++)
            array[i] = array[i + 1];
        array[k] = temp;
    }

    private static void storePermutationInList(final List<int[]> list, int[] currentPermutation) {
        final int[] copy = new int[currentPermutation.length];
        System.arraycopy(currentPermutation, 0, copy, 0, currentPermutation.length);
        list.add(copy);
    }

    private static long factorial(final int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private static int[] generateArrayOfSize(final int size) {
        final int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
}
