package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JohnsonTrotterUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class JohnsonTrotterUtilTest {

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
        System.out.printf("Running tests in %s%s", JohnsonTrotterUtilTest.class, System.lineSeparator());
    }

    @Test
    void shouldGenerateSamePermutationsSameOrderedAsCyclicShiftPermutationsGenerator() {
        for (int i = 1; i < 7; i++) {
            List<int[]> l1 = CyclicShiftPermutationsGenerator.generate(i);
            List<int[]> l2 = JohnsonTrotterUtil.generate(i);
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
        List<int[]> result = JohnsonTrotterUtil.generate(5);
        for (int i = 0; i < permutationsGiven.length; i++) {
            assertArrayEquals(JohnsonTrotterUtilTest.permutationsGiven[i], result.get(i));
        }
    }

    @Test
    void shouldGenerateExpectedNumberOfPermutations() {
        List<int[]> result = JohnsonTrotterUtil.generate(5);
        assertEquals(CombinatoricsUtil.countPermutationsNoRepetitions(5), result.size());
    }
}
