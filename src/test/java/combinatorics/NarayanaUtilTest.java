/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static combinatorics.NarayanaUtil.reverseOrderAfterIndex;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests for {@link NarayanaUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class NarayanaUtilTest {

    static int[][] permutationsGiven = new int[][]{
            {0, 1, 2, 3, 4},
            {0, 1, 2, 4, 3},
            {0, 1, 3, 2, 4},
            {0, 1, 3, 4, 2},
            {0, 1, 4, 2, 3},
            {0, 1, 4, 3, 2},
            {0, 2, 1, 3, 4},
            {0, 2, 1, 4, 3},
            {0, 2, 3, 1, 4},
            {0, 2, 3, 4, 1},
            {0, 2, 4, 1, 3},
            {0, 2, 4, 3, 1},
            {0, 3, 1, 2, 4},
            {0, 3, 1, 4, 2},
            {0, 3, 2, 1, 4},
            {0, 3, 2, 4, 1},
            {0, 3, 4, 1, 2},
            {0, 3, 4, 2, 1},
            {0, 4, 1, 2, 3},
            {0, 4, 1, 3, 2},
            {0, 4, 2, 1, 3},
            {0, 4, 2, 3, 1},
            {0, 4, 3, 1, 2},
            {0, 4, 3, 2, 1},
            {1, 0, 2, 3, 4},
            {1, 0, 2, 4, 3},
            {1, 0, 3, 2, 4},
            {1, 0, 3, 4, 2},
            {1, 0, 4, 2, 3},
            {1, 0, 4, 3, 2},
            {1, 2, 0, 3, 4},
            {1, 2, 0, 4, 3},
            {1, 2, 3, 0, 4},
            {1, 2, 3, 4, 0},
            {1, 2, 4, 0, 3},
            {1, 2, 4, 3, 0},
            {1, 3, 0, 2, 4},
            {1, 3, 0, 4, 2},
            {1, 3, 2, 0, 4},
            {1, 3, 2, 4, 0},
            {1, 3, 4, 0, 2},
            {1, 3, 4, 2, 0},
            {1, 4, 0, 2, 3},
            {1, 4, 0, 3, 2},
            {1, 4, 2, 0, 3},
            {1, 4, 2, 3, 0},
            {1, 4, 3, 0, 2},
            {1, 4, 3, 2, 0},
            {2, 0, 1, 3, 4},
            {2, 0, 1, 4, 3},
            {2, 0, 3, 1, 4},
            {2, 0, 3, 4, 1},
            {2, 0, 4, 1, 3},
            {2, 0, 4, 3, 1},
            {2, 1, 0, 3, 4},
            {2, 1, 0, 4, 3},
            {2, 1, 3, 0, 4},
            {2, 1, 3, 4, 0},
            {2, 1, 4, 0, 3},
            {2, 1, 4, 3, 0},
            {2, 3, 0, 1, 4},
            {2, 3, 0, 4, 1},
            {2, 3, 1, 0, 4},
            {2, 3, 1, 4, 0},
            {2, 3, 4, 0, 1},
            {2, 3, 4, 1, 0},
            {2, 4, 0, 1, 3},
            {2, 4, 0, 3, 1},
            {2, 4, 1, 0, 3},
            {2, 4, 1, 3, 0},
            {2, 4, 3, 0, 1},
            {2, 4, 3, 1, 0},
            {3, 0, 1, 2, 4},
            {3, 0, 1, 4, 2},
            {3, 0, 2, 1, 4},
            {3, 0, 2, 4, 1},
            {3, 0, 4, 1, 2},
            {3, 0, 4, 2, 1},
            {3, 1, 0, 2, 4},
            {3, 1, 0, 4, 2},
            {3, 1, 2, 0, 4},
            {3, 1, 2, 4, 0},
            {3, 1, 4, 0, 2},
            {3, 1, 4, 2, 0},
            {3, 2, 0, 1, 4},
            {3, 2, 0, 4, 1},
            {3, 2, 1, 0, 4},
            {3, 2, 1, 4, 0},
            {3, 2, 4, 0, 1},
            {3, 2, 4, 1, 0},
            {3, 4, 0, 1, 2},
            {3, 4, 0, 2, 1},
            {3, 4, 1, 0, 2},
            {3, 4, 1, 2, 0},
            {3, 4, 2, 0, 1},
            {3, 4, 2, 1, 0},
            {4, 0, 1, 2, 3},
            {4, 0, 1, 3, 2},
            {4, 0, 2, 1, 3},
            {4, 0, 2, 3, 1},
            {4, 0, 3, 1, 2},
            {4, 0, 3, 2, 1},
            {4, 1, 0, 2, 3},
            {4, 1, 0, 3, 2},
            {4, 1, 2, 0, 3},
            {4, 1, 2, 3, 0},
            {4, 1, 3, 0, 2},
            {4, 1, 3, 2, 0},
            {4, 2, 0, 1, 3},
            {4, 2, 0, 3, 1},
            {4, 2, 1, 0, 3},
            {4, 2, 1, 3, 0},
            {4, 2, 3, 0, 1},
            {4, 2, 3, 1, 0},
            {4, 3, 0, 1, 2},
            {4, 3, 0, 2, 1},
            {4, 3, 1, 0, 2},
            {4, 3, 1, 2, 0},
            {4, 3, 2, 0, 1},
            {4, 3, 2, 1, 0}
    };

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", NarayanaUtilTest.class, System.lineSeparator());
    }

    @Test
    void shouldGenerateSamePermutationsAsGiven() {
        List<int[]> permutationList = NarayanaUtil.generate(5);
        int i = 0;
        for (int[] permutation : permutationsGiven)
            assertArrayEquals(permutation, permutationList.get(i++));
    }

    @Test
    void reverseOrderAfterIndexTest() {
        final int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        int[][] shifts = new int[][]{
                new int[]{1, 2, 3, 4, 5, 6, 7, 9, 8},
                new int[]{1, 2, 3, 4, 5, 6, 9, 8, 7},
                new int[]{1, 2, 3, 4, 5, 9, 8, 7, 6},
                new int[]{1, 2, 3, 4, 9, 8, 7, 6, 5},
                new int[]{1, 2, 3, 9, 8, 7, 6, 5, 4},
                new int[]{1, 2, 9, 8, 7, 6, 5, 4, 3},
                new int[]{1, 9, 8, 7, 6, 5, 4, 3, 2},
                new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}};

        for (int i = 0; i < arr.length - 3; i++) {
            final int[] copy = Arrays.copyOf(arr, arr.length);
            assertFalse(Arrays.equals(copy, shifts[i]));
            reverseOrderAfterIndex(copy, arr.length - 3 - i);
            assertArrayEquals(copy, shifts[i]);
        }
    }
}
