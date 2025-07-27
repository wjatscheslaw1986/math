/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.List;
import java.util.Objects;

import static approximation.RoundingUtil.isEffectivelyZero;

/**
 * A utility class for solving quadratic equation.
 *
 * @author Viacheslav Mikhailov
 */
final class LinearEquationSolver {

    /**
     * Solves the given quadratic equation.
     *
     * @param equation equation the equation given
     * @return quadratic equation roots
     */
    static EquationRoots<Complex> solve(final Equation equation) {
        Objects.requireNonNull(equation, "Equation cannot be null");
        if (equation.members().size() != 2)
            throw new IllegalArgumentException("Malformed linear equation. Must have 2 members on the left side of the equation.");

        var a = equation.members().get(0).getCoefficient();

        if (isEffectivelyZero(a))
            throw new IllegalArgumentException("Malformed quadratic equation ('a' cannot be zero).");

        var b = equation.members().get(1).getCoefficient() - equation.equalsTo().getCoefficient();

        return EquationRoots.of(List.of(Complex.of(-b/a, .0d)), Double.NaN);
    }
}
