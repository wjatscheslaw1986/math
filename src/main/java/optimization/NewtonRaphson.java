/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import exception.MathException;

import java.util.function.DoubleUnaryOperator;

public class NewtonRaphson {
    private static final double DEFAULT_ACCURACY = 1e-10;
    private static final int DEFAULT_MAX_ITERATIONS = 100;
    private static final double THRESHOLD_VALUE = 1e-12;

    /**
     * Implements the Newton-Raphson (Newton's) method to find a root of the equation
     * {@code f(x) = 0} using the provided function and its derivative.
     *
     * <p>The method starts with an initial guess at the midpoint of the given interval
     * and iteratively improves the approximation using the formula:
     * <pre>{@code x_{n+1} = x_n - f(x_n) / f'(x_n)}</pre>
     *
     * <p><strong>Note:</strong> This implementation does not check for convergence failure.
     * It may run indefinitely (infinite loop) if the method does not converge or if
     * the derivative becomes zero. For production use, consider adding a maximum
     * iteration limit.
     *
     * @param interval      a two-element array containing the lower and upper bounds
     *                      of the search interval. Only the midpoint is used as the
     *                      initial guess. Must not be null and must have length 2.
     * @param function      the function {@code f(x)} whose root we are seeking
     * @param derivative    the derivative {@code f'(x)} of the function
     * @param accuracy      stopping criterion: |x_{n+1} - x_n| ≤ accuracy
     * @param maxIterations maximum allowed iterations before we decide that the method does not converge
     * @return an approximation of the root
     * @throws IllegalArgumentException if interval or parameters are invalid
     * @throws NullPointerException     if function or derivative is null
     * @throws MathException            on division by near-zero derivative or non-convergence
     */
    public static double findEquationRoot(final double[] interval,
                                          final DoubleUnaryOperator function,
                                          final DoubleUnaryOperator derivative,
                                          final double accuracy,
                                          final int maxIterations) throws MathException {
        if (interval == null || interval.length != 2) {
            throw new IllegalArgumentException("Interval must contain at least two elements");
        }
        if (function == null || derivative == null) {
            throw new NullPointerException("Function and derivative must not be null");
        }
        if (accuracy <= 0) {
            throw new IllegalArgumentException("Accuracy must be positive");
        }
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations must be positive");
        }

        double x = (interval[1] + interval[0]) / 2;
        double x1 = x;
        int iteration = 0;

        double intervalLength = Math.abs(interval[1] - interval[0]);
        double maxStep = Math.max(intervalLength * 0.5, 1.0); // sensible minimum

        // Initial check
        double f0 = function.applyAsDouble(x);
        double df0 = derivative.applyAsDouble(x);
        if (Math.abs(df0) < THRESHOLD_VALUE) {
            throw new MathException(
                    String.format("Derivative near zero at initial guess x = %.8f", x));
        }

        if (Math.abs(f0) < accuracy) {
            return x; // already a root
        }

        do {
            x = x1;

            double f = function.applyAsDouble(x);
            double df = derivative.applyAsDouble(x);

            if (Math.abs(df) < THRESHOLD_VALUE)
                // TODO fallback to bisection
                throw new MathException(
                        String.format("Derivative near zero at x = %.8f (iteration %d)", x, iteration));

            double currentMaxStep = Math.max(maxStep, Math.abs(x) * 0.5); // adaptive max step is relative to current x
            double step = Math.max(Math.min(f / df, currentMaxStep), -currentMaxStep); // limit the step size

            x1 = x - step;
            if (++iteration > maxIterations) {
                throw new MathException(
                        String.format("Newton's method failed to converge after %d iterations. Last x = %.10f",
                                maxIterations, x1));
            }
        } while (Math.abs(x1 - x) > accuracy);

        return x1;
    }

    /**
     * Convenience overload using default accuracy and iteration limit.
     *
     * @see #findEquationRoot(double[], DoubleUnaryOperator, DoubleUnaryOperator, double, int)
     */
    public static double findEquationRoot(final double[] interval,
                                          final DoubleUnaryOperator function,
                                          final DoubleUnaryOperator derivative) throws MathException {
        return findEquationRoot(interval, function, derivative, DEFAULT_ACCURACY, DEFAULT_MAX_ITERATIONS);
    }
}
