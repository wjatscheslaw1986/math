package algebra;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
final class QuarticEquationSolver {

    /**
     * Solves the given cubic equation.
     *
     * @param equation the equation given
     * @return cubic equation roots
     */
    public static List<Double> solveQuartic(double[] coeffs) {
        List<Double> roots = new ArrayList<>();
        if (coeffs.length != 5) {
            throw new IllegalArgumentException("Array must contain exactly 5 coefficients");
        }

        double a = coeffs[0], b = coeffs[1], c = coeffs[2], d = coeffs[3], e = coeffs[4];

        // Handle case where a=0 (not a quartic)
        if (Math.abs(a) < 1e-10) {
            throw new IllegalArgumentException("Leading coefficient cannot be zero");
        }

        // Normalize coefficients
        b /= a; c /= a; d /= a; e /= a; a = 1.0;

        // Depressed quartic: t^4 + pt^2 + qt + r = 0
        double p = c - (3.0 * b * b / 8.0);
        double q = d + (b * b * b / 8.0) - (b * c / 2.0);
        double r = e - (b * b * b * b / 256.0) + (b * b * c / 16.0) - (b * d / 4.0);

        // Solve the resolvent cubic: y^3 + py^2 + qy + r = 0
        double p_cubic = -p / 2.0;
        double q_cubic = (p * p - 4.0 * r) / 8.0;
        double r_cubic = (-q * q) / 8.0;

        // Solve cubic using Cardano's method
        double Q = (p_cubic * p_cubic - 3.0 * q_cubic) / 9.0;
        double R = (2.0 * p_cubic * p_cubic * p_cubic - 9.0 * p_cubic * q_cubic + 27.0 * r_cubic) / 54.0;
        double D = Q * Q * Q - R * R;

        double y;
        if (D >= 0) {
            double theta = Math.acos(R / Math.sqrt(Q * Q * Q));
            y = -2.0 * Math.sqrt(Q) * Math.cos(theta / 3.0) - p_cubic / 3.0;
        } else {
            double A = Math.pow(Math.abs(R) + Math.sqrt(-D), 1.0 / 3.0);
            double B = (R > 0 ? A : -A);
            y = (B + Q / B) - p_cubic / 3.0;
        }

        // Use y to factor the quartic into two quadratics
        double m = Math.sqrt(y);
        double n = q / (2.0 * m);
        double p1 = p / 2.0 + y / 2.0;
        double p2 = p / 2.0 - y / 2.0;

        // Solve the two quadratics
        double sqrt1 = Math.sqrt(m * m - 4.0 * (p1 + n));
        double sqrt2 = Math.sqrt(m * m - 4.0 * (p2 - n));

        // Quadratic 1: x^2 + mx + (p1 + n) = 0
        double disc1 = m * m - 4.0 * (p1 + n);
        if (disc1 >= 0) {
            roots.add((-m + Math.sqrt(disc1)) / 2.0 - b / 4.0);
            roots.add((-m - Math.sqrt(disc1)) / 2.0 - b / 4.0);
        }

        // Quadratic 2: x^2 - mx + (p2 - n) = 0
        double disc2 = m * m - 4.0 * (p2 - n);
        if (disc2 >= 0) {
            roots.add((m + Math.sqrt(disc2)) / 2.0 - b / 4.0);
            roots.add((m - Math.sqrt(disc2)) / 2.0 - b / 4.0);
        }

        return roots;
    }
}
