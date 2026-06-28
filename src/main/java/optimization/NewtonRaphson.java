/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import exception.MathException;

import java.util.function.DoubleUnaryOperator;

public class NewtonRaphson {
    public static final String INTERVAL_MUST_CONTAIN_AT_LEAST_TWO_ELEMENTS = "Interval must contain at least two elements";
    public static final String FUNCTION_AND_DERIVATIVE_MUST_NOT_BE_NULL = "Function and derivative must not be null";
    public static final String ACCURACY_MUST_BE_POSITIVE = "Accuracy must be positive";
    public static final String MAX_ITERATIONS_MUST_BE_POSITIVE = "maxIterations must be positive";
    private static final double DEFAULT_ACCURACY = 1e-10;
    private static final int DEFAULT_MAX_ITERATIONS = 100;
    private static final double THRESHOLD_VALUE = 1e-12;
    private static final String DERIVATIVE_NEAR_ZERO_AT_INITIAL_GUESS_X_8_F = "Derivative near zero at initial guess x = %.8f";
    private static final String DERIVATIVE_NEAR_ZERO_AT_X_AT_NTH_ITERATION = "Derivative near zero at x = %.8f (iteration %d)";
    private static final String NEWTON_S_METHOD_FAILED_TO_CONVERGE_AFTER_D_ITERATIONS = "Newton's method failed to converge after %d iterations. Last x = %.10f";

    /**
     * Implements the Newton-Raphson (Newton's) method to find a root of the equation
     * {@code f(x) = 0}.
     *
     * <p>The method starts with an initial guess at the midpoint of the provided interval
     * and iteratively refines the approximation using the update formula:
     * <pre>{@code x_{n+1} = x_n - f(x_n) / f'(x_n)}</pre>
     *
     * <p><strong>Note on finding extrema:</strong> This method can also be used to locate
     * local minima or maxima of a function by passing its first derivative as the
     * {@code function} parameter and its second derivative as the {@code derivative}
     * parameter. In this case, it finds a root of the first derivative (i.e., a critical point).
     *
     * <p>The implementation includes safeguards against common failure modes:
     * <ul>
     *   <li>Division by near-zero derivative values</li>
     *   <li>Excessive step sizes (with adaptive limiting)</li>
     *   <li>Non-convergence within the iteration limit</li>
     * </ul>
     *
     * @param interval      a two-element array {@code [a, b]} defining the search interval.
     *                      Only the midpoint {@code (a + b) / 2} is used as the initial guess.
     *                      The interval bounds themselves are not otherwise enforced.
     *                      Must not be null and must have exactly length 2.
     * @param function      the function {@code f(x)} (or its derivative when finding extrema)
     * @param derivative    the derivative {@code f'(x)} of the function (or the second derivative
     *                      when finding extrema)
     * @param accuracy      the convergence criterion: the iteration stops when
     *                      {@code |x_{n+1} - x_n| ≤ accuracy}. Must be positive.
     * @param maxIterations maximum number of iterations allowed. If exceeded, a
     *                      {@link MathException} is thrown. Must be positive.
     * @return an approximation of the root (or critical point) within the specified accuracy
     * @throws IllegalArgumentException if {@code interval} is invalid, {@code accuracy <= 0},
     *                                  or {@code maxIterations <= 0}
     * @throws NullPointerException     if {@code function} or {@code derivative} is null
     * @throws MathException            if the derivative is near zero at any evaluation point,
     *                                  or if the method fails to converge within {@code maxIterations}
     * @see #findEquationRoot(double[], DoubleUnaryOperator, DoubleUnaryOperator)
     */
    public static double findEquationRoot(final double[] interval,
                                          final DoubleUnaryOperator function,
                                          final DoubleUnaryOperator derivative,
                                          final double accuracy,
                                          final int maxIterations) throws MathException {
        // Input validation
        if (interval == null || interval.length != 2) {
            throw new IllegalArgumentException(INTERVAL_MUST_CONTAIN_AT_LEAST_TWO_ELEMENTS);
        }
        if (function == null || derivative == null) {
            throw new NullPointerException(FUNCTION_AND_DERIVATIVE_MUST_NOT_BE_NULL);
        }
        if (accuracy <= 0) {
            throw new IllegalArgumentException(ACCURACY_MUST_BE_POSITIVE);
        }
        if (maxIterations <= 0) {
            throw new IllegalArgumentException(MAX_ITERATIONS_MUST_BE_POSITIVE);
        }

        // Initial guess: midpoint of the interval
        double x = (interval[1] + interval[0]) / 2.0d;
        double x1 = x;
        int iteration = 0;

        double intervalLength = Math.abs(interval[1] - interval[0]);
        double maxStep = Math.max(intervalLength * 0.5d, 1.0d); // sensible minimum

        // Initial check
        double f0 = function.applyAsDouble(x);
        double df0 = derivative.applyAsDouble(x);
        if (Math.abs(df0) < THRESHOLD_VALUE) {
            throw new MathException(
                    String.format(DERIVATIVE_NEAR_ZERO_AT_INITIAL_GUESS_X_8_F, x));
        }

        if (Math.abs(f0) < accuracy) {
            return x; // already a root
        }

        do {
            x = x1;

            double f = function.applyAsDouble(x);
            double df = derivative.applyAsDouble(x);

            if (Math.abs(df) < THRESHOLD_VALUE)
                // TODO: Consider implementing a fallback (e.g., bisection or hybrid method)
                // when derivative is near zero to improve robustness.
                throw new MathException(
                        String.format(DERIVATIVE_NEAR_ZERO_AT_X_AT_NTH_ITERATION, x, iteration));

            // Adaptive step size limiting (prevents divergence due to large steps)
            double currentMaxStep = Math.max(maxStep, Math.abs(x) * 0.5d); // adaptive max step is relative to current x
            double step = Math.max(Math.min(f / df, currentMaxStep), -currentMaxStep); // limit the step size

            x1 = x - step;
            if (++iteration > maxIterations) {
                throw new MathException(
                        String.format(NEWTON_S_METHOD_FAILED_TO_CONVERGE_AFTER_D_ITERATIONS,
                                maxIterations, x1));
            }
        } while (Math.abs(x1 - x) > accuracy);

        return x1;
    }

    /**
     * Convenience overload that uses sensible default values for accuracy and
     * maximum iterations.
     *
     * @see #findEquationRoot(double[], DoubleUnaryOperator, DoubleUnaryOperator, double, int)
     */
    public static double findEquationRoot(final double[] interval,
                                          final DoubleUnaryOperator function,
                                          final DoubleUnaryOperator derivative) throws MathException {
        return findEquationRoot(interval, function, derivative, DEFAULT_ACCURACY, DEFAULT_MAX_ITERATIONS);
    }
}
