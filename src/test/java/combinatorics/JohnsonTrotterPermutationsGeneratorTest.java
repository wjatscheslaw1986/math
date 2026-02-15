/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package combinatorics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JohnsonTrotterPermutationsGeneratorTest {

    @Test
    void shouldGenerateSamePermutationsAsGiven() {
        JohnsonTrotterPermutationsGenerator gen = new JohnsonTrotterPermutationsGenerator(5);
        int i = 0;
        while (gen.hasNext()) {
            assertArrayEquals(gen.next(), JohnsonTrotterUtilTest.permutationsGiven[i++]);
        }
        assertEquals(120, CombinatoricsUtil.countPermutationsNoRepetitions(5));
    }

    @Test
    void shouldGenerateFirst7PermutationsFromTheGivenCardinalityOfIndicesArray() {
        int i = 0;
        for (int[] permutation : JohnsonTrotterUtil.generateFirstN(5, 7))
            assertArrayEquals(JohnsonTrotterUtilTest.permutationsGiven[i++], permutation);
        assertEquals(7, i);
    }
}
