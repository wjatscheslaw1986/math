/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.List;
import java.util.Objects;

import static approximation.RoundingUtil.isEffectivelyZero;

/**
 * A utility class for solving linear equations.
 *
 * @author Viacheslav Mikhailov
 */
final class LinearEquationSolver {

    /**
     * Solves the given linear equation.
     *
     * @param equation the equation given
     * @return linear equation roots
     */
    static EquationRoots<Complex> solve(final Equation equation) {
        Objects.requireNonNull(equation, "Equation cannot be null");
        if (equation.terms().size() != 2)
            throw new IllegalArgumentException("Malformed linear equation. Must have 2 terms on the left side of the equation.");

        var a = equation.terms().get(0).getCoefficient();

        if (isEffectivelyZero(a))
            throw new IllegalArgumentException("Malformed quadratic equation ('a' cannot be zero).");

        var b = equation.terms().get(1).getCoefficient() - equation.equalsTo().getCoefficient();

        return EquationRoots.of(List.of(Complex.of(-b/a, .0d)), Double.NaN);
    }
}
