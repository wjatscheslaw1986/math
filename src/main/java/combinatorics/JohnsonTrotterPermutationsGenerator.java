/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static combinatorics.JohnsonTrotterUtil.changeDirection;
import static combinatorics.JohnsonTrotterUtil.maxMobileElementIndex;
import static combinatorics.NarayanaUtil.*;
import static linear.spatial.VectorUtil.swap;

public class JohnsonTrotterPermutationsGenerator extends PermutationGenerator {

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
        if (n > 0)
            super.setHasNext(true);
    }

    @Override
    public int[] next() {
        if (!hasNext())
            throw new NoSuchElementException(NO_NEXT_PERMUTATION.formatted(this.getClass()));
        var result = Arrays.copyOf(permutation, permutation.length);
        mobileElementIndex = maxMobileElementIndex(permutation, direction);
        int mobileElement = permutation[mobileElementIndex];
        int nextIndex = mobileElementIndex + direction[mobileElementIndex];
        JohnsonTrotterUtil.swap(permutation, direction, mobileElementIndex, nextIndex);
        changeDirection(permutation, direction, mobileElement);
        mobileElementIndex = maxMobileElementIndex(permutation, direction);
        if (mobileElementIndex == -1)
            super.setHasNext(false);
        return result;
    }
}
