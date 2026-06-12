/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package arithmetics;

public class Fibonacci {

    private Fibonacci() {

    }

    /**
     * Returns the nth Fibonacci number using Binet's closed-form formula.
     *
     * <p>This method computes the nth term of the Fibonacci sequence where:
     * <ul>
     *   <li>{@code F(0) = 0}</li>
     *   <li>{@code F(1) = 1}</li>
     *   <li>{@code F(n) = F(n-1) + F(n-2)} for {@code n > 1}</li>
     * </ul>
     *
     * <p>The implementation uses the mathematically exact Binet's formula:
     * <pre>{@code
     * F(n) = (φ^n - ψ^n) / √5
     * }</pre>
     * where {@code φ = (1 + √5)/2} (the golden ratio) and {@code ψ = (1 - √5)/2}.
     *
     * <p><strong>Note on precision:</strong> Although Binet's formula is mathematically exact for integers,
     * this floating-point implementation (using {@code double}) is accurate only up to a certain point
     * (typically n ≤ 70–92, depending on the exact value). Beyond that, rounding errors in {@code Math.pow}
     * may produce incorrect results. For very large n, consider using arbitrary-precision arithmetic
     * (e.g., {@code BigInteger}) instead.
     *
     * @param n the ordinal (0-based index) of the Fibonacci number to retrieve
     * @return the nth Fibonacci number as a {@code long}
     * @throws IllegalArgumentException if {@code n < 0}
     *
     * @see <a href="https://en.wikipedia.org/wiki/Fibonacci_number#Binet%27s_formula">Binet's formula on Wikipedia</a>
     *
     * @since 0.0.1
     */
    public static long getNthFibonacci(long n) {
        if (n < 0) throw new IllegalArgumentException("Ordinal n needs to be >= 0");
        final double sqrt5 = Math.sqrt(5);
        return (long) ((Math.pow((1 + sqrt5) / 2, n) - Math.pow((1 - sqrt5) / 2, n)) / sqrt5);
    }
}
