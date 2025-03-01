/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com)  © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsCalc.countVariationsWithRepetitions;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@linkplain VariationsWithRepetitionsGenerator} class.
 *
 * @author Viacheslav Mikhailov
 */
public class VariationsWithRepetitionsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", VariationsWithRepetitionsGeneratorTest.class, System.lineSeparator());
    }

    @Test
    void generateVariationsWithRepetitionsTest() {
        int n = 5, k = 3;
        var repetitions = VariationsWithRepetitionsGenerator.generate(n, k);
        assertEquals(countVariationsWithRepetitions(n, k), repetitions.size());
        VariationsWithRepetitionsGenerator.print(n, k, System.out);
    }
}
