/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */
package combinatorics;

import java.util.Iterator;

/**
 * A generator of INT arrays that are meant to be index sequences.
 *
 * @author Viacheslav Mikhailov
 */
public abstract class IndexSequenceGenerator implements Iterator<int[]> {

    static final String NO_NEXT_SEQUENCE = "No more index sequences available [%s]";

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
