/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class VariationsWithRepetitionsGenerator extends PermutationGenerator {

    private final int[] lastPermutation;
    private final int setPower;

    public VariationsWithRepetitionsGenerator(int powerOfASet, int lengthOfEachVariation) {
        if (powerOfASet < 0 ||  lengthOfEachVariation < 0) {
            throw new ArrayIndexOutOfBoundsException("Number of elements must be non-negative.");
        }
        if (lengthOfEachVariation > powerOfASet) {
            throw new IllegalArgumentException("lengthOfEachVariation > powerOfASet");
        }
        if (lengthOfEachVariation <= 0) {
            throw new IllegalArgumentException("lengthOfEachVariation <= 0");
        }
        setHasNext(true);
        this.lastPermutation = new int[lengthOfEachVariation];
        this.setPower = powerOfASet;
    }

    @Override
    public int[] next() {
        if (!hasNext())
            throw new NoSuchElementException(NO_NEXT_PERMUTATION.formatted(getClass()));
        var result = Arrays.copyOf(lastPermutation, lastPermutation.length);
        try {
            int i = lastPermutation.length - 1;
            for (; i >= 0; i--) {
                if (lastPermutation[i] < setPower - 1) {
                    break;
                }
            }
            if (i < 0) {
                setHasNext(false);
                throw new RuntimeException();
            }
            lastPermutation[i] += 1;
            i = i + 1;
            while (i < lastPermutation.length) {
                lastPermutation[i] = 0;
                i += 1;
            }
        } catch (Exception e) {

        }
        return result;
    }
}
