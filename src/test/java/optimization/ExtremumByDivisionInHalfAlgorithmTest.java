/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Equation;
import algebra.Letter;
import algebra.Term;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtremumByDivisionInHalfAlgorithmTest {

    @Test
    @DisplayName("Compare the extremum (min) for a given function, on a given range, to an expected one")
    void givenFunctionAndRange_whenSearchForExtremumWithByDivisionInHalf_thenExpectedResults() {
        var letter = Letter.of("x", 0);
        var algorithm = ExtremumByDivisionInHalfAlgorithm.of(Equation.of(
                List.of(
                        Term.builder()
                                .letter(letter)
                                .power(2.0d)
                                .coefficient(3.0d)
                                .build(),
                        Term.builder()
                                .letter(letter)
                                .power(-3.0d)
                                .coefficient(12.0d)
                                .build(),
                        Term.asRealConstant(-5)
                ),
                null
        ));
        assertEquals(1.4378125, algorithm.getExtremumX(0.5d, 2.5d, 0.1d, 0.01d));
        assertEquals(5, algorithm.getStepsCount());

        algorithm = ExtremumByDivisionInHalfAlgorithm.of(Equation.of(
                List.of(
                        Term.builder()
                                .letter(letter)
                                .power(4.0d)
                                .coefficient(3.0d)
                                .build(),
                        Term.builder()
                                .letter(letter)
                                .power(2.0d)
                                .coefficient(1.0d)
                                .build(),
                        Term.builder()
                                .letter(letter)
                                .power(1.0d)
                                .coefficient(-2.0d)
                                .build(),
                        Term.asRealConstant(1)
                ),
                null
        ));
        assertEquals(0.5671875, algorithm.getExtremumX(0.5d, 2.5d, 0.1d, 0.01d));
        assertEquals(5, algorithm.getStepsCount());
    }
}
