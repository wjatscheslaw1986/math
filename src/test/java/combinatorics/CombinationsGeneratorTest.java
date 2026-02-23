/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsUtil.printJavaCode;

/**
 * Tests for {@link CombinationsNoRepetition} class.
 *
 * @author Viacheslav Mikhailov
 */
public class CombinationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CombinationsGeneratorTest.class, System.lineSeparator());
    }

    @Test
    void shouldGenerateBinomialCoefficientNumberOfCombinationsWithoutRepetition() {
        final int n = 13;
        for (int k = 1; k <= n; k++) {
            var result = CombinationsNoRepetition.generate(n, k);
            Assertions.assertEquals(CombinatoricsCalc.binomialCoefficient(n, k), result.size());
            for (var selection : result)
                Assertions.assertEquals(k, selection.length);
        }
    }

    @Test
    void print() {
        printJavaCode(new CombinationsNoRepetitionGenerator(5, 3),  System.out);
    }
}
