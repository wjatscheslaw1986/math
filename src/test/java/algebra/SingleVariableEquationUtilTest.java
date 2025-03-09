/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static algebra.SingleVariableEquationUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@linkplain SingleVariableEquationUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class SingleVariableEquationUtilTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", SingleVariableEquationUtilTest.class, System.lineSeparator());
    }

    @Test
    void given_single_variable_equation_members_list_gather_them_in_another_list_distinct_by_power_of_the_variable() {
        var given = List.of(
                Member.builder().coefficient(3.0d).power(1.0d).build(),
                Member.builder().coefficient(5.0d).power(2.0d).build(),
                Member.builder().coefficient(6.0d).power(3.0d).build(),
                Member.builder().coefficient(6.0d).power(.0d).build(),
                Member.builder().coefficient(5.0d).power(2.0d).build(),
                Member.builder().coefficient(7.0d).power(.0d).build(),
                Member.builder().coefficient(5.0d).power(-1.0d).build(),
                Member.builder().coefficient(1.0d).power(.0d).build(),
                Member.builder().coefficient(8.0d).power(.0d).build(),
                Member.builder().coefficient(5.0d).power(2.0d).build(),
                Member.builder().coefficient(-1.0d).power(2.0d).build(),
                Member.builder().coefficient(9.0d).power(.0d).build(),
                Member.builder().coefficient(2.0d).power(3.0d).build(),
                Member.builder().coefficient(12.0d).power(-3.0d).build(),
                Member.builder().coefficient(10.0d).power(.0d).build()
        );

        var expected = "8.0x^3.0 14.0x^2.0 3.0x 41.0 5.0x^-1.0 12.0x^-3.0";
        var result = distinctByPower(given).stream()
                .map(SingleVariableEquationUtil::textify).reduce((a, b) ->
                        a.concat(" ").concat(b)).orElseThrow();
//        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    void given_4x_power_2_and_5x_power_2_assert_their_sum_9x_power_2() {
        var givenX1 = Member.builder().power(2.0d).coefficient(5.0d).build();
        var givenX2 = Member.builder().power(2.0d).coefficient(4.0d).build();
        var expected = Member.builder().power(2.0d).coefficient(9.0d).build();
        assertEquals(expected, sum(givenX1, givenX2));
        assertEquals(expected, sum(givenX2, givenX1));
    }

    @Test
    void given_4x_power_2_and_5x_power_2_assert_their_product_20x_power_4() {
        var givenX1 = Member.builder().power(2.0d).coefficient(5.0d).build();
        var givenX2 = Member.builder().power(2.0d).coefficient(4.0d).build();
        var expected = Member.builder().power(4.0d).coefficient(20.0d).build();
        assertEquals(expected, multiply(givenX1, givenX2));
        assertEquals(expected, multiply(givenX2, givenX1));
    }

}
