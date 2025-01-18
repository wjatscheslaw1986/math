/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package test.combinatoric;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import combinatoric.Calc;
import combinatoric.CyclicShiftIndexPermutationsGenerator;

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
            Assertions.assertEquals(Calc.getNumberOfPermutations(arraySize),
                                    result.size());
            for (var permutation : result)
                Assertions.assertEquals(arraySize, permutation.length);

            CyclicShiftIndexPermutationsGenerator.print(System.out, arraySize);
        }
    }
}
