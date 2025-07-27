/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */
package algebra;

import org.junit.jupiter.api.Test;

import java.util.List;

import static algebra.EquationType.*;
import static algebra.EquationValidator.determinePolynomialEquationType;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@linkplain algebra.EquationValidator} class.
 *
 * @author Viacheslav Mikhailov
 */
public class EquationValidatorTest {

    @Test
    void given_linear_equations_when_check_for_type_assert_quadratic_and_not_anything_else() {
        var equation1 = new Equation(List.of(
                Member.builder().coefficient(-7).letter("x").power(1.0d).build(),
                Member.builder().coefficient(-7).letter("x").power(0.0d).build(),
                Member.builder().coefficient(4).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertEquals(LINEAR, determinePolynomialEquationType(equation1));
        assertNotEquals(QUADRATIC, determinePolynomialEquationType(equation1));
        assertNotEquals(CUBIC, determinePolynomialEquationType(equation1));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation1));

        var equation2 = new Equation(List.of(
                Member.builder().coefficient(7).letter("x").power(1.0d).build(),
                Member.builder().coefficient(17).letter("x").power(1.0d).build(),
                Member.builder().coefficient(27).letter("x").power(1.0d).build(),
                Member.builder().coefficient(37).letter("x").power(1.0d).build(),
                Member.builder().coefficient(7).letter("x").power(1.0d).build(),
                Member.builder().coefficient(12).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertEquals(LINEAR, determinePolynomialEquationType(equation2));
        assertNotEquals(QUADRATIC, determinePolynomialEquationType(equation2));
        assertNotEquals(CUBIC, determinePolynomialEquationType(equation2));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation2));

        var equation3 = new Equation(List.of(
                Member.builder().coefficient(3.2).letter("x").power(1.0d).build(),
                Member.builder().coefficient(-8.4).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertEquals(LINEAR, determinePolynomialEquationType(equation3));
        assertNotEquals(QUADRATIC, determinePolynomialEquationType(equation3));
        assertNotEquals(CUBIC, determinePolynomialEquationType(equation3));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation3));

        var equation4 = new Equation(List.of(
                Member.builder().coefficient(-14).letter("x").power(1.0d).build(),
                Member.builder().coefficient(-14).letter("x").power(1.0d).build(),
                Member.builder().coefficient(-80).letter("x").power(0.0d).build(),
                Member.builder().coefficient(-80).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertEquals(LINEAR, determinePolynomialEquationType(equation4));
        assertNotEquals(QUADRATIC, determinePolynomialEquationType(equation4));
        assertNotEquals(CUBIC, determinePolynomialEquationType(equation4));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation4));
    }

    @Test
    void given_quadratic_equations_when_check_for_type_assert_quadratic_and_not_anything_else() {
        var equation1 = new Equation(List.of(
                Member.builder().coefficient(3).letter("x").power(2.0d).build(),
                Member.builder().coefficient(-7).letter("x").power(1.0d).build(),
                Member.builder().coefficient(4).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertNotEquals(LINEAR, determinePolynomialEquationType(equation1));
        assertEquals(QUADRATIC, determinePolynomialEquationType(equation1));
        assertNotEquals(CUBIC, determinePolynomialEquationType(equation1));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation1));

        var equation2 = new Equation(List.of(
                Member.builder().coefficient(1).letter("x").power(2.0d).build(),
                Member.builder().coefficient(7).letter("x").power(1.0d).build(),
                Member.builder().coefficient(12).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertNotEquals(LINEAR, determinePolynomialEquationType(equation2));
        assertEquals(QUADRATIC, determinePolynomialEquationType(equation2));
        assertNotEquals(CUBIC, determinePolynomialEquationType(equation2));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation2));

        var equation3 = new Equation(List.of(
                Member.builder().coefficient(.6).letter("x").power(2.0d).build(),
                Member.builder().coefficient(3.2).letter("x").power(1.0d).build(),
                Member.builder().coefficient(-8.4).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertNotEquals(LINEAR, determinePolynomialEquationType(equation3));
        assertEquals(QUADRATIC, determinePolynomialEquationType(equation3));
        assertNotEquals(CUBIC, determinePolynomialEquationType(equation3));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation3));

        var equation4 = new Equation(List.of(
                Member.builder().coefficient(3).letter("x").power(2.0d).build(),
                Member.builder().coefficient(-14).letter("x").power(1.0d).build(),
                Member.builder().coefficient(-80).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertNotEquals(LINEAR, determinePolynomialEquationType(equation4));
        assertEquals(QUADRATIC, determinePolynomialEquationType(equation4));
        assertNotEquals(CUBIC, determinePolynomialEquationType(equation4));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation4));
    }

    @Test
    void given_cubic_equations_when_check_for_type_assert_cubic_and_not_anything_else() {
        var equation5 = new Equation(List.of(
                Member.builder().coefficient(1).letter("x").power(3.0d).build(),
                Member.builder().coefficient(5).letter("x").power(2.0d).build(),
                Member.builder().coefficient(-14).letter("x").power(1.0d).build(),
                Member.builder().coefficient(0).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertNotEquals(LINEAR, determinePolynomialEquationType(equation5));
        assertNotEquals(QUADRATIC, determinePolynomialEquationType(equation5));
        assertEquals(CUBIC, determinePolynomialEquationType(equation5));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation5));

        var equation6 = new Equation(List.of(
                Member.builder().coefficient(2).letter("x").power(3.0d).build(),
                Member.builder().coefficient(9).letter("x").power(2.0d).build(),
                Member.builder().coefficient(13).letter("x").power(1.0d).build(),
                Member.builder().coefficient(6).letter("x").power(0.0d).build()
        ), Member.asRealConstant(.0d));
        assertNotEquals(LINEAR, determinePolynomialEquationType(equation6));
        assertNotEquals(QUADRATIC, determinePolynomialEquationType(equation6));
        assertEquals(CUBIC, determinePolynomialEquationType(equation6));
        assertNotEquals(QUARTIC, determinePolynomialEquationType(equation6));
    }
}
