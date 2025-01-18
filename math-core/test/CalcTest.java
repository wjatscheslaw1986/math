package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import combinatoric.Calc;

public class CalcTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CalcTest.class, System.lineSeparator());
    }

    @Test
    void getNumberOfVariations() {
        assertEquals(6, Calc.getNumberOfVariations(6, 1));
        assertEquals(60, Calc.getNumberOfVariations(5, 3));
        assertEquals(120, Calc.getNumberOfVariations(5, 4));
        assertEquals(720, Calc.getNumberOfVariations(6, 5));
    }

    @Test
    void factorial() {
        assertEquals(1L, Calc.factorial(0));
        assertEquals(1L, Calc.factorial(1));
        assertEquals(2L, Calc.factorial(2));
        assertEquals(6L, Calc.factorial(3));
        assertEquals(24L, Calc.factorial(4));
        assertEquals(120L, Calc.factorial(5));
        assertEquals(720L, Calc.factorial(6));
        assertEquals(5040L, Calc.factorial(7));
        assertEquals(3628800L, Calc.factorial(10));

        // TODO what to do with negatives?
        assertEquals(1L, Calc.factorial(-1));
        assertEquals(1L, Calc.factorial(-15));
    }
}
