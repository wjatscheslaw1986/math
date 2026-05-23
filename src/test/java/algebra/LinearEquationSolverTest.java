/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package algebra;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link LinearEquationSolver}.
 */
public class LinearEquationSolverTest {

    @Test
    void givenLinearEquation_whenSolve_thenExpectedRoot() {
        var equation = new Equation(List.of(
                Term.builder().coefficient(2).letter("x").power(1.0d).build(),
                Term.asRealConstant(6.0d)
        ), Term.asRealConstant(12.0d));
        var roots = LinearEquationSolver.solve(equation).roots();
        assertEquals(1, roots.size());
        assertEquals(3.0d, roots.getFirst().real(), .00001d);
        assertEquals(.0d, roots.getFirst().imaginary(), .00001d);
    }
}
