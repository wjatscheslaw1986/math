package combinatorics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@linkplain combinatorics.JohnsonTrotterGenerator} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class JohnsonTrotterGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", JohnsonTrotterGeneratorTest.class, System.lineSeparator());
    }

    @Test
    void generate() {
        JohnsonTrotterGenerator.print(3, System.out);
    }

}
