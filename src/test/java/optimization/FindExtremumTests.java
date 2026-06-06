/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Letter;
import algebra.Term;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindExtremumTests {

    @Test
    @DisplayName("Compare the extremum (min) for a given function, on a given range, to an expected one")
    void givenFunctionAndRange_whenSearchForExtremumByDivisionInHalf_thenExpectedResults() {
        var letter = Letter.of("x", 0);
        var algorithm = ExtremumByDivisionInHalfAlgorithm.of(
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
                )
        );
        assertEquals(1.4378125, algorithm.getExtremumX(0.5d, 2.5d, 0.1d, 0.01d));
        assertEquals(5, algorithm.getStepsCount());

        algorithm = ExtremumByDivisionInHalfAlgorithm.of(
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
                ));
        assertEquals(0.5671875, algorithm.getExtremumX(0.5d, 2.5d, 0.1d, 0.01d));
        assertEquals(5, algorithm.getStepsCount());

        algorithm = ExtremumByDivisionInHalfAlgorithm.of(
                List.of(Term.asVariableX(1.0d)),
                List.of(x -> 4 * x * Math.sin(x))
        );
        assertEquals(0.13, algorithm.getExtremumX(.0d, Math.PI, 0.2d, 0.08d), 0.01);
        assertEquals(5, algorithm.getStepsCount());
    }

    @Test
    @DisplayName("Compare the extremum (min) for a given function, on a given range, to an expected one")
    void givenFunctionAndRange_whenSearchForExtremumWithByGoldenRation_thenExpectedResults() {
        var letter = Letter.of("x", 0);
        var algorithm = ExtremumByGoldenRatioAlgorithm.of(
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
                )
        );
        assertEquals(1.466, algorithm.getExtremum(0.5d, 2.5d, 0.1d), .001d);
        assertEquals(6, algorithm.getStepsCount());

        algorithm = ExtremumByGoldenRatioAlgorithm.of(
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
                )
        );
        assertEquals(0.59d, algorithm.getExtremum(0.5d, 2.5d, 0.1d), .001d);
        assertEquals(6, algorithm.getStepsCount());
        assertEquals(0.429d, algorithm.getExtremum(.0d, 4.0d, 0.1d), .001d);
        assertEquals(14, algorithm.getStepsCount());

        algorithm = ExtremumByGoldenRatioAlgorithm.of(
                List.of(Term.asVariableX(1.0d)),
                List.of(x -> 4 * x * Math.sin(x))
        );
        assertEquals(0.141, algorithm.getExtremum(.0d, Math.PI, 0.2d), 0.001);
        assertEquals(6, algorithm.getStepsCount());
    }
}
