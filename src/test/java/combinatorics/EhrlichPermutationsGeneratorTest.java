/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link EhrlichPermutationsGenerator} class.
 *
 * @author Viacheslav Mikhailov
 */
public class EhrlichPermutationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", EhrlichPermutationsGeneratorTest.class,
                          System.lineSeparator());
    }

    @Test
    void givenIndicesArray_whenGeneratePermutations_thenVerifyIntegrity() {
        var initialPermutation = new Integer[]{0, 1, 2, 3, 4, 5, 6};
        List<Integer[]> controlArray = CyclicShiftPermutationsGenerator.generate(initialPermutation.length)
                .stream().map(arr -> Arrays.stream(arr).boxed().toArray(Integer[]::new))
                .toList();
        List<Integer[]> result = EhrlichPermutationsGenerator.generate(initialPermutation);
        Assertions.assertEquals(CombinatoricsCalc.countPermutations(initialPermutation.length),
                result.size());
        Assertions.assertEquals(controlArray.size(),result.size());
        for (Integer[] permutation : result)
            Assertions.assertTrue(contains(controlArray, permutation));
    }

    private static boolean contains(List<Integer[]> permutations,  Integer[] permutation) {
        for (Integer[] perm : permutations) {
            var hits = 0;
            for (Integer val1 : perm) {
                for (Integer val2 : permutation) {
                    if (val1.equals(val2)) {
                        hits++;
                    }
                }
                if (hits == permutation.length) return true;
            }
        }
        return false;
    }
}
