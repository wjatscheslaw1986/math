/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsCalc.countVariationsWithRepetitions;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@linkplain combinatorics.RepetitionsGenerator} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class RepetitionsGeneratorTest {

    @Test
    void generateRepetitions() {
        int n = 5, k = 3;
        var repetitions = RepetitionsGenerator.generate(n, k);
        assertEquals(countVariationsWithRepetitions(n, k), repetitions.size());
        RepetitionsGenerator.print(n, k, System.out);
    }
}
