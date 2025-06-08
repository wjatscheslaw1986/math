/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package approximation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Tests for {@linkplain approximation.RoundingUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class RoundingUtilTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", RoundingUtilTest.class,
                System.lineSeparator());
    }

    @Test
    void given_array_of_doubles_clean_it_of_negative_zeroes() {
        Double[] coefficients = new Double[]{.0d, -.0d, 4.0d, .0d, -.0d, .05d, -0.0d, .0d, 8.05d};
        Double[] expected = new Double[]{.0d, .0d, 4.0d, .0d, .0d, .05d, 0.0d, .0d, 8.05d};
        RoundingUtil.cleanDoubleArrayOfNegativeZeros(coefficients);
        assertArrayEquals(expected, coefficients);
    }
}
