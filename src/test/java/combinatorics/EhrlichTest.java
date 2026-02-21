/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link Ehrlich} class.
 *
 * @author Viacheslav Mikhailov
 */
public class EhrlichTest {

    private int[][] permutationsGiven = {
            {0, 1, 2, 3, 4},
            {1, 0, 2, 3, 4},
            {2, 0, 1, 3, 4},
            {0, 2, 1, 3, 4},
            {1, 2, 0, 3, 4},
            {2, 1, 0, 3, 4},
            {3, 1, 0, 2, 4},
            {0, 1, 3, 2, 4},
            {1, 0, 3, 2, 4},
            {3, 0, 1, 2, 4},
            {0, 3, 1, 2, 4},
            {1, 3, 0, 2, 4},
            {2, 3, 0, 1, 4},
            {3, 2, 0, 1, 4},
            {0, 2, 3, 1, 4},
            {2, 0, 3, 1, 4},
            {3, 0, 2, 1, 4},
            {0, 3, 2, 1, 4},
            {1, 3, 2, 0, 4},
            {2, 3, 1, 0, 4},
            {3, 2, 1, 0, 4},
            {1, 2, 3, 0, 4},
            {2, 1, 3, 0, 4},
            {3, 1, 2, 0, 4},
            {4, 1, 2, 0, 3},
            {0, 1, 2, 4, 3},
            {1, 0, 2, 4, 3},
            {4, 0, 2, 1, 3},
            {0, 4, 2, 1, 3},
            {1, 4, 2, 0, 3},
            {2, 4, 1, 0, 3},
            {4, 2, 1, 0, 3},
            {0, 2, 1, 4, 3},
            {2, 0, 1, 4, 3},
            {4, 0, 1, 2, 3},
            {0, 4, 1, 2, 3},
            {1, 4, 0, 2, 3},
            {2, 4, 0, 1, 3},
            {4, 2, 0, 1, 3},
            {1, 2, 0, 4, 3},
            {2, 1, 0, 4, 3},
            {4, 1, 0, 2, 3},
            {0, 1, 4, 2, 3},
            {1, 0, 4, 2, 3},
            {2, 0, 4, 1, 3},
            {0, 2, 4, 1, 3},
            {1, 2, 4, 0, 3},
            {2, 1, 4, 0, 3},
            {3, 1, 4, 0, 2},
            {4, 1, 3, 0, 2},
            {0, 1, 3, 4, 2},
            {3, 1, 0, 4, 2},
            {4, 1, 0, 3, 2},
            {0, 1, 4, 3, 2},
            {1, 0, 4, 3, 2},
            {3, 0, 4, 1, 2},
            {4, 0, 3, 1, 2},
            {1, 0, 3, 4, 2},
            {3, 0, 1, 4, 2},
            {4, 0, 1, 3, 2},
            {0, 4, 1, 3, 2},
            {1, 4, 0, 3, 2},
            {3, 4, 0, 1, 2},
            {0, 4, 3, 1, 2},
            {1, 4, 3, 0, 2},
            {3, 4, 1, 0, 2},
            {4, 3, 1, 0, 2},
            {0, 3, 1, 4, 2},
            {1, 3, 0, 4, 2},
            {4, 3, 0, 1, 2},
            {0, 3, 4, 1, 2},
            {1, 3, 4, 0, 2},
            {2, 3, 4, 0, 1},
            {3, 2, 4, 0, 1},
            {4, 2, 3, 0, 1},
            {2, 4, 3, 0, 1},
            {3, 4, 2, 0, 1},
            {4, 3, 2, 0, 1},
            {0, 3, 2, 4, 1},
            {2, 3, 0, 4, 1},
            {3, 2, 0, 4, 1},
            {0, 2, 3, 4, 1},
            {2, 0, 3, 4, 1},
            {3, 0, 2, 4, 1},
            {4, 0, 2, 3, 1},
            {0, 4, 2, 3, 1},
            {2, 4, 0, 3, 1},
            {4, 2, 0, 3, 1},
            {0, 2, 4, 3, 1},
            {2, 0, 4, 3, 1},
            {3, 0, 4, 2, 1},
            {4, 0, 3, 2, 1},
            {0, 4, 3, 2, 1},
            {3, 4, 0, 2, 1},
            {4, 3, 0, 2, 1},
            {0, 3, 4, 2, 1},
            {1, 3, 4, 2, 0},
            {2, 3, 4, 1, 0},
            {3, 2, 4, 1, 0},
            {1, 2, 4, 3, 0},
            {2, 1, 4, 3, 0},
            {3, 1, 4, 2, 0},
            {4, 1, 3, 2, 0},
            {1, 4, 3, 2, 0},
            {2, 4, 3, 1, 0},
            {4, 2, 3, 1, 0},
            {1, 2, 3, 4, 0},
            {2, 1, 3, 4, 0},
            {3, 1, 2, 4, 0},
            {4, 1, 2, 3, 0},
            {1, 4, 2, 3, 0},
            {3, 4, 2, 1, 0},
            {4, 3, 2, 1, 0},
            {1, 3, 2, 4, 0},
            {2, 3, 1, 4, 0},
            {3, 2, 1, 4, 0},
            {4, 2, 1, 3, 0},
            {2, 4, 1, 3, 0},
            {3, 4, 1, 2, 0},
            {4, 3, 1, 2, 0}
    };

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", EhrlichTest.class,
                          System.lineSeparator());
    }

    @Test
    void givenIndicesArray_whenGeneratePermutations_thenVerifyIntegrity() {
        var initialPermutation = new int[]{0, 1, 2, 3, 4, 5, 6};
        List<int[]> controlArray = CyclicShift.generate(initialPermutation.length)
                .stream().map(arr -> Arrays.stream(arr).toArray())
                .toList();
        List<int[]> result = Ehrlich.generate(initialPermutation.length);
        Assertions.assertEquals(CombinatoricsCalc.countPermutationsNoRepetitions(initialPermutation.length),
                result.size());
        Assertions.assertEquals(controlArray.size(),result.size());
        for (int[] permutation : result)
            Assertions.assertTrue(contains(controlArray, permutation));
    }

    private static boolean contains(List<int[]> permutations,  int[] permutation) {
        for (int[] perm : permutations) {
            var hits = 0;
            for (int val1 : perm) {
                for (int val2 : permutation) {
                    if (val1 == val2) {
                        hits++;
                    }
                }
                if (hits == permutation.length) return true;
            }
        }
        return false;
    }

//    @Test
    void printPermutationsGiven() {
        CombinatoricsUtil.printJavaCode(new EhrlichPermutationsGenerator(5), System.out);
    }

    @Test
    void shouldGenerateSamePermutationsAsGiven() {
        List<int[]> result = Ehrlich.generate(5);
        for (int i = 0; i < permutationsGiven.length; i++) {
            assertArrayEquals(permutationsGiven[i], result.get(i));
        }
    }

    @Test
    void shouldGenerateExpectedNumberOfPermutations() {
        List<int[]> result = Ehrlich.generate(5);
        assertEquals(CombinatoricsCalc.countPermutationsNoRepetitions(5), result.size());
    }

    @Test
    void shouldReturnSameCountOfElementsForBothMethods() {
        assertEquals(
                Ehrlich.count(7),
                CombinatoricsCalc.countPermutationsNoRepetitions(7));
    }
}
