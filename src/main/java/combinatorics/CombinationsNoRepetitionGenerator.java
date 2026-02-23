/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

public class CombinationsNoRepetitionGenerator extends IndexSequenceGenerator {

    private final int[] lastCombination;
    private final int k;

    /**
     * Constructor.
     *
     * @param n          size of the index sequence set
     * @param subsetSize size of every selection
     */
    public CombinationsNoRepetitionGenerator(final int n, final int subsetSize) {
        if (subsetSize < 0) throw new IllegalArgumentException("Subset size must be non-negative");
        if (subsetSize > n) throw new IllegalArgumentException("Subset size must be less than or equal to n");
        this.k = subsetSize;
        this.lastCombination = new int[k + 2];
        for (int i = 0; i < k; i++) {
            lastCombination[i] = i;
        }
        lastCombination[k] = n;
        lastCombination[k + 1] = 0;
        if (k > 0)
            setHasNext(true);
    }

    @Override
    public int[] next() {
        var result = cutFrom(lastCombination, k);
        int j = 0;
        for (; lastCombination[j] + 1 == lastCombination[j + 1]; j++) {
            lastCombination[j] = j;
        }
        if (j < k) {
            lastCombination[j]++;
        } else {
            setHasNext(false);
        }
        return result;
    }

    /**
     * Cuts off all the elements from the given array starting with the
     * given <i>index</i>, inclusive.
     *
     * @param array given
     * @param index the index since which we throw away elements from the given array
     * @return the shortened array
     */
    private static int[] cutFrom(final int[] array, final int index) {
        final int[] subArray = new int[index];
        System.arraycopy(array, 0, subArray, 0, index);
        return subArray;
    }
}
