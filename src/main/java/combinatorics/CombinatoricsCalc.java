/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

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
     * 
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return number of variations
     */
    public static long countVariations(final int n, final int k) {
        if (n < k)
            throw new ArithmeticException("You cannot choose from a set the amout of elements which is greater than there are in the set");
        return Math.divideExact(factorial(n), factorial(n - k));
    }

    /**
     * Number of variations with repetitions.
     * I.e. number of ways to click <b>k</b> times any key on a keyboard of <b>n</b> keys.
     *
     * @param n size of a given set of all elements
     * @param k size of a subset of <b>n</b>
     * @return number of variations with repetitions
     */
    public static long countVariationsWithRepetitions(final int n, final int k) {
        return (long) Math.pow(n, k);
    }

    /**
     * Combinations without repetitions.
     * I.e. a number of ways to choose <b>k</b> elements out of a set of <b>n</b> elements,
     * disregard of the order, repetitions are not included.
     * 
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return number of combinations without repetitions
     */
    public static long binomialCoefficient(final int n, final int k) {
        if (n < k)
            throw new ArithmeticException("You cannot choose from a set the amount of elements which is greater than there are in the set");
        return Math.divideExact(countVariations(n, k), factorial(k));
    }

    /**
     * Calculates a size of a set of all possible permutations of indices, from 0 (inclusive)
     * to arrayLength (exclusive).
     * 
     * @param arrayLength a size of the given set of array indices
     * @return Number of all possible permutations for the given number of elements.
     */
    public static long countPermutations(final int arrayLength) {
        return factorial(arrayLength);
    }

    /**
     * n! / n1!*n2!*...*nk!
     * TODO
     */
    public static long countPermutationsWithRepetitions(final int arrayLength) {
        throw new IllegalStateException("TODO");
    }

    /**
     * I have no idea why they still didn't add it to {@linkplain java.lang.Math}
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
