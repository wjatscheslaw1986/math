/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@linkplain CombinationsGenerator } class.
 *
 * @author Viacheslav Mikhailov
 */
public class CombinationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CombinationsGeneratorTest.class, System.lineSeparator());
    }

    @Test
    void generate() {
        final int n = 13;
        for (int k = 1; k <= n; k++) {
            var result = CombinationsGenerator.generate(n, k);
            Assertions.assertEquals(CombinatoricsCalc.binomialCoefficient(n, k), result.size());
            for (var choice : result)
                Assertions.assertEquals(k, choice.length);
//            CombinationsGenerator.print(n, k, System.out);
        }
    }
}
