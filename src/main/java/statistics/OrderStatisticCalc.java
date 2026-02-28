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
            throw new IllegalArgumentException("Order statistic must have at least k + 2 elements");
        if (k < 0)
            throw new ArrayIndexOutOfBoundsException("Negative order statistic index %d".formatted(k));
        int[] sorted = new int[elements.length];
        System.arraycopy(elements, 0, sorted, 0, elements.length);
        Arrays.sort(sorted);
        for (int element : elements) {
            if (sorted[k - 1] == element) return element;
        }
        throw new IllegalArgumentException("This should never happen");
    }
}
