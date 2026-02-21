/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsCalc.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link combinatorics.CombinatoricsCalc} class.
 *
 * @author Viacheslav Mikhailov
 */
public class CombinatoricsCalcTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CombinatoricsCalcTest.class,
                System.lineSeparator());
    }

    @Test
    void countVariationsNoRepetitionsWithRepetitionsTest() {
        assertEquals(125, CombinatoricsCalc.countVariationsWithRepetitions(5, 3));
    }

    @Test
    void countPermutationsTest() {
        assertEquals(120, countPermutationsNoRepetitions(5));
        for (int i = 1; i < 10; i++)
            assertEquals(factorial(i), countPermutationsNoRepetitions(i));
    }

    @Test
    void countCombinationsWithoutRepetitionsTest() {
        assertEquals(10, binomialCoefficient(5, 3));
        for (int i = 1; i < 10; i++)
        assertEquals(binomialCoefficient(10, i), countCombinationsNoRepetitions(10, i));
    }

    @Test
    void shouldReturnExpectedCombinationsNoRepetitions() {
        assertEquals(10, countCombinationsNoRepetitions(5, 3));
    }

    /**
     * Count the number of all possible combinations of indices,
     * given the index sequence length, and the size of each combination.
     *
     * @param n length of the sequence of indices, starting at 0 (i.e. max index + 1)
     * @param k the size of combinations
     * @return the count of all possible selections of the indices, without repetition
     */
    private static int countCombinationsNoRepetitions(final int n, final int k) {
        return Math.toIntExact((int) factorial(n) / (factorial(k) * factorial(n - k)));
    }
}
