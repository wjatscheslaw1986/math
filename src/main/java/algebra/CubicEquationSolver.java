package algebra;

import java.util.List;

/**
 *
 */
final class CubicEquationSolver {

    /**
     * Solves the given cubic equation.
     *
     * @param equation the equation given
     * @return cubic equation roots
     */
    static EquationRoots<Double> solve(final Equation equation) {

        if (equation.members().size() != 4)
            throw new IllegalArgumentException("Malformed cubic equation. Must have 4 members on the left.");

        var a = equation.members().get(0).getCoefficient();

        if (a == 0)
            throw new IllegalArgumentException("Malformed cubic equation ('a' cannot be zero).");

        var b = equation.members().get(1).getCoefficient();
        var c = equation.members().get(2).getCoefficient();
        var d = equation.members().get(3).getCoefficient();

        var twoA = 2 * a;

        double root1, root2, root3;

        // Normalize coefficients
        double p = b / a;
        double q = c / a;
        double r = d / a;

        // Depress the cubic: t^3 + pt^2 + qt + r = 0 becomes t^3 + at + b = 0
        double a_coeff = q - (p * p) / 3;
        double b_coeff = (2 * p * p * p - 9 * p * q + 27 * r) / 27;
        // Calculate discriminant
        var discriminant = (b_coeff * b_coeff / 4) + (a_coeff * a_coeff * a_coeff / 27);
        var discriminantSqrt = Math.sqrt(discriminant);

        if (discriminant > 0) {
            // One real root, two complex roots
            double u = Math.cbrt(-b_coeff / 2 + discriminantSqrt);
            double v = Math.cbrt(-b_coeff / 2 - discriminantSqrt);
            root1 = u + v - p / 3;
            double realPart = -(u + v) / 2 - p / 3;
            double imaginaryPart = (u - v) * Math.sqrt(3) / 2;
//            System.out.printf("One real root: x1 = %.4f%n", root1);
//            System.out.printf("Two complex roots: x2 = %.4f + %.4fi, x3 = %.4f - %.4fi%n",
//                    realPart, imaginaryPart, realPart, imaginaryPart);
            root2 = realPart + imaginaryPart;
            root3 = realPart - imaginaryPart;
        } else if (discriminant == 0) {
            // Three real roots, at least two are equal
            double u = Math.cbrt(-b_coeff / 2);
            root1 = 2 * u - p / 3;
            root2 = -u - p / 3;
            root3 = root1;
//            System.out.printf("Three real roots (at least two equal): x1 = %.4f, x2 = x3 = %.4f%n", root1, root2);
        } else {
            // Three distinct real roots
            double phi = Math.acos(-b_coeff / (2 * Math.sqrt(-a_coeff * a_coeff * a_coeff / 27)));
            double magnitude = 2 * Math.sqrt(-a_coeff / 3);
            root1 = magnitude * Math.cos(phi / 3) - p / 3;
            root2 = magnitude * Math.cos((phi + 2 * Math.PI) / 3) - p / 3;
            root3 = magnitude * Math.cos((phi + 4 * Math.PI) / 3) - p / 3;
//            System.out.printf("Three real roots: x1 = %.4f, x2 = %.4f, x3 = %.4f%n", root1, root2, root3);
        }
        return new EquationRoots<Double>(List.of(root1, root2, root3), discriminant);
    }
}
