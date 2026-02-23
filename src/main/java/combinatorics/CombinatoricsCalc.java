/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

/**
 * Utility class to calculate with basic combinatorics formula.
 *
 * @author Viacheslav Mikhailov
 */
public final class CombinatoricsCalc {

    private CombinatoricsCalc() {
        // static context only
    }

    /**
     * Count number of all possible permutations of array indices,
     * no repetitions of elements allowed, for a given cardinality.
     *
     * @param cardinality the number of distinct sequential indices, starting with 0
     * @return the number of all possible permutations, without repetitions
     */
    public static int countPermutationsNoRepetition(final int cardinality) {
        return (int) CombinatoricsCalc.factorial(cardinality);
    }

    /**
     * Computes the number of **distinct permutations** of a multiset
     * (also known as the multinomial coefficient).
     * <p>
     * Count all possible permutations with repetition
     * </p>
     * <p>
     * Given an array {@code repetitions} where {@code repetitions[i]} is the number
     * of times the i-th distinct item appears, this method returns:
     * </p>
     * <pre>
     *                     n!
     *     ───────────────────────────────
     *      r₀! × r₁! × r₂! × … × r_{k-1}!
     * </pre>
     * where {@code n = r₀ + r₁ + … + r_{k-1}}.
     * <p>
     * This is the number of ways to arrange {@code n} items where there are
     * {@code repetitions.length} distinct types with the given frequencies.
     * </p>
     * <p>
     * Examples:
     * <ul>
     *   <li>{@code [2, 1]} → 3! / (2!·1!) = 3  ("AAB")</li>
     *   <li>{@code [1, 1, 1]} → 3! / (1!·1!·1!) = 6  (all distinct)</li>
     *   <li>{@code [3]} → 3! / 3! = 1  (all identical)</li>
     * </ul>
     * </p>
     *
     * @param repetitions non-negative integer array; {@code repetitions[i]} = frequency of the i-th distinct item
     * @return the number of distinct permutations of the corresponding multiset
     * @throws IllegalArgumentException if {@code repetitions} is {@code null},
     *                                  contains negative values, or the total sum overflows {@code long}
     * @throws ArithmeticException      if the result does not fit in a {@code int}
     *                                  (many inputs will overflow {@code int} — consider using {@code long} return type)
     */
    public static long multinomialCoefficient(int[] repetitions) {
        if (repetitions == null) {
            throw new IllegalArgumentException("repetitions array cannot be null");
        }

        long total = 0;
        long denominator = 1L;
        for (int r : repetitions) {
            if (r < 0) {
                throw new IllegalArgumentException("Negative repetition count not allowed: " + r);
            }
            total += r;
            if (total < 0) {  // overflow
                throw new IllegalArgumentException("Total size too large (sum overflow)");
            }

            long f = factorial(r);
            if (denominator > Long.MAX_VALUE / f) {
                throw new ArithmeticException("Denominator overflow while computing multinomial coefficient");
            }
            denominator *= f;
        }

        if (total == 0) {
            return 1;  // empty multiset → 1 way (the empty arrangement)
        }

        long numerator = factorial(total);

        return numerator / denominator;
    }

    /**
     * Count combinations without repetition.
     * </br>
     * Returns number of ways to select a subset of <b>k</b> elements from a set of <b>n</b> elements,
     * disregard of ordering, repetitions aren't counted.
     * <p>
     * n!/(k!*(n-k)!)
     * </p>
     *
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return number of combinations without repetitions
     */
    public static long binomialCoefficient(final int n, final int k) {
        if (n < k)
            throw new ArithmeticException("You cannot choose more elements than there are in a set");
        if (n < 0 || k < 0)
            throw new ArithmeticException("Number of elements has to be positive");
        return Math.divideExact(countVariationsNoRepetition(n, k), factorial(k));
    }

