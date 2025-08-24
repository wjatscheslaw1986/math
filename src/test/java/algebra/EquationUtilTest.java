/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

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
                Term.builder()
                        .coefficient(coefficients[0])
                        .value(1.0d)
                        .power(1.0d)
                        .build(),
                Term.builder()
                        .coefficient(coefficients[1])
                        .value(1.0d)
                        .power(1.0d)
                        .build(),
                Term.builder()
                        .coefficient(coefficients[2])
                        .power(1.0d)
                        .build(),
                Term.builder()
                        .coefficient(coefficients[3])
                        .power(1.0d)
                        .value(1.0d)
                        .build(),
                Term.builder()
                        .coefficient(coefficients[4])
                        .power(1.0d)
                        .value(1.0d)
                        .build(),
                Term.builder()
                        .coefficient(coefficients[5])
                        .power(1.0d)
                        .value(1.0d)
                        .build(),
                Term.builder()
                        .coefficient(coefficients[6])
                        .power(1.0d)
                        .value(1.0d)
                        .build(),
                Term.builder()
                        .coefficient(coefficients[7])
                        .power(1.0d)
                        .value(1.0d)
                        .build()
        ), Term.asRealConstant(8.05d));
        assertEquals(expected, toSingleVariableEquation(coefficients, index));
    }

    @Test
    void given_single_variable_linear_equation_as_Equation_find_its_solution() {
        double[] coefficients = new double[]{.0d, .0d, 4.0d, .0d, .0d, .05d, .0d, .0d, 8.05d};
        Equation equation = toSingleVariableEquation(coefficients, 2);
        assertNull(equation.getTermByIndex(2).getValue());
        EquationUtil.solveSingleVariableLinearEquation(equation);
        assertNotNull(equation.getTermByIndex(2).getValue());
        assertEquals(2d, equation.getTermByIndex(2).getValue());

        equation = toSingleVariableEquation(new double[]{5d, 20d}, 0);
        solveSingleVariableLinearEquation(equation);
        assertEquals(4d, equation.getTermByIndex(0).getValue());
    }

    @Test
    void given_two_terms_find_sum() {
        var given1 = Term.builder().coefficient(15).letter("a").build();
        var given2 = Term.builder().coefficient(20).letter("a").build();
        var sum = TermUtil.sum(given1, given2);
        assertEquals(35, sum.getCoefficient());
        assertEquals("a0", sum.getLetter().toString());
        assertEquals(1.0d, sum.getPower());
        assertNull(sum.getValue());

        given1 = Term.builder().coefficient(15).letter("b").build();
        given2 = Term.builder().coefficient(-20).letter("b").build();
        sum = TermUtil.sum(given1, given2);
        assertEquals(-5, sum.getCoefficient());
        assertEquals("b0", sum.getLetter().toString());
        assertEquals(1.0d, sum.getPower());
        assertNull(sum.getValue());
        assertEquals(sum.getCoefficient(), TermUtil.sum(given2, given1).getCoefficient());

        given1 = Term.builder().coefficient(15).letter("a").build();
        given2 = Term.builder().coefficient(20).letter("b").build();
        Term finalGiven1 = given1;
        Term finalGiven2 = given2;
        assertThrows(IllegalArgumentException.class, () -> TermUtil.sum(finalGiven1, finalGiven2));
        assertThrows(IllegalArgumentException.class, () -> TermUtil.sum(finalGiven2, finalGiven1));
    }

    @Test
    void given_list_of_terms_prepare_sorted_distinct_equation() {
        var given = List.of(
                Term.builder().coefficient(5).letter("a").build(),
                Term.builder().coefficient(5).letter("b").power(2.0d).build(),
                Term.builder().coefficient(22).letter("z").build(),
                Term.builder().coefficient(5).letter("a").power(2.0d).build(),
                Term.builder().coefficient(15).letter("a").build(),
                Term.builder().coefficient(2).letter("b").build(),
                Term.builder().coefficient(6).letter("c").build(),
                Term.builder().coefficient(1).letter("b").build(),
                Term.builder().coefficient(2).letter("c").build(),
                Term.builder().coefficient(10).letter("b").power(3.0d).build()
        );
        var expected = List.of(
                Term.builder().coefficient(5).letter("a").power(2.0d).build(),
                Term.builder().coefficient(20).letter("a").build(),
                Term.builder().coefficient(10).letter("b").power(3.0d).build(),
                Term.builder().coefficient(5).letter("b").power(2.0d).build(),
                Term.builder().coefficient(3).letter("b").build(),
                Term.builder().coefficient(8).letter("c").build(),
                Term.builder().coefficient(22).letter("z").build()
        );
        assertNotEquals(expected, given);
        assertFalse(EquationUtil.isDistinct(given));
        var distinctEquation = distinct(given);
        assertEquals(expected, distinctEquation);
        assertTrue(EquationUtil.isDistinct(distinctEquation));

        given = List.of(
                Term.builder().coefficient(136).power(.0d).build(),
                Term.builder().coefficient(1.0d).power(2.0d).build(),
                Term.builder().coefficient(-36).power(.0d).build(),
                Term.builder().coefficient(-8).build(),
                Term.builder().coefficient(-17).build()
        );

        expected = List.of(
                Term.builder().coefficient(1.0d).power(2.0d).build(),
                Term.builder().coefficient(-25).build(),
                Term.builder().coefficient(100).power(.0d).build()
        );
        assertFalse(EquationUtil.isDistinct(given));
        assertNotEquals(expected, given);
        distinctEquation = distinct(given);
        assertEquals(expected, distinctEquation);
        assertTrue(EquationUtil.isDistinct(distinctEquation));
    }

    @Test
    void givenLinearEquation_whenSolvePolynomial_thenExpectedRoots() {
        var equation = new Equation(List.of(
                Term.builder().coefficient(4).letter("x").power(1.0d).build(),
                Term.builder().coefficient(8).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));
        var roots = EquationUtil.solvePolynomial(equation);
        assertEquals(roots.roots().getFirst(), Complex.of(-2.0d, .0d));

        equation = new Equation(List.of(
                Term.builder().coefficient(-4).letter("x").power(1.0d).build(),
                Term.builder().coefficient(8).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));
        roots = EquationUtil.solvePolynomial(equation);
        assertEquals(roots.roots().getFirst(), Complex.of(2.0d, .0d));

        equation = new Equation(List.of(
                Term.builder().coefficient(-1).letter("x").power(1.0d).build(),
                Term.builder().coefficient(8).letter("x").power(0.0d).build(),
                Term.builder().coefficient(-1).letter("x").power(1.0d).build(),
                Term.builder().coefficient(-1).letter("x").power(1.0d).build(),
                Term.builder().coefficient(-1).letter("x").power(1.0d).build()
        ), Term.asRealConstant(.0d));
        roots = EquationUtil.solvePolynomial(equation);
        assertEquals(roots.roots().getFirst(), Complex.of(2.0d, .0d));
    }

    /**
     * Примеры взяты из "Справочник по Элементарной Математике" - М.Я.Выгодский - 1965 - стр.184
     */
    @Test
    void givenQuadraticEquation_whenSolvePolynomial_thenExpectedRoots() {
        var equation1 = new Equation(List.of(
                Term.builder().coefficient(3).letter("x").power(2.0d).build(),
                Term.builder().coefficient(-7).letter("x").power(1.0d).build(),
                Term.builder().coefficient(4).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));
        var expectedRoots1 = new EquationRoots<Complex>(
                List.of(
                        Complex.of(1.3333333333333333d, Double.NaN),
                        Complex.of(1d, Double.NaN)),
                1d);
        var solution1 = EquationUtil.solvePolynomial(equation1);
        assertEquals(expectedRoots1, solution1);

        var equation2 = new Equation(List.of(
                Term.builder().coefficient(1).letter("x").power(2.0d).build(),
                Term.builder().coefficient(7).letter("x").power(1.0d).build(),
                Term.builder().coefficient(12).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));
        var expectedRoots2 = new EquationRoots<Complex>(
                List.of(
                        Complex.of(-3.0d, Double.NaN),
                        Complex.of(-4.0d, Double.NaN)),
                1d);
        var solution2 = EquationUtil.solvePolynomial(equation2);
        assertEquals(expectedRoots2, solution2);

        var equation3 = new Equation(List.of(
                Term.builder().coefficient(.6).letter("x").power(2.0d).build(),
                Term.builder().coefficient(3.2).letter("x").power(1.0d).build(),
                Term.builder().coefficient(-8.4).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));
        var expectedRoots3 = new EquationRoots<Complex>(
                List.of(
                        Complex.of(1.928016250696741d, Double.NaN),
                        Complex.of(-7.261349584030074d, Double.NaN)),
                (-3.2 * -3.2 - 4 * .60 * (-8.4)));
        var solution3 = EquationUtil.solvePolynomial(equation3);
        assertEquals(expectedRoots3, solution3);

        var equation4 = new Equation(List.of(
                Term.builder().coefficient(3).letter("x").power(2.0d).build(),
                Term.builder().coefficient(-14).letter("x").power(1.0d).build(),
                Term.builder().coefficient(-80).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));
        var expectedRoots4 = new EquationRoots<Complex>(
                List.of(
                        Complex.of(8d, Double.NaN),
                        Complex.of(-1d * (10d / 3d), Double.NaN)),
                (-14.0d * -14.0d - 4d * 3d * -80d));
        var solution4 = EquationUtil.solvePolynomial(equation4);
        assertEquals(expectedRoots4, solution4);

    }

    /**
     * Примеры взяты из "Справочник по Элементарной Математике" - М.Я.Выгодский - 1965 - стр.184
     */
    @Test
    void givenCubicEquation_whenSolvePolynomial_thenExpectedRoots() {
        var equation5 = new Equation(List.of(
                Term.builder().coefficient(1).letter("x").power(3.0d).build(),
                Term.builder().coefficient(5).letter("x").power(2.0d).build(),
                Term.builder().coefficient(-14).letter("x").power(1.0d).build(),
                Term.builder().coefficient(0).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));
        var expectedRoots5 = new EquationRoots<Complex>(List.of(
                Complex.of(2d, Double.NaN),
                Complex.of(-7d, Double.NaN),
                Complex.of(.0d, Double.NaN)),
                -147d);
        var solution5 = EquationUtil.solvePolynomial(equation5);
        assertEquals(expectedRoots5, solution5);

        var equation6 = new Equation(List.of(
                Term.builder().coefficient(2).letter("x").power(3.0d).build(),
                Term.builder().coefficient(9).letter("x").power(2.0d).build(),
                Term.builder().coefficient(13).letter("x").power(1.0d).build(),
                Term.builder().coefficient(6).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));
        var expectedRoots6 = new EquationRoots<Complex>(List.of(
                Complex.of(-1d, Double.NaN),
                Complex.of(-2d, Double.NaN),
                Complex.of(-1.5d, Double.NaN)),
                -.0005787037d);
        var solution6 = EquationUtil.solvePolynomial(equation6);
        assertEquals(expectedRoots6, solution6);
    }

    @Test
    void givenQuarticEquation_whenSolvePolynomial_thenExpectedRoots() {
        var equation6 = new Equation(List.of(
                Term.builder().coefficient(1).letter("x").power(4.0d).build(),
                Term.builder().coefficient(-2).letter("x").power(3.0d).build(),
                Term.builder().coefficient(-3).letter("x").power(2.0d).build(),
                Term.builder().coefficient(4).letter("x").power(1.0d).build(),
                Term.builder().coefficient(4).letter("x").power(0.0d).build()
        ), Term.asRealConstant(.0d));

        var roots = QuarticEquationSolver.solveQuartic(equation6);
        System.out.println(roots);
    }

    @Test
    void given_a_2x2_matrix_when_toCharacteristicPolynomial_then_get_expected_Equation() {
        var matrix = new double[][]{
                {.5d, -.5d},
                {-.5d, .5d}
        };

        var expected = Equation.of(List.of(
                Term.builder().coefficient(1.0d).letter("x").power(2.0d).build(),
                Term.builder().coefficient(-1.0d).letter("x").power(1.0d).build(),
                Term.builder().coefficient(.0d).letter("x").power(.0d).build()
        ), Term.asRealConstant(.0d));

        var result = toCharacteristicPolynomial(matrix);

        assertEquals(expected, result);
    }

    @Test
    void given_a_3x3_matrix_when_toCharacteristicPolynomial_then_get_expected_Equation() {
        var matrix = new double[][]{
                {3, -1, 1},
                {0, 2, -5},
                {1, -1, 2}
        };

        var expected = Equation.of(List.of(
                Term.builder().coefficient(-1.0d).letter("x").power(3.0d).build(),
                Term.builder().coefficient(7).letter("x").power(2.0d).build(),
                Term.builder().coefficient(-10).letter("x").power(1.0d).build(),
                Term.builder().coefficient(0).letter("x").power(.0d).build()
        ), Term.asRealConstant(0));

        assertEquals(expected, toCharacteristicPolynomial(matrix));
    }

    @Test
    void given_a_4x4_matrix_when_toCharacteristicPolynomial_then_get_expected_Equation() {
        var matrix = new double[][]{
                {2.0d, 3.0d, 5.0d, 3.0d},
                {2.0d, 5.0d, 2.0d, 2.0d},
                {1.0d, 6.0d, 2.0d, 2.0d},
                {66.0d, 6.0d, 2.0d, 4.0d}
        };
        assertEquals(4, toCharacteristicPolynomial(matrix).terms().getFirst().getPower());

        matrix = new double[][]{
                {2.0d, 3.0d, 5.0d, 3.0d, 1.0d},
                {2.0d, 5.0d, 2.0d, 2.0d, 2.0d},
                {1.0d, 6.0d, 2.0d, 2.0d, 2.0d},
                {66.0d, 6.0d, 2.0d, 4.0d, 2.0d},
                {1.0d, 3.0d, 2.0d, 9.0d, 6.0d}
        };
        assertEquals(5, toCharacteristicPolynomial(matrix).terms().getFirst().getPower());
    }
}
