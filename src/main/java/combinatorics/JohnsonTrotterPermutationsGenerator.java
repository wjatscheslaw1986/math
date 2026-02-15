/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static combinatorics.JohnsonTrotterUtil.maxMobileElementIndex;

public class JohnsonTrotterPermutationsGenerator extends CombinationGenerator {

    private final int[] permutation;
    private final int[] direction;
    private int mobileElementIndex;

    public JohnsonTrotterPermutationsGenerator(int n) {
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        permutation = new int[n];
        direction = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = i;
            direction[i] = -1;
        }
        mobileElementIndex = maxMobileElementIndex(permutation, direction);
        if (n > 0)
            super.setHasNext(true);
    }

    @Override
    public int[] next() {
        if (!super.hasNext())
            throw new NoSuchElementException(NO_NEXT_COMBINATION.formatted(this.getClass()));
        var result = Arrays.copyOf(permutation, permutation.length);

        if (mobileElementIndex == -1) {
            super.setHasNext(false);
            return result;
        }

        int mobileElement = permutation[mobileElementIndex];
        int nextIndex = mobileElementIndex + direction[mobileElementIndex];
        JohnsonTrotterUtil.swap(permutation, direction, mobileElementIndex, nextIndex);
        JohnsonTrotterUtil.changeDirection(permutation, direction, mobileElement);
        mobileElementIndex = maxMobileElementIndex(permutation, direction);
        return result;
    }
}
