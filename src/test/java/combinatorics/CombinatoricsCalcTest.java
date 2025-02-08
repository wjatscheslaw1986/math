/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsCalc.countVariationsWithRepetitions;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@linkplain combinatorics.CombinatoricsCalc} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class CombinatoricsCalcTest {

    @Test
    void countVariationsWithRepetitionsTest() {
        assertEquals(125L, countVariationsWithRepetitions(5, 3));
    }
}