    /**
     * Computes the number of **combinations with repetition** (also called
     * "multisets", "combinations with replacement", or "stars and bars" selections).
     * <p>
     * This is the number of ways to choose {@code k} elements from {@code n} distinct types,
     * where repetition is allowed and the order of selection does **not** matter.
     * </p>
     * <p>
     * Mathematically, it is the number of non-negative integer solutions to the equation:
     * </p>
     * <pre>
     *     x₁ + x₂ + … + xₙ = k    where each xᵢ ≥ 0
     * </pre>
     * <p>
     * The standard closed-form formula is:
     * </p>
     * <pre>
     *     C(n + k - 1, k)   or equivalently   C(n + k - 1, n - 1)
     * </pre>
     * <p>
     * This is one of the most fundamental functions in combinatorics and appears frequently in:
     * <ul>
     *   <li>distributing {@code k} identical items into {@code n} distinct bins</li>
     *   <li>buying {@code k} items from {@code n} types with repetition allowed</li>
     *   <li>number of multi-subsets of size {@code k} from a set of size {@code n}</li>
     *   <li>number of monomials of degree {@code k} in {@code n} variables</li>
     * </ul>
     * </p>
     *
     * <h3>Examples</h3>
     * <pre>{@code
     * countCombinationsWithRepetition(3, 2)  →  C(4,2) = 6
     *     // ways to choose 2 fruits from {apple, banana, cherry} with repetition:
     *     // AA, AB, AC, BB, BC, CC
     *
     * countCombinationsWithRepetition(4, 3)  →  C(6,3) = 20
     *
     * countCombinationsWithRepetition(5, 0)  →  1   (only the empty selection)
     *
     * countCombinationsWithRepetition(1, 7)  →  1   (only one way: seven times the only type)
     *
     * countCombinationsWithRepetition(0, 0)  →  1   (empty set → one empty combination)
     * countCombinationsWithRepetition(0, 1)  →  0   (cannot choose 1 item from nothing)
     * }</pre>
     *
     * <h3>Comparison with combinations without repetition</h3>
     * <table border="1" cellpadding="4">
     *   <tr><th>Property</th><th>Without repetition</th><th>With repetition</th></tr>
     *   <tr><td>Formula</td><td>C(n, k)</td><td>C(n + k - 1, k)</td></tr>
     *   <tr><td>Repetition allowed?</td><td>No</td><td>Yes</td></tr>
     *   <tr><td>Max k</td><td>k ≤ n</td><td>no upper limit</td></tr>
     *   <tr><td>Balls & boxes analogy</td><td>at most 1 ball per box</td><td>any number of balls per box</td></tr>
     * </table>
     *
     * @param n number of distinct types / categories / bins / variables (≥ 0)
     * @param k number of items / selections / degree / summands (≥ 0)
     * @return the number of combinations with repetition C(n + k - 1, k)
     * @throws ArithmeticException if n < 0 or k < 0
     *                             or if the result cannot be represented in a {@code long}
     *                             (very large values of n+k will overflow even long)
     * @see CombinatoricsCalc#binomialCoefficient(int, int)  assumed helper method
     */
    public static long countCombinationsWithRepetition(final int n, final int k) {
        if (n < 0 || k < 0) {
            throw new ArithmeticException("n and k must be non-negative");
        }

        // Special case: choosing 0 items is always 1 way (the empty combination)
        if (k == 0) {
            return 1L;
        }

        // Choosing from 0 types is only possible when k = 0 (already handled)
        if (n == 0) {
            return 0L;
        }

        // The classic formula C(n + k - 1, k)
        // We use the smaller of k and (n-1) to minimize intermediate values
        return binomialCoefficient(n + k - 1, Math.min(k, n - 1));
    }

    /**
     * Calculates the number of **variations without repetition** (also known as permutations
     * of <i>n</i> things taken <i>k</i> at a time).
     * <p>
     * This is the number of ways to choose and arrange <b>k</b> distinct elements
     * from a set of <b>n</b> distinct elements, where the order of selection matters.
     * </p>
     * <p>
     * Mathematically:
     * <pre>
     * P(n,k) = n! / (n - k)!
     *        = n × (n-1) × (n-2) × … × (n-k+1)
     * </pre>
     * (defined as 0 when k > n or k < 0, usually 1 when k = 0)
     * </p>
     *
     * @param n the total number of elements in the set (must be ≥ 0)
     * @param k the number of elements to choose and arrange (must be ≥ 0)
     * @return the number of possible ordered selections of k elements from n
     * @throws IllegalArgumentException if n < 0 or k < 0
     *                                  (some implementations may also throw when k > n)
     */
    public static long countVariationsNoRepetition(final int n, final int k) {
        if (k > n)
            throw new ArithmeticException("You cannot select more elements than there are in the set.");
        if (k < 0)
            throw new ArithmeticException("A negative size is not allowed.");
        return Math.divideExact(factorial(n), factorial(n - k));
    }

    /**
     * Returns a number of all possible arrangements of selections of indices, distinct by their order.
     * <p>
     * It is a number of ways to click <b>k</b> times any key on a keyboard of <b>n</b> keys.
     * </p>
     *
     * @param n cardinality
     * @param k size of a selection
     * @return a count of all possible variations of indices, distinctly ordered.
     */
    public static int countVariationsWithRepetition(int n, int k) {
        return (int) Math.pow(n, k);
    }

    /**
     * Computes n! (n factorial) for non-negative integers.
     * <p>
     * Returns the product of all positive integers ≤ n.
     * Throws on negative input or when the result exceeds 64-bit signed range.
     * </p>
     *
     * @param n non-negative integer
     * @return n!
     * @throws ArithmeticException if n < 0 or overflow occurs
     */
    public static long factorial(long n) {
        if (n < 0) {
            throw new ArithmeticException("Factorial is not defined for negative numbers.");
        }
        if (n == 0 || n == 1) return 1L;

        long result = 1L;
        for (int i = 2; i <= n; i++) {
            if (result > Long.MAX_VALUE / i) {
                throw new ArithmeticException(
                        String.format("Overflow while computing %d! at multiplier %d", n, i));
            }
            result *= i;
        }
        return result;
    }

    /**
     * Overload for {@link CombinatoricsCalc#factorial(long n)}.
     *
     * @param n non-negative integer
     * @return n!
     * @throws ArithmeticException if n < 0 or overflow occurs
     */
    public static long factorial(int n) {
        return factorial((long) n);
    }
}
