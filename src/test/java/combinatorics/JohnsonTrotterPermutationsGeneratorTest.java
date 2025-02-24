package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@linkplain JohnsonTrotterPermutationsGenerator} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class JohnsonTrotterPermutationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", JohnsonTrotterPermutationsGeneratorTest.class, System.lineSeparator());
    }

    @Test
    void generate() {
        JohnsonTrotterPermutationsGenerator.print(3, System.out);
    }

}
