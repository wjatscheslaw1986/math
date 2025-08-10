/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package statistics;

import java.util.Arrays;

/**
 * A utility class with methods to calculate order statistic.
 *
 * @author Viacheslav Mikhailov
 */
public final class OrderStatisticCalc {

    private OrderStatisticCalc() {
        // static context only
    }

    /**
     * Find the k-th order statistic for the given unordered array of elements.
     *
     * @param k index of the element in the sorted sequence of the elements
     * @param elements the given unordered array of elements
     * @return the order statistic
     */
    public static int getOrderStatisticIndexForK(int k, int... elements) {
        if (k > elements.length)
            throw new IllegalArgumentException("Order statistics must have at least k + 2 elements");
        int[] sorted = new int[elements.length];
        System.arraycopy(elements, 0, sorted, 0, elements.length);
        Arrays.sort(sorted);
        for (int i = 0; i < elements.length; i++) {
            if (sorted[k - 1] == elements[i]) return elements[i];
        }
        throw new IllegalArgumentException("This should never happen");
    }
}
