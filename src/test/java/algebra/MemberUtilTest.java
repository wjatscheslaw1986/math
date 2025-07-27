/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@linkplain algebra.MemberUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class MemberUtilTest {

    @Test
    void given_two_members_when_sum_then_get_expected_result() {
        var a = Member.builder().letter("x").power(1.0).coefficient(1.0d).build();
        var b = Member.builder().letter("x").power(1.0).coefficient(2.0d).build();
        var c = Member.builder().letter("x").power(1.0).coefficient(3.0d).build();
        assertEquals(c, MemberUtil.add(a, b));

        a = Member.builder().letter("x").power(22.0).coefficient(11.0d).build();
        b = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(22.0).coefficient(33.0d).build();
        assertEquals(c, MemberUtil.add(a, b));

        a = Member.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        b = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(22.0).coefficient(11.0d).build();
        assertEquals(c, MemberUtil.add(a, b));

        var a1 = Member.builder().letter("x").power(22.0).coefficient(11.0d).build();
        var b1 = Member.builder().letter("x").power(21.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.add(a1, b1));

        var a2 = Member.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.add(a2, b2));
    }

    @Test
    void given_two_members_when_subtract_then_get_expected_result() {
        var a = Member.builder().letter("x").power(1.0).coefficient(1.0d).build();
        var b = Member.builder().letter("x").power(1.0).coefficient(2.0d).build();
        var c = Member.builder().letter("x").power(1.0).coefficient(-1.0d).build();
        assertEquals(c, MemberUtil.subtract(a, b));

        a = Member.builder().letter("x").power(22.0).coefficient(11.0d).build();
        b = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        assertEquals(c, MemberUtil.subtract(a, b));

        a = Member.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        b = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(22.0).coefficient(-33.0d).build();
        assertEquals(c, MemberUtil.subtract(a, b));

        var a1 = Member.builder().letter("x").power(22.0).coefficient(11.0d).build();
        var b1 = Member.builder().letter("x").power(21.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.subtract(a1, b1));

        var a2 = Member.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.subtract(a2, b2));
    }

    @Test
    void given_two_members_when_multiply_then_get_expected_result() {
        var a = Member.builder().letter("x").power(1.0).coefficient(1.0d).build();
        var b = Member.builder().letter("x").power(1.0).coefficient(2.0d).build();
        var c = Member.builder().letter("x").power(2.0).coefficient(2.0d).build();
        assertEquals(c, MemberUtil.multiply(a, b));

        a = Member.builder().letter("x").power(22.0d).coefficient(11.0d).build();
        b = Member.builder().letter("x").power(22.0d).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(44.0d).coefficient(11.0d * 22.0d).build();
        assertEquals(c, MemberUtil.multiply(a, b));

        a = Member.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        b = Member.builder().letter("x").power(21.0).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(43.0).coefficient(22.0d * -11.0d).build();
        assertEquals(c, MemberUtil.multiply(a, b));

        var a2 = Member.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.multiply(a2, b2));

        a = Member.builder().letter("x").power(13.0).coefficient(-11.0d).build();
        b = Member.asRealConstant(-1.0d);
        c = Member.builder().letter("x").power(13.0).coefficient(11.0d).build();
        assertEquals(c, MemberUtil.multiply(a, b));
    }

    @Test
    void given_two_members_when_divide_then_get_expected_result() {
        var a = Member.builder().letter("x").power(1.0).coefficient(1.0d).build();
        var b = Member.builder().letter("x").power(1.0).coefficient(2.0d).build();
        var c = Member.builder().letter("x").power(0.0).coefficient(.5d).build();
        assertEquals(c, MemberUtil.divide(a, b));

        a = Member.builder().letter("x").power(21.0d).coefficient(11.0d).build();
        b = Member.builder().letter("x").power(23.0d).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(-2.0d).coefficient(11.0d / 22.0d).build();
        assertEquals(c, MemberUtil.divide(a, b));

        a = Member.builder().letter("x").power(22.0d).coefficient(-11.0d).build();
        b = Member.builder().letter("x").power(23.0d).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(-1.0d).coefficient(-11.0d / 22.0d).build();
        assertEquals(c, MemberUtil.divide(a, b));

        var a2 = Member.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.divide(a2, b2));
    }
}
