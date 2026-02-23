/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static combinatorics.CombinatoricsUtil.getArrayOfIndicesForSize;
import static combinatorics.Narayana.*;
import static linear.spatial.VectorUtil.swap;

public class NarayanaPermutationsGenerator extends IndexSequenceGenerator {

    private final int[] lastPermutation;
    private int currentIndex;

    public NarayanaPermutationsGenerator(int size) {
        if (size < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        lastPermutation = getArrayOfIndicesForSize(size);
        currentIndex = findMaxIndexOfElementSmallerThanItsRightNeighbour(lastPermutation);
        if (size > 0)
            super.setHasNext(true);
    }

    @Override
    public int[] next() {
        if (super.hasNext() && currentIndex == -1) {
            super.setHasNext(false);
            return lastPermutation;
        }

        if (!hasNext())
            throw new NoSuchElementException(NO_NEXT_SEQUENCE.formatted(this.getClass()));
        var result = Arrays.copyOf(lastPermutation, lastPermutation.length);
        final int element = lastPermutation[currentIndex];
        int swapIndex = findMaxIndexOfElementBiggerThanGivenValue(lastPermutation, element);
        swap(lastPermutation, currentIndex, swapIndex);
        reverseOrderAfterIndex(lastPermutation, currentIndex);
        currentIndex = findMaxIndexOfElementSmallerThanItsRightNeighbour(lastPermutation);
        return result;
    }
}
