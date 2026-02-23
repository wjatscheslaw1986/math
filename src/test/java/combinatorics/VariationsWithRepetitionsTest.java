/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static combinatorics.CombinatoricsCalc.countVariationsWithRepetition;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link VariationsWithRepetitions} class.
 *
 * @author Viacheslav Mikhailov
 */
public class VariationsWithRepetitionsTest {

    private static int[][] variationsGiven = {
            {0, 0, 0},
            {0, 0, 1},
            {0, 0, 2},
            {0, 0, 3},
            {0, 0, 4},
            {0, 1, 0},
            {0, 1, 1},
            {0, 1, 2},
            {0, 1, 3},
            {0, 1, 4},
            {0, 2, 0},
            {0, 2, 1},
            {0, 2, 2},
            {0, 2, 3},
            {0, 2, 4},
            {0, 3, 0},
            {0, 3, 1},
            {0, 3, 2},
            {0, 3, 3},
            {0, 3, 4},
            {0, 4, 0},
            {0, 4, 1},
            {0, 4, 2},
            {0, 4, 3},
            {0, 4, 4},
            {1, 0, 0},
            {1, 0, 1},
            {1, 0, 2},
            {1, 0, 3},
            {1, 0, 4},
            {1, 1, 0},
            {1, 1, 1},
            {1, 1, 2},
            {1, 1, 3},
            {1, 1, 4},
            {1, 2, 0},
            {1, 2, 1},
            {1, 2, 2},
            {1, 2, 3},
            {1, 2, 4},
            {1, 3, 0},
            {1, 3, 1},
            {1, 3, 2},
            {1, 3, 3},
            {1, 3, 4},
            {1, 4, 0},
            {1, 4, 1},
            {1, 4, 2},
            {1, 4, 3},
            {1, 4, 4},
            {2, 0, 0},
            {2, 0, 1},
            {2, 0, 2},
            {2, 0, 3},
            {2, 0, 4},
            {2, 1, 0},
            {2, 1, 1},
            {2, 1, 2},
            {2, 1, 3},
            {2, 1, 4},
            {2, 2, 0},
            {2, 2, 1},
            {2, 2, 2},
            {2, 2, 3},
            {2, 2, 4},
            {2, 3, 0},
            {2, 3, 1},
            {2, 3, 2},
            {2, 3, 3},
            {2, 3, 4},
            {2, 4, 0},
            {2, 4, 1},
            {2, 4, 2},
            {2, 4, 3},
            {2, 4, 4},
            {3, 0, 0},
            {3, 0, 1},
            {3, 0, 2},
            {3, 0, 3},
            {3, 0, 4},
            {3, 1, 0},
            {3, 1, 1},
            {3, 1, 2},
            {3, 1, 3},
            {3, 1, 4},
            {3, 2, 0},
            {3, 2, 1},
            {3, 2, 2},
            {3, 2, 3},
            {3, 2, 4},
            {3, 3, 0},
            {3, 3, 1},
            {3, 3, 2},
            {3, 3, 3},
            {3, 3, 4},
            {3, 4, 0},
            {3, 4, 1},
            {3, 4, 2},
            {3, 4, 3},
            {3, 4, 4},
            {4, 0, 0},
            {4, 0, 1},
            {4, 0, 2},
            {4, 0, 3},
            {4, 0, 4},
            {4, 1, 0},
            {4, 1, 1},
            {4, 1, 2},
            {4, 1, 3},
            {4, 1, 4},
            {4, 2, 0},
            {4, 2, 1},
            {4, 2, 2},
            {4, 2, 3},
            {4, 2, 4},
            {4, 3, 0},
            {4, 3, 1},
            {4, 3, 2},
            {4, 3, 3},
            {4, 3, 4},
            {4, 4, 0},
            {4, 4, 1},
            {4, 4, 2},
            {4, 4, 3},
            {4, 4, 4}
    };

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", VariationsWithRepetitionsTest.class, System.lineSeparator());
    }

    @Test
    void shouldGenerateSameVariationsAsGiven() {
        List<int[]> variationList = VariationsWithRepetitions.generate(5, 3);
        int i = 0;
        for (int[] permutation : variationsGiven)
            assertArrayEquals(permutation, variationList.get(i++));
        assertEquals(variationsGiven.length, variationList.size());
    }

    @Test
    void generateVariationsWithRepetitionsTest() {
        int n = 5, k = 3;
        var repetitions = VariationsWithRepetitions.generate(n, k);
        assertEquals(countVariationsWithRepetition(n, k), repetitions.size());
        assertEquals(125, repetitions.size());

    }

    @Test
    void shouldReturn125WhenCountVariationsWithRepetitionsWhen3Of5Elements() {
        assertEquals(125, VariationsWithRepetitions.count(5, 3));
    }

    @Test
    void shouldReturnSameCountOfElementsForBothMethods() {
        assertEquals(
                VariationsWithRepetitions.count(16, 5),
                CombinatoricsCalc.countVariationsWithRepetition(16, 5));
    }

//    @Test
    void print() {
        CombinatoricsUtil.printJavaCode(new VariationsWithRepetitionsGenerator(5, 3), System.out);
    }
}
