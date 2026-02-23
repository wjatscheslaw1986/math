/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear;

import combinatorics.CombinatoricsCalc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsCalc.binomialCoefficient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalcTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CalcTest.class, System.lineSeparator());
    }

    @Test
    void getNumberOfVariations() {
        assertEquals(6, CombinatoricsCalc.countVariationsNoRepetition(6, 1));
        assertEquals(60, CombinatoricsCalc.countVariationsNoRepetition(5, 3));
        assertEquals(120, CombinatoricsCalc.countVariationsNoRepetition(5, 4));
        assertEquals(720, CombinatoricsCalc.countVariationsNoRepetition(6, 5));
    }

    @Test
    void shouldReturnExpectedValue() {
        assertEquals(1L, CombinatoricsCalc.factorial(0));
        assertEquals(1L, CombinatoricsCalc.factorial(1));
        assertEquals(2L, CombinatoricsCalc.factorial(2));
        assertEquals(6L, CombinatoricsCalc.factorial(3));
        assertEquals(24L, CombinatoricsCalc.factorial(4));
        assertEquals(120L, CombinatoricsCalc.factorial(5));
        assertEquals(720L, CombinatoricsCalc.factorial(6));
        assertEquals(5040L, CombinatoricsCalc.factorial(7));
        assertEquals(3628800L, CombinatoricsCalc.factorial(10));
    }

    @Test
    void shouldThrowExpectedExceptionIfConditionMet() {
        assertThrows(ArithmeticException.class, () -> CombinatoricsCalc.factorial(-1));
        assertThrows(ArithmeticException.class, () -> CombinatoricsCalc.factorial(-15));
    }

    @Test
    void givenTwoFormulasForCombinationsWithRepetitions_whenPassedSameArguments_expectSameResults() {
        for (int n = 3, k = 1; n <= 10; n = n + 2, k++) {
            System.out.println("n - 1 = " + n + " k = " + k);
            assertEquals(
                    binomialCoefficient(n + k - 1, n - 1),
                    binomialCoefficient(n + k - 1, k)
            );
        }

    }
}
