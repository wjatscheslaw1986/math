/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import java.util.Arrays;

/**
 * An API for calculating stereotypical tasks in combinatorics.
 *
 * @author Viacheslav Mikhailov
 */
public final class CombinatoricsCalc {

    private CombinatoricsCalc() {
        // static context only
    }

    /**
     * Number of variations without repetitions. I.e. number of ways to choose a subset of <b>k</b> elements
     * from a set of <b>n</b> elements, summarizing all possible mutual permutations (orderings)
     * for each chosen subset of <b>k</b> elements, i.e. counting each ordering as a separate variation.
     * <p>
     *     n!/(n-m)!
     * </p>
     *
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return number of variations
     */
    public static long countVariationsNoRepetitions(final int n, final int k) {
        if (n < k)
            throw new ArithmeticException("You cannot choose from a set the amout of elements which is greater than there are in the set");
        return Math.divideExact(factorial(n), factorial(n - k));
    }

    /**
     * Count combinations without repetitions.
     * </br>
     * I.e. a number of ways to choose <b>k</b> elements out of a set of <b>n</b> elements,
     * disregard of ordering, repetitions are not counted.
     * <p>
     * n!/(m!*(n-m)!)
     * </p>
     *
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return number of combinations without repetitions
     */
    public static long binomialCoefficient(final int n, final int k) {
        if (n < k)
            throw new ArithmeticException("You cannot choose from a set the amount of elements which is greater than there are in the set");
        return Math.divideExact(countVariationsNoRepetitions(n, k), countPermutationsNoRepetitions(k));
    }

    /**
     * Count number of all possible permutations with repetitions of array indices,
     * for a cardinality given.
     * <p>
     *     Each index is repeated the prescribed number of times, individually.
     * </p>
     * <p>
     *     The given cardinality is the argument array length.
     * </p>
     *
     * @param repetitions an array that maps index on the prescribed number of repetitions.
     * @return the number of all possible permutations with individually prescribed repetitions, per element
     */
    public static int countPermutationsWithRepetitions(final int[] repetitions) {
        // TODO maybe add a validation to avoid INT overflow
        int nominator = Arrays.stream(repetitions).sum();
        int denominator = (int) Arrays.stream(repetitions)
                .mapToLong(CombinatoricsCalc::factorial)
                .reduce(0L, (a, b) -> a*b);
        return nominator / denominator;
    }

    /**
     * Count number of all possible permutations of array indices,
     * no repetitions of elements allowed, for a given cardinality.
     *
     * @param cardinality the number of distinct sequential indices, starting with 0
     * @return the number of all possible permutations, without repetitions
     */
    public static int countPermutationsNoRepetitions(final int cardinality) {
        return (int) CombinatoricsCalc.factorial(cardinality);
    }

    /**
     * Returns a number of all possible arrangements of selections of indices, distinct by their order.
     * <p>
     *     It is a number of ways to click <b>k</b> times any key on a keyboard of <b>n</b> keys.
     * </p>
     *
     * @param n cardinality
     * @param k size of a selection
     * @return a count of all possible variations of indices, distinctly ordered.
     */
    public static int countVariationsWithRepetitions(int n, int k) {
        return (int) Math.pow(n, k);
    }

    /**
     * I have no idea why they still didn't add it to {@link java.lang.Math}
     *
     * @return a factorial of a number.
     */
    public static long factorial(final int n) {
        if (n < 0)
            throw new ArithmeticException("A factorial of a negative number doesn't exist.");
        long result = 1L;
        for (int i = 1; i <= n; i++) {
            result *= i;
            if (result < 0)
                throw new ArithmeticException(String.format("An int64 overflow has happened while counting a %d!, on the %dth number", n, i));
        }
        return result;
    }


}
