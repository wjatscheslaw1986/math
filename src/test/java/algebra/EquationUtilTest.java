/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
