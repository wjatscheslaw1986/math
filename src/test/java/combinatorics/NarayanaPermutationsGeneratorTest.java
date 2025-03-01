/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com)  © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static combinatorics.NarayanaPermutationsGenerator.reverseOrderAfterIndex;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@linkplain NarayanaPermutationsGenerator} class.
 *
 * @author Viacheslav Mikhailov
 */
public class NarayanaPermutationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", NarayanaPermutationsGeneratorTest.class, System.lineSeparator());
    }

    @Test
    void test() {
        assertTrue(NarayanaPermutationsGenerator.generate(5).stream()
                .map(Arrays::toString)
                .map(s -> s.replaceAll("[\\[\\],]", ""))
                .reduce((a, b) -> { assertTrue(a.compareTo(b) < 0); return a; })
                .isPresent());

//        NarayanaPermutationsGenerator.generate(5).stream()
//                .map(Arrays::toString)
//                .forEach(System.out::println);
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
