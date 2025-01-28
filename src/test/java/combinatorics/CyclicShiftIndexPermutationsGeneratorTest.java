/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CyclicShiftIndexPermutationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CyclicShiftIndexPermutationsGeneratorTest.class,
                          System.lineSeparator());
    }

    @Test
    void generate() {
        for (int arraySize = 0; arraySize < 7; arraySize++) {
            var result = CyclicShiftIndexPermutationsGenerator.generate(arraySize);
            Assertions.assertEquals(CombinatoricsCalc.getNumberOfPermutations(arraySize),
                                    result.size());
            for (var permutation : result)
                Assertions.assertEquals(arraySize, permutation.length);

//            CyclicShiftIndexPermutationsGenerator.print(System.out, arraySize);
        }
    }
}
