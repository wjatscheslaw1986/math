/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static combinatorics.CombinatoricsUtil.getPrintIntArrayFunction;

/**
 * Tests for {@link Heap} class.
 *
 * @author Viacheslav Mikhailov
 */
class HeapTest {

    private int[][] expectedPermutationsForLength3 = {
            {1, 2, 3},
            {2, 1, 3},
            {3, 1, 2},
            {1, 3, 2},
            {2, 3, 1},
            {3, 2, 1},
    };

    @Test
    void shouldGenerateExpectedPermutationsForLength3() {
        var hip = new Heap(3).generate();
        for (int i = 0; i < hip.length; i++) {
            Assertions.assertArrayEquals(expectedPermutationsForLength3[i], hip[i]);
        }
    }

    //@Test
    void print() {
        var printf = getPrintIntArrayFunction(System.out, arr -> Arrays.toString(arr).replace("[", "{").replace("]", "}"));
        var hip = new Heap(3).generate();
        Arrays.stream(hip).forEach(printf);
    }
}
