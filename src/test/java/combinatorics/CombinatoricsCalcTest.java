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
    void countVariationsNoRepetitionsWithRepetitionTest() {
        assertEquals(125, CombinatoricsCalc.countVariationsWithRepetition(5, 3));
    }

    @Test
    void countPermutationsTest() {
        assertEquals(120, countPermutationsNoRepetition(5));
        for (int i = 1; i < 10; i++)
            assertEquals(factorial(i), countPermutationsNoRepetition(i));
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

    @Test
    void shouldReturnExpectedMultinomialCoefficient() {
        assertEquals(countPermutationsNoRepetition(3), multinomialCoefficient(new int[]{1, 1, 1}));
        assertEquals(280, multinomialCoefficient(new int[]{3, 4, 1}));
    }

    @Test
    void shouldReturnExpectedCombinationsWithRepetitionsCount() {
        assertEquals(35, countCombinationsWithRepetition(5, 3));
        assertEquals(1, countCombinationsWithRepetition(1, 1));
        assertEquals(2, countCombinationsWithRepetition(2, 1));
        assertEquals(20, countCombinationsWithRepetition(20, 1));
        assertEquals(3, countCombinationsWithRepetition(2, 2));
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
