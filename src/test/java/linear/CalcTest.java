package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import combinatorics.CombinatoricsCalc;

public class CalcTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CalcTest.class, System.lineSeparator());
    }

    @Test
    void getNumberOfVariations() {
        assertEquals(6, CombinatoricsCalc.countVariations(6, 1));
        assertEquals(60, CombinatoricsCalc.countVariations(5, 3));
        assertEquals(120, CombinatoricsCalc.countVariations(5, 4));
        assertEquals(720, CombinatoricsCalc.countVariations(6, 5));
    }

    @Test
    void factorial() {
        assertEquals(1L, CombinatoricsCalc.factorial(0));
        assertEquals(1L, CombinatoricsCalc.factorial(1));
        assertEquals(2L, CombinatoricsCalc.factorial(2));
        assertEquals(6L, CombinatoricsCalc.factorial(3));
        assertEquals(24L, CombinatoricsCalc.factorial(4));
        assertEquals(120L, CombinatoricsCalc.factorial(5));
        assertEquals(720L, CombinatoricsCalc.factorial(6));
        assertEquals(5040L, CombinatoricsCalc.factorial(7));
        assertEquals(3628800L, CombinatoricsCalc.factorial(10));

        // TODO what to do with negatives?
        assertThrows(ArithmeticException.class, () -> CombinatoricsCalc.factorial(-1));
        assertThrows(ArithmeticException.class, () -> CombinatoricsCalc.factorial(-15));
    }
}
