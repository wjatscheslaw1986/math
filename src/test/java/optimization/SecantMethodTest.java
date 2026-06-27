/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Term;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static functional.FunctionUtil.calculateSingleVariableFunctionValueAtGivenX;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Comprehensive JUnit tests for {@link SecantMethod}.
 *
 * Covers extremum finding (f'(x) = 0).
 */
class SecantMethodTest {

    @Nested
    @DisplayName("Function 2: f(x) = 3x⁴ + (x-1)²")
    class Function2Tests {

        @Test
        @DisplayName("Should find min in [0.5, 2.5]")
        void testMinOfSecondFunction() {
            // f(x) = 3x⁴ + (x-1)² = 3x⁴ + x² - 2x + 1
            List<Term> function = List.of(
                    Term.builder().power(4.0).coefficient(3.0).build(),
                    Term.builder().power(2.0).coefficient(1.0).build(),
                    Term.builder().power(1.0).coefficient(-2.0).build(),
                    Term.asRealConstant(1.0)
            );

            // f(x) = 12x³ + 2x - 2
            List<Term> derivative = List.of(
                    Term.builder().power(3.0).coefficient(12.0).build(),
                    Term.builder().power(1.0).coefficient(2.0).build(),
                    Term.asRealConstant(-2.0)
            );

            SecantMethod solver = SecantMethod.of(derivative);

            double minimumX = solver.getExtremumX(0.1, 1.0, 1e-10, 1e-9);

            assertEquals(0.450698825, minimumX,1e-9);
            assertEquals(0.425516477, calculateSingleVariableFunctionValueAtGivenX(function, minimumX), 1e-9);
        }
    }
}