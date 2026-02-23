package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JohnsonTrotter} class.
 *
 * @author Viacheslav Mikhailov
 */
public class JohnsonTrotterTest {

    static int[][] permutationsGiven = new int[][]{
            {0, 1, 2, 3, 4},
            {0, 1, 2, 4, 3},
            {0, 1, 4, 2, 3},
            {0, 4, 1, 2, 3},
            {4, 0, 1, 2, 3},
            {4, 0, 1, 3, 2},
            {0, 4, 1, 3, 2},
            {0, 1, 4, 3, 2},
            {0, 1, 3, 4, 2},
            {0, 1, 3, 2, 4},
            {0, 3, 1, 2, 4},
            {0, 3, 1, 4, 2},
            {0, 3, 4, 1, 2},
            {0, 4, 3, 1, 2},
            {4, 0, 3, 1, 2},
            {4, 3, 0, 1, 2},
            {3, 4, 0, 1, 2},
            {3, 0, 4, 1, 2},
            {3, 0, 1, 4, 2},
            {3, 0, 1, 2, 4},
            {3, 0, 2, 1, 4},
            {3, 0, 2, 4, 1},
            {3, 0, 4, 2, 1},
            {3, 4, 0, 2, 1},
            {4, 3, 0, 2, 1},
            {4, 0, 3, 2, 1},
            {0, 4, 3, 2, 1},
            {0, 3, 4, 2, 1},
            {0, 3, 2, 4, 1},
            {0, 3, 2, 1, 4},
            {0, 2, 3, 1, 4},
            {0, 2, 3, 4, 1},
            {0, 2, 4, 3, 1},
            {0, 4, 2, 3, 1},
            {4, 0, 2, 3, 1},
            {4, 0, 2, 1, 3},
            {0, 4, 2, 1, 3},
            {0, 2, 4, 1, 3},
            {0, 2, 1, 4, 3},
            {0, 2, 1, 3, 4},
            {2, 0, 1, 3, 4},
            {2, 0, 1, 4, 3},
            {2, 0, 4, 1, 3},
            {2, 4, 0, 1, 3},
            {4, 2, 0, 1, 3},
            {4, 2, 0, 3, 1},
            {2, 4, 0, 3, 1},
            {2, 0, 4, 3, 1},
            {2, 0, 3, 4, 1},
            {2, 0, 3, 1, 4},
            {2, 3, 0, 1, 4},
            {2, 3, 0, 4, 1},
            {2, 3, 4, 0, 1},
            {2, 4, 3, 0, 1},
            {4, 2, 3, 0, 1},
            {4, 3, 2, 0, 1},
            {3, 4, 2, 0, 1},
            {3, 2, 4, 0, 1},
            {3, 2, 0, 4, 1},
            {3, 2, 0, 1, 4},
            {3, 2, 1, 0, 4},
            {3, 2, 1, 4, 0},
            {3, 2, 4, 1, 0},
            {3, 4, 2, 1, 0},
            {4, 3, 2, 1, 0},
            {4, 2, 3, 1, 0},
            {2, 4, 3, 1, 0},
            {2, 3, 4, 1, 0},
            {2, 3, 1, 4, 0},
            {2, 3, 1, 0, 4},
            {2, 1, 3, 0, 4},
            {2, 1, 3, 4, 0},
            {2, 1, 4, 3, 0},
            {2, 4, 1, 3, 0},
            {4, 2, 1, 3, 0},
            {4, 2, 1, 0, 3},
            {2, 4, 1, 0, 3},
            {2, 1, 4, 0, 3},
            {2, 1, 0, 4, 3},
            {2, 1, 0, 3, 4},
            {1, 2, 0, 3, 4},
            {1, 2, 0, 4, 3},
            {1, 2, 4, 0, 3},
            {1, 4, 2, 0, 3},
            {4, 1, 2, 0, 3},
            {4, 1, 2, 3, 0},
            {1, 4, 2, 3, 0},
            {1, 2, 4, 3, 0},
            {1, 2, 3, 4, 0},
            {1, 2, 3, 0, 4},
            {1, 3, 2, 0, 4},
            {1, 3, 2, 4, 0},
            {1, 3, 4, 2, 0},
            {1, 4, 3, 2, 0},
            {4, 1, 3, 2, 0},
            {4, 3, 1, 2, 0},
            {3, 4, 1, 2, 0},
            {3, 1, 4, 2, 0},
            {3, 1, 2, 4, 0},
            {3, 1, 2, 0, 4},
            {3, 1, 0, 2, 4},
            {3, 1, 0, 4, 2},
            {3, 1, 4, 0, 2},
            {3, 4, 1, 0, 2},
            {4, 3, 1, 0, 2},
            {4, 1, 3, 0, 2},
            {1, 4, 3, 0, 2},
            {1, 3, 4, 0, 2},
            {1, 3, 0, 4, 2},
            {1, 3, 0, 2, 4},
            {1, 0, 3, 2, 4},
            {1, 0, 3, 4, 2},
            {1, 0, 4, 3, 2},
            {1, 4, 0, 3, 2},
            {4, 1, 0, 3, 2},
            {4, 1, 0, 2, 3},
            {1, 4, 0, 2, 3},
            {1, 0, 4, 2, 3},
            {1, 0, 2, 4, 3},
            {1, 0, 2, 3, 4}
    };

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", JohnsonTrotterTest.class, System.lineSeparator());
    }

    @Test
    void shouldGenerateSamePermutationsAsCyclicShiftAlgorithm() {
        for (int i = 1; i < 7; i++) {
            List<int[]> l1 = CyclicShift.generate(i);
            List<int[]> l2 = JohnsonTrotter.generate(i);
            assertEquals(l1.size(), l2.size());

            for (int[] i1s : l1) {
                var found = false;
                for (int[] i2s : l2) {
                    if (Arrays.equals(i2s, i1s)) {
                        found = true;
                        break;
                    }
                }
                assertTrue(found);
            }
        }
    }

    @Test
    void shouldGenerateSamePermutationsAsGiven() {
        List<int[]> result = JohnsonTrotter.generate(5);
        for (int i = 0; i < permutationsGiven.length; i++) {
            assertArrayEquals(JohnsonTrotterTest.permutationsGiven[i], result.get(i));
        }
    }

    @Test
    void shouldGenerateExpectedNumberOfPermutations() {
        List<int[]> result = JohnsonTrotter.generate(5);
        assertEquals(JohnsonTrotter.count(5), result.size());
    }

    @Test
    void shouldReturnSameCountOfElementsForBothMethods() {
        assertEquals(
                JohnsonTrotter.count(7),
                CombinatoricsCalc.countPermutationsNoRepetition(7));
    }
}
