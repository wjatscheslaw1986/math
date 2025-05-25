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
import java.util.stream.Collectors;

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
        }
    }

    @Test
    void given_array_return_its_permutations() {
        int[] array = new int[]{1, 0, -1, -1, 0, -1, 1, 1, -1, 0, 0};
        List<Integer> addresses = new ArrayList<>();
        List<int[]> permutations = new ArrayList<>();
        for(int i = 0; i < array.length; i++) if (array[i] == -1) addresses.add(i);
        List<int[]> permz = CyclicShiftPermutationsGenerator.generate(addresses.size()).stream().limit(addresses.size()).toList();
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
}
