/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static algebra.EquationUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@linkplain algebra.EquationUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class EquationUtilTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", EquationUtilTest.class,
                System.lineSeparator());
    }

    @Test
    void given_single_variable_linear_equation_find_its_solution() {
        double[] coefficients = new double[]{.0d, .0d, 4.0d, .0d, .0d, .05d, .0d, .0d, 8.05d};
        assertEquals(2, EquationUtil.solveSingleVariableLinearEquation(coefficients, 2));

        coefficients = new double[]{.0d, 1.0d, 1.0d};
        assertEquals(1, EquationUtil.solveSingleVariableLinearEquation(coefficients, 1));
    }

    @Test
    void given_equation_as_an_array_of_coefficients_and_a_right_part_convert_it_to_Equation() {
        double[] coefficients = new double[]{.0d, .0d, 4.0d, .0d, .0d, .05d, .0d, .0d, 8.05d};
        int index = 2;
        Equation expected = new Equation(List.of(
                Member.builder()
                        .coefficient(coefficients[0])
                        .value(1.0d)
                        .power(1.0d)
                        .build(),
                Member.builder()
                        .coefficient(coefficients[1])
                        .value(1.0d)
                        .power(1.0d)
                        .build(),
                Member.builder()
                        .coefficient(coefficients[2])
                        .power(1.0d)
                        .build(),
                Member.builder()
                        .coefficient(coefficients[3])
                        .power(1.0d)
                        .value(1.0d)
                        .build(),
                Member.builder()
                        .coefficient(coefficients[4])
                        .power(1.0d)
                        .value(1.0d)
                        .build(),
                Member.builder()
                        .coefficient(coefficients[5])
                        .power(1.0d)
                        .value(1.0d)
                        .build(),
                Member.builder()
                        .coefficient(coefficients[6])
                        .power(1.0d)
                        .value(1.0d)
                        .build(),
                Member.builder()
                        .coefficient(coefficients[7])
                        .power(1.0d)
                        .value(1.0d)
                        .build()
        ), new AtomicReference<Double>(8.05d));
        assertEquals(expected, toSingleVariableEquation(coefficients, index));
    }

    @Test
    void given_single_variable_linear_equation_as_Equation_find_its_solution() {
        double[] coefficients = new double[]{.0d, .0d, 4.0d, .0d, .0d, .05d, .0d, .0d, 8.05d};
        Equation equation = toSingleVariableEquation(coefficients, 2);
        assertNull(equation.getMemberByIndex(2).getValue());
        EquationUtil.solveSingleVariableLinearEquation(equation);
        assertNotNull(equation.getMemberByIndex(2).getValue());
        assertEquals(2d, equation.getMemberByIndex(2).getValue());

        equation = toSingleVariableEquation(new double[]{5d, 20d}, 0);
        solveSingleVariableLinearEquation(equation);
        assertEquals(4d, equation.getMemberByIndex(0).getValue());
    }

    @Test
    void given_two_members_find_sum() {
        var given1 = Member.builder().coefficient(15).letter("a").build();
        var given2 = Member.builder().coefficient(20).letter("a").build();
        var sum = EquationUtil.sum(given1, given2);
        assertEquals(35, sum.getCoefficient());
        assertEquals("a0", sum.getLetter().toString());
        assertEquals(1.0d, sum.getPower());
        assertNull(sum.getValue());

        given1 = Member.builder().coefficient(15).letter("b").build();
        given2 = Member.builder().coefficient(-20).letter("b").build();
        sum = EquationUtil.sum(given1, given2);
        assertEquals(-5, sum.getCoefficient());
        assertEquals("b0", sum.getLetter().toString());
        assertEquals(1.0d, sum.getPower());
        assertNull(sum.getValue());
        assertEquals(sum.getCoefficient(), EquationUtil.sum(given2, given1).getCoefficient());

        given1 = Member.builder().coefficient(15).letter("a").build();
        given2 = Member.builder().coefficient(20).letter("b").build();
        Member finalGiven1 = given1;
        Member finalGiven2 = given2;
        assertThrows(IllegalArgumentException.class, () -> EquationUtil.sum(finalGiven1, finalGiven2));
        assertThrows(IllegalArgumentException.class, () -> EquationUtil.sum(finalGiven2, finalGiven1));
    }

    @Test
    void given_list_of_members_prepare_sorted_distinct_equation() {
        var given = List.of(
                Member.builder().coefficient(5).letter("a").build(),
                Member.builder().coefficient(5).letter("b").power(2.0d).build(),
                Member.builder().coefficient(22).letter("z").build(),
                Member.builder().coefficient(5).letter("a").power(2.0d).build(),
                Member.builder().coefficient(15).letter("a").build(),
                Member.builder().coefficient(2).letter("b").build(),
                Member.builder().coefficient(6).letter("c").build(),
                Member.builder().coefficient(1).letter("b").build(),
                Member.builder().coefficient(2).letter("c").build(),
                Member.builder().coefficient(10).letter("b").power(3.0d).build()
        );
        var expected = List.of(
                Member.builder().coefficient(5).letter("a").power(2.0d).build(),
                Member.builder().coefficient(20).letter("a").build(),
                Member.builder().coefficient(10).letter("b").power(3.0d).build(),
                Member.builder().coefficient(5).letter("b").power(2.0d).build(),
                Member.builder().coefficient(3).letter("b").build(),
                Member.builder().coefficient(8).letter("c").build(),
                Member.builder().coefficient(22).letter("z").build()
        );
        assertNotEquals(expected, given);
        var distinctEquation = distinct(given);
        assertEquals(expected, distinctEquation);
        assertTrue(EquationUtil.isDistinct(distinctEquation));

        given = List.of(
                Member.builder().coefficient(136).power(.0d).build(),
                Member.builder().coefficient(1.0d).power(2.0d).build(),
                Member.builder().coefficient(-36).power(.0d).build(),
                Member.builder().coefficient(-8).build(),
                Member.builder().coefficient(-17).build()
                );

        expected = List.of(
                Member.builder().coefficient(1.0d).power(2.0d).build(),
                Member.builder().coefficient(-25).build(),
                Member.builder().coefficient(100).power(.0d).build()
        );
        assertNotEquals(expected, given);
        distinctEquation = distinct(given);
        assertEquals(expected, distinctEquation);
        assertTrue(EquationUtil.isDistinct(distinctEquation));
    }

    /**
     * Примеры взяты из "Справочник по Элементарной Математике" - М.Я.Выгодский - 1965 - стр.184
     */
    @Test
    void given_quadratic_equation_when_solve_then_expected_roots() {
        var equation1 = new Equation(List.of(
                Member.builder().coefficient(3).letter("x").power(2.0d).build(),
                Member.builder().coefficient(-7).letter("x").power(1.0d).build(),
                Member.builder().coefficient(4).letter("x").power(0.0d).build()
        ), new AtomicReference<Double>(.0d));
        var expectedRoots1 = new EquationRoots<Double>(List.of(1.3333333333333333d,  1d), 1d);
        var solution1 = EquationUtil.solveEquation(equation1);
        assertTrue(solution1.isPresent());
        assertEquals(expectedRoots1, solution1.get());

        var equation2 = new Equation(List.of(
                Member.builder().coefficient(1).letter("x").power(2.0d).build(),
                Member.builder().coefficient(7).letter("x").power(1.0d).build(),
                Member.builder().coefficient(12).letter("x").power(0.0d).build()
        ), new AtomicReference<Double>(.0d));
        var expectedRoots2 = new EquationRoots<Double>(List.of(-3.0d,  -4.0d), 1d);
        var solution2 = EquationUtil.solveEquation(equation2);
        assertTrue(solution2.isPresent());
        assertEquals(expectedRoots2, solution2.get());

        var equation3 = new Equation(List.of(
                Member.builder().coefficient(.6).letter("x").power(2.0d).build(),
                Member.builder().coefficient(3.2).letter("x").power(1.0d).build(),
                Member.builder().coefficient(-8.4).letter("x").power(0.0d).build()
        ), new AtomicReference<Double>(.0d));
        var expectedRoots3 = new EquationRoots<Double>(List.of(1.928016250696741d,  -7.261349584030074d), (-3.2 * -3.2 - 4 * .60 * (-8.4)));
        var solution3 = EquationUtil.solveEquation(equation3);
        assertTrue(solution3.isPresent());
        assertEquals(expectedRoots3, solution3.get());

        var equation4 = new Equation(List.of(
                Member.builder().coefficient(3).letter("x").power(2.0d).build(),
                Member.builder().coefficient(-14).letter("x").power(1.0d).build(),
                Member.builder().coefficient(-80).letter("x").power(0.0d).build()
        ), new AtomicReference<Double>(.0d));
        var expectedRoots4 = new EquationRoots<Double>(List.of(8d,  -1d*(10d/3d)), (-14.0d * -14.0d - 4d * 3d * -80d));
        var solution4 = EquationUtil.solveEquation(equation4);
        assertTrue(solution4.isPresent());
        assertEquals(expectedRoots4, solution4.get());

        var equation5 = new Equation(List.of(
                Member.builder().coefficient(1).letter("x").power(3.0d).build(),
                Member.builder().coefficient(5).letter("x").power(2.0d).build(),
                Member.builder().coefficient(-14).letter("x").power(1.0d).build(),
                Member.builder().coefficient(0).letter("x").power(0.0d).build()
        ), new AtomicReference<Double>(.0d));
        var expectedRoots5 = new EquationRoots<Double>(List.of(2d, -7d, 0d), -147d);
        var solution5 = EquationUtil.solveEquation(equation5);
        assertTrue(solution5.isPresent());
        assertEquals(expectedRoots5, solution5.get());

        var equation6 = new Equation(List.of(
                Member.builder().coefficient(2).letter("x").power(3.0d).build(),
                Member.builder().coefficient(9).letter("x").power(2.0d).build(),
                Member.builder().coefficient(13).letter("x").power(1.0d).build(),
                Member.builder().coefficient(6).letter("x").power(0.0d).build()
        ), new AtomicReference<Double>(.0d));
        var expectedRoots6 = new EquationRoots<Double>(List.of(-1d, -2d, -1.5d), -.0005787037d);
        var solution6 = EquationUtil.solveEquation(equation6);
        assertTrue(solution6.isPresent());
        assertEquals(expectedRoots6, solution6.get());
    }
}
