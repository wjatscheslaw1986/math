/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

public class CyclicShiftPermutationsGenerator extends IndexSequenceGenerator {

    private final int[] lastPermutation;
    private int currentIndex;

    public CyclicShiftPermutationsGenerator(final int n) {
        if (n < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        this.lastPermutation = new int[n];
        this.currentIndex = lastPermutation.length - 1;
        if (this.currentIndex > 0)
            setHasNext(true);
    }

    @Override
    public int[] next() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
