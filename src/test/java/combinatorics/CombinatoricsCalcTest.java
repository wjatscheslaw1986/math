/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsCalc.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@linkplain combinatorics.CombinatoricsCalc} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class CombinatoricsCalcTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CombinatoricsCalcTest.class,
                System.lineSeparator());
    }

    @Test
    void countVariationsWithRepetitionsTest() {
        assertEquals(125, countVariationsWithRepetitions(5, 3));
    }

    @Test
    void countPermutationsTest() {
        assertEquals(120, countPermutations(5));
        for (int i = 1; i < 10; i++)
            assertEquals(factorial(i), countPermutations(i));
    }

    @Test
    void countCombinationsWithoutRepetitionsTest() {
        assertEquals(10, binomialCoefficient(5, 3));
    }
}
