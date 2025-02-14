/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@linkplain combinatorics.CyclicShiftGenerator} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class CyclicShiftGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CyclicShiftGeneratorTest.class,
                          System.lineSeparator());
    }

    @Test
    void generate() {
        for (int arraySize = 0; arraySize < 7; arraySize++) {
            var result = CyclicShiftGenerator.generate(arraySize);
            Assertions.assertEquals(CombinatoricsCalc.countPermutations(arraySize),
                                    result.size());
            for (var permutation : result)
                Assertions.assertEquals(arraySize, permutation.length);
            CyclicShiftGenerator.print(System.out, arraySize);
        }
    }
}
