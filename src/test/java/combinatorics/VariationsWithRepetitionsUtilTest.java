/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static combinatorics.CombinatoricsCalc.countVariationsWithRepetitions;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link VariationsWithRepetitionsUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class VariationsWithRepetitionsUtilTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", VariationsWithRepetitionsUtilTest.class, System.lineSeparator());
    }

    @Test
    void generateVariationsWithRepetitionsTest() {
        int n = 5, k = 3;
        var repetitions = VariationsWithRepetitionsUtil.generate(n, k);
        assertEquals(countVariationsWithRepetitions(n, k), repetitions.size());
        VariationsWithRepetitionsUtil.print(n, k, System.out);

        VariationsWithRepetitionsGenerator gen = new VariationsWithRepetitionsGenerator(n, k);
        while (gen.hasNext()) {
            System.out.println(Arrays.toString(gen.next()));
        }
    }
}
