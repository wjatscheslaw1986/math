/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package statistics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link statistics.OrderStatisticCalc} class.
 *
 * @author Viacheslav Mikhailov
 */
public class OrderStatisticCalcTest {

    @Test
    void givenArrayOfIntegersAndK_whenFindKthOrderStatistic_thenGetExpectedOrderStatisticIndex() {
        int[] sequence = new int[] { 4, 1, 10, 9, 7, 12, 8, 2, 15 };
        assertEquals(1, OrderStatisticCalc.getOrderStatisticIndexForK(1,  sequence));
        assertEquals(2, OrderStatisticCalc.getOrderStatisticIndexForK(2,  sequence));
        assertEquals(4, OrderStatisticCalc.getOrderStatisticIndexForK(3,  sequence));
        assertEquals(7, OrderStatisticCalc.getOrderStatisticIndexForK(4,  sequence));
        assertEquals(8, OrderStatisticCalc.getOrderStatisticIndexForK(5,  sequence));
        assertEquals(9, OrderStatisticCalc.getOrderStatisticIndexForK(6,  sequence));
        assertEquals(10, OrderStatisticCalc.getOrderStatisticIndexForK(7,  sequence));
        assertEquals(12, OrderStatisticCalc.getOrderStatisticIndexForK(8,  sequence));
        assertEquals(15, OrderStatisticCalc.getOrderStatisticIndexForK(9,  sequence));
    }
}
