package combinatorics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IndexCombinationsGeneratorTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", IndexCombinationsGeneratorTest.class, System.lineSeparator());
    }

    @Test
    void generate() {
        final int n = 10;
        for (int k = 0; k < n; k++) {
            var result = VariationsWithRepetitionsGenerator.generate(n, k);
            Assertions.assertEquals(CombinatoricsCalc.binomialCoefficient(n, k), result.size());
            for (var choice : result)
                Assertions.assertEquals(k, choice.length);
            VariationsWithRepetitionsGenerator.print(n, k, System.out);
        }
    }
}
