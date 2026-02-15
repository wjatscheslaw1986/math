/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NarayanaPermutationsGeneratorTest {

    @Test
    void shouldGenerateSamePermutationsAsGiven() {
        NarayanaPermutationsGenerator gen = new NarayanaPermutationsGenerator(5);
        int i = 0;
        while (gen.hasNext()) {
            assertArrayEquals(NarayanaUtilTest.permutationsGiven[i++], gen.next());
        }
    }

    @Test
    void shouldGenerateFirst5PermutationsFromTheGivenArrayOfPermutations() {
        int i = 0;
        for (int[] permutation : NarayanaUtil.generateFirstN(5, 5))
            assertArrayEquals(NarayanaUtilTest.permutationsGiven[i++], permutation);
        assertEquals(5, i);
    }
}
