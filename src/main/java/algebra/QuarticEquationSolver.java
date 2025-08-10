/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static approximation.RoundingUtil.isEffectivelyZero;

/**
 * A utility class for solving quartic equation. TODO not working.
 */
public class QuarticEquationSolver {

    /**
     * Find roots of the given equation.
     *
     * @param equation given equation
     * @return the roots
     */
    public static EquationRoots<Complex> solveQuartic(Equation equation) {
        // Extract coefficients
        Map<Double, Double> mapPowerOnCoefficient = new HashMap<>();
        if (equation.terms().isEmpty())
            throw new IllegalArgumentException("Empty equation");

        String letter = equation.terms().getFirst().getLetter().toString();
        for (Term term : equation.terms()) {
            if (!term.getLetter().toString().equals(letter))
                throw new IllegalArgumentException("Multiple variables in equation");

            double power = term.getPower();
            double coeff = term.getCoefficient();
            mapPowerOnCoefficient.put(power, coeff);
        }
        double constant = mapPowerOnCoefficient.getOrDefault(0.0, 0.0) - equation.equalsTo().getCoefficient();
        mapPowerOnCoefficient.put(0.0, constant);

        double a = mapPowerOnCoefficient.getOrDefault(4.0, 0.0);
        double b = mapPowerOnCoefficient.getOrDefault(3.0, 0.0);
        double c = mapPowerOnCoefficient.getOrDefault(2.0, 0.0);
        double d = mapPowerOnCoefficient.getOrDefault(1.0, 0.0);
        double e = mapPowerOnCoefficient.getOrDefault(0.0, 0.0);

        if (isEffectivelyZero(a))
            throw new IllegalArgumentException("Not a quartic equation (leading coefficient is zero)");

        // Depress the quartic
        double p = (8 * a * c - 3 * b * b) / (8 * a * a);
        double q = (b * b * b - 4 * a * b * c + 8 * a * a * d) / (8 * a * a * a);
        double r = (-3 * b * b * b * b + 256 * a * a * a * e - 64 * a * a * b * d + 16 * a * b * b * c) / (256 * a * a * a * a);

        // Resolvent cubic: m^3 - (p/2)m^2 - rm + (pr/2 - q^2/8) = 0
        List<Complex> mRoots = EquationUtil.solvePolynomial(Equation.of(List.of(
                Term.builder().letter("m").power(3.0d).coefficient(1.0d).build(),
                Term.builder().letter("m").power(2.0d).coefficient(-p / 2d).build(),
                Term.builder().letter("m").power(1.0d).coefficient(-r).build(),
                Term.builder().letter("m").power(.0d).coefficient((p * r / 2d) - (Math.pow(q, 2) / 8d)).build()
        ), Term.asRealConstant(.0d))).roots();

        // Use the first root m
        Complex m = mRoots.getFirst();
        Complex s = ComplexUtil.sqrt(ComplexUtil.subtract(
                ComplexUtil.multiply(Complex.of(2.0, 0.0), m),
                Complex.of(p, 0.0)
        ));

        // Form and solve the two quadratics
        Complex sOver2 = ComplexUtil.divide(s, Complex.of(2.0, 0.0));
        Complex qOver2s = ComplexUtil.divide(
                Complex.of(q, 0.0),
                ComplexUtil.multiply(Complex.of(2.0, 0.0), s));
        Complex aQuad = Complex.of(1.0, 0.0);

        List<Complex> yRoots = new ArrayList<>();
        yRoots.addAll(EquationUtil.solvePolynomial(Equation.of(List.of(
                Term.builder().letter("x").power(2.0d).coefficient(aQuad.real()).build(),
                Term.builder().letter("x").power(1.0d).coefficient(sOver2.real()).build(),
                Term.builder().letter("x").power(.0d).coefficient(ComplexUtil.add(m, qOver2s).real()).build()),
                Term.asRealConstant(.0d))).roots());
        yRoots.addAll(EquationUtil.solvePolynomial(Equation.of(List.of(
                Term.builder().letter("x").power(2.0d).coefficient(aQuad.real()).build(),
                Term.builder().letter("x").power(1.0d).coefficient(ComplexUtil.negate(sOver2).real()).build(),
                Term.builder().letter("x").power(.0d).coefficient(ComplexUtil.subtract(m, qOver2s).real()).build()),
                Term.asRealConstant(.0d))).roots());

        // Back-substitute: x = y - b/(4a)
        double shift = -b / (4 * a);
        List<Complex> xRoots = yRoots.stream()
                .map(y -> ComplexUtil.add(y, Complex.of(shift, 0.0)))
                .collect(Collectors.toList());

        // Compute discriminant
        double discriminant = computeQuarticDiscriminant(a, b, c, d, e);

        return EquationRoots.of(xRoots, discriminant);
    }

    private static double computeQuarticDiscriminant(double a, double b, double c, double d, double e) {
        double a2 = a * a, a3 = a2 * a;
        double b2 = b * b, b3 = b2 * b, b4 = b3 * b;
        double c2 = c * c, c3 = c2 * c, c4 = c3 * c;
        double d2 = d * d, d3 = d2 * d, d4 = d3 * d;
        double e2 = e * e, e3 = e2 * e;
        return 256 * a3 * e3 - 192 * a2 * b * d * e2 - 128 * a2 * c2 * e2 + 144 * a2 * c * d2 * e - 27 * a2 * d4
                + 144 * a * b2 * e2 - 6 * a * b * d2 * e - 80 * a * b * c * d * e + 18 * a * b * c3 - 4 * a * c3 * e
                + 16 * a * c4 - 27 * b4 * e2 + 18 * b3 * d * e - 4 * b3 * c3 - 4 * b2 * d3 + b2 * c2 * d2;
    }
}
