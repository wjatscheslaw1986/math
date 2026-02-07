/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */
package combinatorics;

import java.util.Iterator;

public abstract class PermutationGenerator implements Iterator<int[]> {

    static final String NO_NEXT_PERMUTATION = "No more permutations available [%s]";

    private boolean hasNext = false;

    public abstract int[] next();

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
