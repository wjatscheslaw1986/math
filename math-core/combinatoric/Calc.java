package combinatoric;

public final class Calc {

    /**
     * Number of variations. I.e. number of ways to choose a subset of <b>k</b> elements
     * from of a set of <b>n</b> elements, summarizing all possible mutual permutations
     * of each chosen subset of <b>k</b> elements as separate variations.
     * 
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return number of variations
     */
    public static long getNumberOfVariations(final int n, final int k) {
        if (n < k)
            throw new ArithmeticException("You cannot choose from a set the amout of elements which is greater than there are in the set");
        return Math.divideExact(factorial(n), factorial(n - k));
    }

    /**
     * Number of ways to choose <b>k</b> elements out of a set of <b>n</b> elements,
     * disregard of the order.
     * 
     * @param n size of a given set of all elements
     * @param k size of a single choice, i.e. a subset of <b>n</b>
     * @return number of choices
     */
    public static long binomialCoefficient(final int n, final int k) {
        if (n < k)
            throw new ArithmeticException("You cannot choose from a set the amout of elements which is greater than there are in the set");
        return Math.divideExact(getNumberOfVariations(n, k), factorial(k));
    }

    /**
     * Calculates a size of a set of all possible permutations of indices, from 0 (inclusive)
     * to arrayLength (exclusive).
     * 
     * @param arrayLength a size of the given set of array indices
     * @return Number of all possible permutations for the given number of elements.
     */
    public static long getNumberOfPermutations(final int arrayLength) {
        return factorial(arrayLength);
    }

    /*
     * @return a factorial of a number.
     */
    public static long factorial(final int n) {
        long result = 1;
        for (long i = 1L; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
