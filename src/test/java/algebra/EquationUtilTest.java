/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static algebra.EquationUtil.solveSingleVariableLinearEquation;
import static algebra.EquationUtil.toSingleVariableEquation;
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
        Equation expected = new Equation(new ArrayDeque<>(List.of(
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
        )), new AtomicReference<Double>(8.05d));
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
    void given_array_of_doubles_clean_it_of_negative_zeroes() {
        Double[] coefficients = new Double[]{.0d, -.0d, 4.0d, .0d, -.0d, .05d, -0.0d, .0d, 8.05d};
        Double[] expected = new Double[]{.0d, .0d, 4.0d, .0d, .0d, .05d, 0.0d, .0d, 8.05d};
        EquationUtil.cleanDoubleArrayOfNegativeZeros(coefficients);
        assertArrayEquals(expected, coefficients);
    }

}
