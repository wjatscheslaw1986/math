/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link CyclicShift} class.
 *
 * @author Viacheslav Mikhailov
 */
public class CyclicShiftTest {

    private int[][] permutationsGiven = {
            {0, 1, 2, 3, 4},
            {1, 2, 3, 4, 0},
            {2, 3, 4, 0, 1},
            {3, 4, 0, 1, 2},
            {4, 0, 1, 2, 3},
            {1, 2, 3, 0, 4},
            {2, 3, 0, 4, 1},
            {3, 0, 4, 1, 2},
            {0, 4, 1, 2, 3},
            {4, 1, 2, 3, 0},
            {2, 3, 0, 1, 4},
            {3, 0, 1, 4, 2},
            {0, 1, 4, 2, 3},
            {1, 4, 2, 3, 0},
            {4, 2, 3, 0, 1},
            {3, 0, 1, 2, 4},
            {0, 1, 2, 4, 3},
            {1, 2, 4, 3, 0},
            {2, 4, 3, 0, 1},
            {4, 3, 0, 1, 2},
            {1, 2, 0, 3, 4},
            {2, 0, 3, 4, 1},
            {0, 3, 4, 1, 2},
            {3, 4, 1, 2, 0},
            {4, 1, 2, 0, 3},
            {2, 0, 3, 1, 4},
            {0, 3, 1, 4, 2},
            {3, 1, 4, 2, 0},
            {1, 4, 2, 0, 3},
            {4, 2, 0, 3, 1},
            {0, 3, 1, 2, 4},
            {3, 1, 2, 4, 0},
            {1, 2, 4, 0, 3},
            {2, 4, 0, 3, 1},
            {4, 0, 3, 1, 2},
            {3, 1, 2, 0, 4},
            {1, 2, 0, 4, 3},
            {2, 0, 4, 3, 1},
            {0, 4, 3, 1, 2},
            {4, 3, 1, 2, 0},
            {2, 0, 1, 3, 4},
            {0, 1, 3, 4, 2},
            {1, 3, 4, 2, 0},
            {3, 4, 2, 0, 1},
            {4, 2, 0, 1, 3},
            {0, 1, 3, 2, 4},
            {1, 3, 2, 4, 0},
            {3, 2, 4, 0, 1},
            {2, 4, 0, 1, 3},
            {4, 0, 1, 3, 2},
            {1, 3, 2, 0, 4},
            {3, 2, 0, 4, 1},
            {2, 0, 4, 1, 3},
            {0, 4, 1, 3, 2},
            {4, 1, 3, 2, 0},
            {3, 2, 0, 1, 4},
            {2, 0, 1, 4, 3},
            {0, 1, 4, 3, 2},
            {1, 4, 3, 2, 0},
            {4, 3, 2, 0, 1},
            {1, 0, 2, 3, 4},
            {0, 2, 3, 4, 1},
            {2, 3, 4, 1, 0},
            {3, 4, 1, 0, 2},
            {4, 1, 0, 2, 3},
            {0, 2, 3, 1, 4},
            {2, 3, 1, 4, 0},
            {3, 1, 4, 0, 2},
            {1, 4, 0, 2, 3},
            {4, 0, 2, 3, 1},
            {2, 3, 1, 0, 4},
            {3, 1, 0, 4, 2},
            {1, 0, 4, 2, 3},
            {0, 4, 2, 3, 1},
            {4, 2, 3, 1, 0},
            {3, 1, 0, 2, 4},
            {1, 0, 2, 4, 3},
            {0, 2, 4, 3, 1},
            {2, 4, 3, 1, 0},
            {4, 3, 1, 0, 2},
            {0, 2, 1, 3, 4},
            {2, 1, 3, 4, 0},
            {1, 3, 4, 0, 2},
            {3, 4, 0, 2, 1},
            {4, 0, 2, 1, 3},
            {2, 1, 3, 0, 4},
            {1, 3, 0, 4, 2},
            {3, 0, 4, 2, 1},
            {0, 4, 2, 1, 3},
            {4, 2, 1, 3, 0},
            {1, 3, 0, 2, 4},
            {3, 0, 2, 4, 1},
            {0, 2, 4, 1, 3},
            {2, 4, 1, 3, 0},
            {4, 1, 3, 0, 2},
            {3, 0, 2, 1, 4},
            {0, 2, 1, 4, 3},
            {2, 1, 4, 3, 0},
            {1, 4, 3, 0, 2},
            {4, 3, 0, 2, 1},
            {2, 1, 0, 3, 4},
            {1, 0, 3, 4, 2},
            {0, 3, 4, 2, 1},
            {3, 4, 2, 1, 0},
            {4, 2, 1, 0, 3},
            {1, 0, 3, 2, 4},
            {0, 3, 2, 4, 1},
            {3, 2, 4, 1, 0},
            {2, 4, 1, 0, 3},
            {4, 1, 0, 3, 2},
            {0, 3, 2, 1, 4},
            {3, 2, 1, 4, 0},
            {2, 1, 4, 0, 3},
            {1, 4, 0, 3, 2},
            {4, 0, 3, 2, 1},
            {3, 2, 1, 0, 4},
            {2, 1, 0, 4, 3},
            {1, 0, 4, 3, 2},
            {0, 4, 3, 2, 1},
            {4, 3, 2, 1, 0}
    };

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CyclicShiftTest.class,
                          System.lineSeparator());
    }

    @Test
    void generate() {
        for (int arraySize = 0; arraySize < 7; arraySize++) {
            var result = CyclicShift.generate(arraySize);
            Assertions.assertEquals(CombinatoricsCalc.countPermutationsNoRepetition(arraySize),
                                    result.size());
            for (var permutation : result)
                Assertions.assertEquals(arraySize, permutation.length);
        }
    }

    @Test
    void given_array_return_its_permutations() {
        int[] array = new int[]{1, 0, -1, -1, 0, -1, 1, 1, -1, 0, 0};
        List<Integer> addresses = new ArrayList<>();
        List<int[]> permutations = new ArrayList<>();
        for(int i = 0; i < array.length; i++) if (array[i] == -1) addresses.add(i);
        List<int[]> permz = CyclicShift.generate(addresses.size()).stream().limit(addresses.size()).toList();
        for (int[] permutation : permz) {
            List<Integer> permutationValues = new ArrayList<>();
            permutationValues.add(1);
            for (int n = 1; n < permutation.length; n++)
                permutationValues.add(0);
            int[] arr = new int[array.length];
            int j = 0;
            System.arraycopy(array, 0, arr, 0, array.length);
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == -1) {
                    arr[i] = permutationValues.get(permutation[j++]);
                }
            }
            permutations.add(arr);
        }
        List<int[]> result = new ArrayList<>();
        result.addFirst(permutations.getFirst());
        int j = 1;
        for (int i = permutations.size() - 1; i > 0; i--) {
            result.add(j++, permutations.get(i));
        }
        for (var a : result) {
            System.out.println(Arrays.toString(a));
        }
    }

    @Test
    void shouldGenerateSamePermutationsAsGiven() {
        List<int[]> result = CyclicShift.generate(5);
        for (int i = 0; i < permutationsGiven.length; i++) {
            assertArrayEquals(permutationsGiven[i], result.get(i));
        }
    }

    @Test
    void shouldGenerateExpectedNumberOfPermutations() {
        List<int[]> result = CyclicShift.generate(5);
        assertEquals(CyclicShift.count(5), result.size());
    }

    @Test
    void shouldReturnSameCountOfElementsForBothMethods() {
        assertEquals(
                CyclicShift.count(7),
                CombinatoricsCalc.countPermutationsNoRepetition(7));
    }
    //@Test
    private void print() {
        CyclicShift.printf(System.out, 5, arr -> Arrays.toString(arr)
                .replace("[", "{").replace("]", "}"));
    }
}
