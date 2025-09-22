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
        assertEquals(8, OrderStatisticCalc.getOrderStatisticIndexForK(5,  sequence));
    }
}
