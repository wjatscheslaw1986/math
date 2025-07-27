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
final class QuadraticEquationSolver {

    /**
     * Solves the given quadratic equation.
     *
     * @param equation equation the equation given
     * @return quadratic equation roots
     */
    static EquationRoots<Complex> solve(final Equation equation) {
        Objects.requireNonNull(equation, "Equation cannot be null");
        if (equation.members().size() != 3)
            throw new IllegalArgumentException("Malformed quadratic equation. Must have 3 members on the left side of the equation.");

        var a = equation.members().get(0).getCoefficient();

        if (isEffectivelyZero(a))
            throw new IllegalArgumentException("Malformed quadratic equation ('a' cannot be zero).");

        var b = equation.members().get(1).getCoefficient();
        var c = equation.members().get(2).getCoefficient();

        // Calculate discriminant
        var discriminant = b * b - 4 * a * c;
        var twoA = 2 * a;

        // Calculate roots based on discriminant
        if (discriminant > .0d) {
            // Two distinct real roots
            var discriminantRoot = Math.sqrt(discriminant);
            var root1 = (-b + discriminantRoot) / twoA;
            var root2 = (-b - discriminantRoot) / twoA;
            return new EquationRoots<Complex>(List.of(
                    Complex.of(root1, Double.NaN),
                    Complex.of(root2, Double.NaN)
            ), discriminant);
        } else if (discriminant == .0d) {
            // One real root (repeated)
            var root = -b / twoA;
            return new EquationRoots<Complex>(List.of(
                    Complex.of(root, Double.NaN),
                    Complex.of(root, Double.NaN)
            ), discriminant);
        } else {
            // Complex roots
            var realPart = -b / twoA;
            var imaginaryPart = Math.sqrt(-discriminant) / twoA;
            return new EquationRoots<Complex>(List.of(
                    Complex.of(realPart, imaginaryPart),
                    Complex.of(realPart, -imaginaryPart)
            ), discriminant);
        }
    }
}
