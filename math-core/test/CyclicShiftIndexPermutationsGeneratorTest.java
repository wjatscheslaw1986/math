/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import combinatoric.CyclicShiftIndexPermutationsGenerator;

public class CyclicShiftIndexPermutationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s.class\n", CyclicShiftIndexPermutationsGeneratorTest.class);
    }

    @Test
    void generate() {
        int arraySize = 3;
        var result = CyclicShiftIndexPermutationsGenerator.generate(arraySize);
        Assertions.assertEquals(CyclicShiftIndexPermutationsGenerator.getNumberOfPossiblePermutations(arraySize),
                                result.size());
        for (var permutation : result)
            Assertions.assertEquals(arraySize, permutation.length);
        
        CyclicShiftIndexPermutationsGenerator.print(arraySize);
    }
}
