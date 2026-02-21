/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import linear.spatial.VectorUtil;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static combinatorics.CombinatoricsUtil.generateArrayOfIndicesOfSize;

public class EhrlichPermutationsGenerator extends IndexSequenceGenerator {

    private final int[] lastPermutation;
    final int[] bTable;
    final int[] cTable;

    public EhrlichPermutationsGenerator(int size) {
        if (size < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        lastPermutation = new int[size];
        for (int i = 0; i < size; i++) {
            lastPermutation[i] = i;
        }
        bTable = generateArrayOfIndicesOfSize(lastPermutation.length);
        cTable = new int[lastPermutation.length + 1];

        if (size > 0)
            super.setHasNext(true);
    }

    @Override
    public int[] next() {
        if (!super.hasNext())
            throw new NoSuchElementException(NO_NEXT_SEQUENCE.formatted(this.getClass()));
        var result = Arrays.copyOf(lastPermutation, lastPermutation.length);
        int k = 1;
        while (cTable[k] == k) cTable[k++] = 0;
        if (k != lastPermutation.length) {
        cTable[k]++;
        VectorUtil.swap(lastPermutation, 0, bTable[k]);
        int j = 1;
        --k;
        while (j < k) VectorUtil.swap(bTable, k--, j++);
        } else {
            setHasNext(false);
        }
        return result;
    }
}
