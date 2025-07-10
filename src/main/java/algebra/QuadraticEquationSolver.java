package algebra;

import java.util.List;

/**
 *
 */
final class QuadraticEquationSolver {

    /**
     * Solves the given quadratic equation.
     *
     * @param equation equation the equation given
     * @return quadratic equation roots
     */
    static EquationRoots<Double> solve(final Equation equation) {

        if (equation.members().size() != 3)
            throw new IllegalArgumentException("Malformed quadratic equation. Must have 3 members on the left side of the equation.");

        var a = equation.members().get(0).getCoefficient();

        if (a == 0)
            throw new IllegalArgumentException("Malformed quadratic equation ('a' cannot be zero).");

        var b = equation.members().get(1).getCoefficient();
        var c = equation.members().get(2).getCoefficient();

        // Calculate discriminant
        var discriminant = b * b - 4 * a * c;
        var twoA = 2 * a;

        double root1, root2;

        // Calculate roots based on discriminant
        if (discriminant > 0) {
            // Two distinct real roots
            var discriminantRoot = Math.sqrt(discriminant);
            root1 = (-b + discriminantRoot) / twoA;
            root2 = (-b - discriminantRoot) / twoA;
//            System.out.printf("Two real roots: x1 = %.4f, x2 = %.4f%n", root1, root2);
        } else if (discriminant == 0) {
            // One real root (repeated)
            root1 = -b / twoA;
            root2 = root1;
//            System.out.printf("One real root: x = %.4f%n", root1);
        } else {
            // Complex roots
            var realPart = -b / twoA;
            var imaginaryPart = Math.sqrt(-discriminant) / twoA;
            root1 = realPart + imaginaryPart;
            root2 = realPart - imaginaryPart;
        }
        return new EquationRoots<Double>(List.of(root1, root2), discriminant);
    }
}
