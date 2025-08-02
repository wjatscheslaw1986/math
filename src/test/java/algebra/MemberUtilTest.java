/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import org.junit.jupiter.api.Test;

import java.util.List;

import static algebra.MemberUtil.openBrackets;
import static algebra.MemberUtil.toCharacteristicPolynomial;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals(c, MemberUtil.sum(a, b));

        a = Member.builder().letter("x").power(22.0).coefficient(11.0d).build();
        b = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(22.0).coefficient(33.0d).build();
        assertEquals(c, MemberUtil.sum(a, b));

        a = Member.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        b = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(22.0).coefficient(11.0d).build();
        assertEquals(c, MemberUtil.sum(a, b));

        var a1 = Member.builder().letter("x").power(22.0).coefficient(11.0d).build();
        var b1 = Member.builder().letter("x").power(21.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.sum(a1, b1));

        var a2 = Member.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.sum(a2, b2));
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
        assertEquals(c, a.multiply(b));

        a = Member.builder().letter("x").power(22.0d).coefficient(11.0d).build();
        b = Member.builder().letter("x").power(22.0d).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(44.0d).coefficient(11.0d * 22.0d).build();
        assertEquals(c, a.multiply(b));

        a = Member.builder().letter("x").power(22.0).coefficient(-11.0d).build();
        b = Member.builder().letter("x").power(21.0).coefficient(22.0d).build();
        c = Member.builder().letter("x").power(43.0).coefficient(22.0d * -11.0d).build();
        assertEquals(c, a.multiply(b));

        var a2 = Member.builder().letter("y").power(22.0).coefficient(11.0d).build();
        var b2 = Member.builder().letter("x").power(22.0).coefficient(22.0d).build();
        assertThrows(IllegalArgumentException.class, () -> MemberUtil.multiply(a2, b2));

        a = Member.builder().letter("x").power(13.0).coefficient(-11.0d).build();
        b = Member.asRealConstant(-1.0d);
        c = Member.builder().letter("x").power(13.0).coefficient(11.0d).build();
        assertEquals(c, a.multiply(b));
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

    @Test
    void given_list_of_lists_of_members_when_multiply_then_get_expect_opening_brackets() {
        var given = Members.of(List.of(
                Members.of(List.of(
                        Member.builder().coefficient(5).letter("a").power(2.0d).build(),
                        Member.builder().coefficient(20).letter("a").power(1.0d).build()
                )).multiply(Members.of(List.of(
                        Member.builder().coefficient(1).letter("a").power(2.0d).build(),
                        Member.builder().coefficient(3).letter("a").power(3.0d).build()
                ))))).asList();

        var expectedPolynomialAsSumAfterOpeningBrackets = List.of(
                Member.builder().coefficient(5).letter("a").power(4.0d).build(),
                Member.builder().coefficient(15).letter("a").power(5.0d).build(),
                Member.builder().coefficient(20).letter("a").power(3.0d).build(),
                Member.builder().coefficient(60).letter("a").power(4.0d).build());

        assertEquals(expectedPolynomialAsSumAfterOpeningBrackets, given);
    }

    @Test
    void given_a_matrix_when_toCharacteristicPolynomial_then_get_expected_Equation1() {
        var matrix = new double[][]{
                {.5d, -.5d},
                {-.5d, .5d}
        };

        var expected = Equation.of(List.of(
                Member.builder().coefficient(1.0d).letter("x").power(2.0d).build(),
                Member.builder().coefficient(-1.0d).letter("x").power(1.0d).build()
        ), Member.asRealConstant(.0d));

        var result = toCharacteristicPolynomial(matrix);

        assertEquals(expected, result);
    }

    @Test
    void given_a_matrix_when_toCharacteristicPolynomial_then_get_expected_Equation() {
        var matrix = new double[][]{
                {3, -1, 1},
                {0, 2, -5},
                {1, -1, 2}
        };

        var expected = Equation.of(List.of(
                Member.builder().coefficient(-1.0d).letter("x").power(3.0d).build(),
                Member.builder().coefficient(7).letter("x").power(2.0d).build(),
                Member.builder().coefficient(-10).letter("x").power(1.0d).build()
               ), Member.asRealConstant(0));

        assertEquals(expected, toCharacteristicPolynomial(matrix));
    }
}
