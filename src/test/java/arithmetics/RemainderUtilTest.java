/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package arithmetics;

import org.junit.jupiter.api.Test;

import static arithmetics.RemainderUtil.remainder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RemainderUtilTest {

    @Test
    void givenDividendAndDivisor_whenJavaRemainder_thenExpectedQuotient() {

        int a = 5, b = 3;

        // Remainder operation in Java is this: a%b = a - (a/b)*b

        assertEquals(a - (a/b)*b, a%b);
        assertEquals(a%b, remainder(a, b));
        assertEquals(2, remainder(a, b));

        // a = 5;
        b = -3;

        assertEquals(a - (a/b)*b, a%b);
        assertEquals(a%b, remainder(a, b));
        assertEquals(2, remainder(a, b));

        a = -5;
        b = 3;

        assertEquals(a - (a/b)*b, a%b);
        assertNotEquals(a%b, remainder(a, b));
        assertEquals(a%b, -remainder(a, b) - 1);
        assertEquals(1, remainder(a, b));

        // a = -5;
        b = -3;

        assertEquals(a - (a/b)*b, a%b);
        assertNotEquals(a%b, remainder(a, b));
        assertEquals(a%b, -remainder(a, b) -1);
        assertEquals(1, remainder(a, b));
    }

}
