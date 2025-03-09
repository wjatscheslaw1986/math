/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@linkplain CyclicShiftPermutationsGenerator} class.
 *
 * @author Viacheslav Mikhailov
 */
public class CyclicShiftPermutationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CyclicShiftPermutationsGeneratorTest.class,
                          System.lineSeparator());
    }

    @Test
    void generate() {
        for (int arraySize = 0; arraySize < 7; arraySize++) {
            var result = CyclicShiftPermutationsGenerator.generate(arraySize);
            Assertions.assertEquals(CombinatoricsCalc.countPermutations(arraySize),
                                    result.size());
            for (var permutation : result)
                Assertions.assertEquals(arraySize, permutation.length);
            CyclicShiftPermutationsGenerator.print(System.out, arraySize);
        }
    }
}
