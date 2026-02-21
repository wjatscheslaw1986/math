/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static combinatorics.CombinatoricsUtil.generateArrayOfIndicesOfSize;
import static combinatorics.JohnsonTrotter.maxMobileElementIndex;

public class JohnsonTrotterPermutationsGenerator extends IndexSequenceGenerator {

    private final int[] lastPermutation;
    private final int[] direction;
    private int mobileElementIndex;

    public JohnsonTrotterPermutationsGenerator(int n) {
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        lastPermutation = generateArrayOfIndicesOfSize(n);
        direction = new int[n];
        for (int i = 0; i < n; i++) {
            direction[i] = -1;
        }
        mobileElementIndex = maxMobileElementIndex(lastPermutation, direction);
        if (n > 0)
            super.setHasNext(true);
    }

    @Override
    public int[] next() {
        if (!super.hasNext())
            throw new NoSuchElementException(NO_NEXT_SEQUENCE.formatted(this.getClass()));
        var result = Arrays.copyOf(lastPermutation, lastPermutation.length);

        if (mobileElementIndex == -1) {
            super.setHasNext(false);
            return result;
        }

        int mobileElement = lastPermutation[mobileElementIndex];
        int nextIndex = mobileElementIndex + direction[mobileElementIndex];
        JohnsonTrotter.swap(lastPermutation, direction, mobileElementIndex, nextIndex);
        JohnsonTrotter.changeDirection(lastPermutation, direction, mobileElement);
        mobileElementIndex = maxMobileElementIndex(lastPermutation, direction);
        return result;
    }
}
