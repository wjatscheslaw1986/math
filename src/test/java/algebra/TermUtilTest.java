/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@linkplain TermUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class TermUtilTest {

    @Test
    void given_two_terms_when_sum_then_get_expected_result() {
        var a = Term.builder().letter("x").power(1.0).coefficient(1.0d).build();
        var b = Term.builder().letter("x").power(1.0).coefficient(2.0d).build();
        var c = Term.builder().letter("x").power(1.0).coefficient(3.0d).build();
        assertEquals(c, TermUtil.sum(a, b));

        a = Term.builder().letter("x").power(22.0).coefficient(11.0d).build();
        b = Term.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Term.builder().letter("x").power(22.0).coefficient(33.0d).build();
        assertEquals(c, TermUtil.sum(a, b));

        a = Term.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        b = Term.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Term.builder().letter("x").power(22.0).coefficient(11.0d).build();
        assertEquals(c, TermUtil.sum(a, b));

        var a1 = Term.builder().letter("x").power(22.0).coefficient(11.0d).build();
        var b1 = Term.builder().letter("x").power(21.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> TermUtil.sum(a1, b1));

        var a2 = Term.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Term.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> TermUtil.sum(a2, b2));
    }

    @Test
    void given_two_terms_when_subtract_then_get_expected_result() {
        var a = Term.builder().letter("x").power(1.0).coefficient(1.0d).build();
        var b = Term.builder().letter("x").power(1.0).coefficient(2.0d).build();
        var c = Term.builder().letter("x").power(1.0).coefficient(-1.0d).build();
        assertEquals(c, TermUtil.subtract(a, b));

        a = Term.builder().letter("x").power(22.0).coefficient(11.0d).build();
        b = Term.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Term.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        assertEquals(c, TermUtil.subtract(a, b));

        a = Term.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        b = Term.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Term.builder().letter("x").power(22.0).coefficient(-33.0d).build();
        assertEquals(c, TermUtil.subtract(a, b));

        var a1 = Term.builder().letter("x").power(22.0).coefficient(11.0d).build();
        var b1 = Term.builder().letter("x").power(21.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> TermUtil.subtract(a1, b1));

        var a2 = Term.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Term.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> TermUtil.subtract(a2, b2));
    }

    @Test
    void given_two_terms_when_multiply_then_get_expected_result() {
        var a = Term.builder().letter("x").power(1.0).coefficient(1.0d).build();
        var b = Term.builder().letter("x").power(1.0).coefficient(2.0d).build();
        var c = Term.builder().letter("x").power(2.0).coefficient(2.0d).build();
        assertEquals(c, a.multiply(b));

        a = Term.builder().letter("x").power(22.0d).coefficient(11.0d).build();
        b = Term.builder().letter("x").power(22.0d).coefficient(22.0d).build();
        c = Term.builder().letter("x").power(44.0d).coefficient(11.0d * 22.0d).build();
        assertEquals(c, a.multiply(b));

        a = Term.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        b = Term.builder().letter("x").power(21.0).coefficient(22.0d).build();
        c = Term.builder().letter("x").power(43.0).coefficient(22.0d * -11.0d).build();
        assertEquals(c, a.multiply(b));

        var a2 = Term.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Term.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> TermUtil.multiply(a2, b2));

        a = Term.builder().letter("x").power(13.0).coefficient(-11.0d).build();
        b = Term.asRealConstant(-1.0d);
        c = Term.builder().letter("x").power(13.0).coefficient(11.0d).build();
        assertEquals(c, a.multiply(b));
    }

    @Test
    void given_two_terms_when_divide_then_get_expected_result() {
        var a = Term.builder().letter("x").power(1.0).coefficient(1.0d).build();
        var b = Term.builder().letter("x").power(1.0).coefficient(2.0d).build();
        var c = Term.builder().letter("x").power(0.0).coefficient(.5d).build();
        assertEquals(c, TermUtil.divide(a, b));

        a = Term.builder().letter("x").power(21.0d).coefficient(11.0d).build();
        b = Term.builder().letter("x").power(23.0d).coefficient(22.0d).build();
        c = Term.builder().letter("x").power(-2.0d).coefficient(11.0d / 22.0d).build();
        assertEquals(c, TermUtil.divide(a, b));

        a = Term.builder().letter("x").power(22.0d).coefficient(-11.0d).build();
        b = Term.builder().letter("x").power(23.0d).coefficient(22.0d).build();
        c = Term.builder().letter("x").power(-1.0d).coefficient(-11.0d / 22.0d).build();
        assertEquals(c, TermUtil.divide(a, b));

        var a2 = Term.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Term.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> TermUtil.divide(a2, b2));
    }


}
