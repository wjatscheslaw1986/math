/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsCalc.countVariationsWithRepetitions;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@linkplain VariationsWithRepetitionsGenerator} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class VariationsWithRepetitionsGeneratorTest {

    @Test
    void generateVariationsWithRepetitionsTest() {
        int n = 5, k = 3;
        var repetitions = VariationsWithRepetitionsGenerator.generate(n, k);
        assertEquals(countVariationsWithRepetitions(n, k), repetitions.size());
        VariationsWithRepetitionsGenerator.print(n, k, System.out);
    }
}
