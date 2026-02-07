/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static combinatorics.NarayanaUtil.*;
import static linear.spatial.VectorUtil.swap;

public class NarayanaPermutationsGenerator extends PermutationGenerator {

    private final int[] lastPermutation;
    private int currentIndex;

    public NarayanaPermutationsGenerator(int size) {
        lastPermutation = new int[size];
        for (int i = 0; i < size; i++) {
            lastPermutation[i] = i;
        }
        currentIndex = findMaxIndexOfElementSmallerThanItsRightNeighbour(lastPermutation);
        if (size > 0)
            super.setHasNext(true);
    }

    @Override
    public int[] next() {
        if (!hasNext())
            throw new NoSuchElementException(NO_NEXT_PERMUTATION.formatted(this.getClass()));
        var result = Arrays.copyOf(lastPermutation, lastPermutation.length);
        final int element = lastPermutation[currentIndex];
        int swapIndex = findMaxIndexOfElementBiggerThanGivenValue(lastPermutation, element);
        swap(lastPermutation, currentIndex, swapIndex);
        reverseOrderAfterIndex(lastPermutation, currentIndex);
        currentIndex = findMaxIndexOfElementSmallerThanItsRightNeighbour(lastPermutation);
        if (currentIndex == -1)
            super.setHasNext(false);
        return result;
    }
}
