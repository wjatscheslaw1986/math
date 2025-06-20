/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static combinatorics.CombinatoricsUtil.getPrintIntArrayFunction;

/**
 * A utility class for generating all possible permutations of indices for an array of a
 * given length.
 *
 * @author Viacheslav Mikhailov
 */
public class JohnsonTrotterPermutationsGenerator {

    private JohnsonTrotterPermutationsGenerator() {
        // static context only
    }

    /**
     * Generate permutations of indices for <b>n</b> first indices of an array
     * and print these permutations to System::out.
     *
     * @param n length of an array of indices
     */
    public static void print(int n, OutputStream o) {
        generate(n, getPrintIntArrayFunction(o));
    }

    /**
     * Generate permutations of indices for <b>n</b> first indices of an array,
     * then collect and return these in a list.
     *
     * @param n length of an array of indices
     * @return list of all possible permutations of <b>n</b> indices
     */
    public static List<int[]> generate(int n) {
        final var list = new ArrayList<int[]>();
        generate(n, list::add);
        return list;
    }

    /**
     * Generate permutations of indices for <b>n</b> first indices of an array
     * and apply the given function to each permutation.
     *
     * @param n length of an array of indices
     * @param func function to apply to each permutation
     */
    public static void generate(final int n, final Consumer<int[]> func) {
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        final int[] permutation = new int[n];
        final int[] direction = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = i;
            direction[i] = -1;
        }
        func.accept(Arrays.copyOf(permutation, permutation.length));
        int mobileElementIndex = maxMobileElementIndex(permutation, direction);
        while (mobileElementIndex != -1) {
            int mobileElement = permutation[mobileElementIndex];
            int nextIndex = mobileElementIndex + direction[mobileElementIndex];
            swap(permutation, direction, mobileElementIndex, nextIndex);
            changeDirection(permutation, direction, mobileElement);
            func.accept(Arrays.copyOf(permutation, permutation.length));
            mobileElementIndex = maxMobileElementIndex(permutation, direction);
        }
    }

    private static int maxMobileElementIndex(int[] permutation, int[] direction) {
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

    private static void changeDirection(int[] permutation, int[] direction, int mobileElement) {
        for (int i = 0; i < permutation.length; i++) {
            if (permutation[i] > mobileElement) {
                direction[i] = direction[i] * (-1);
            }
        }
    }

    private static void swap(int[] permutation, int[] direction, int i, int j) {
        int temp = permutation[i];
        permutation[i] = permutation[j];
        permutation[j] = temp;

        int temp2 = direction[i];
        direction[i] = direction[j];
        direction[j] = temp2;
    }
}
