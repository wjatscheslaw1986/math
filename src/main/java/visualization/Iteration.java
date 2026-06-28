/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package visualization;

/**
 * Represents one iteration of the Secant method.
 *
 * The secant line is defined by the two points:
 *
 * (a, f(a))
 * (b, f(b))
 *
 * and produces the next approximation x0.
 */
public record Iteration(
        int step,
        double a,
        double b,
        double fa,
        double fb,
        double x0,
        double fx0
) {

    /**
     * Slope of the secant line.
     */
    public double slope() {
        return (fb - fa) / (b - a);
    }

    /**
     * y-intercept of the secant line.
     */
    public double intercept() {
        return fa - slope() * a;
    }

    /**
     * Returns the y-value on the secant line.
     */
    public double secantY(double x) {
        return slope() * x + intercept();
    }

    /**
     * Returns true if the secant is almost horizontal.
     */
    public boolean isDegenerate() {
        return Math.abs(fb - fa) < 1e-15;
    }

    @Override
    public String toString() {
        return String.format(
                "%2d: a=%10.6f  b=%10.6f  x=%10.6f  f(x)=% .8f",
                step,
                a,
                b,
                x0,
                fx0
        );
    }
}
