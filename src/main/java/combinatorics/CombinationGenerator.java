/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */
package combinatorics;

import java.util.Iterator;

public abstract class CombinationGenerator implements Iterator<int[]> {

    static final String NO_NEXT_COMBINATION = "No more combinations available [%s]";

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
