/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Letter;
import algebra.Term;
import exception.MathException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
    void shouldReturnExpectedMinimumWhenBolzanoSearchAppliedToDerivativeOfFunction() {
        DoubleUnaryOperator derivativeOfFunciton = x -> 6*x - (36 / Math.pow(x, 4));
        var algorithm = BolzanoSearch.of(
                List.of(Term.asVariableX(1.0d)),
                List.of(derivativeOfFunciton)
        );
        assertEquals(1.4375, algorithm.getExtremumX(.5d, 2.5d, 0.1d, 0.1d), 0.01);
        assertEquals(5, algorithm.getStepsCount());

        assertEquals(

                BolzanoSearch.of(
                List.of(Term.asVariableX(1.0d)),
                List.of(derivativeOfFunciton)).getExtremumX(.5d, 2.5d, 0.01d, 0.01d),

                ExtremumByDivisionInHalfAlgorithm.of(
                        List.of(
                                Term.builder()
                                        .power(2.0d)
                                        .coefficient(3.0d)
                                        .build(),
                                Term.builder()
                                        .power(-3.0d)
                                        .coefficient(12.0d)
                                        .build(),
                                Term.asRealConstant(-5)
                        )
                ).getExtremumX(.5d, 2.5d, 0.01d, 0.01d),
                0.01d
                );
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

    @Test
    void shouldFindExpectedIntervalForTheGivenFunctionAndInitialX() {
        SwannAlgorithm swannAlgorithm = SwannAlgorithm.of(
                x -> (3 * Math.pow(x, 2) + (12 / Math.pow(x, 3)) - 5), 0.1d);
        double[] intervalWithMinimum;
        try {
            intervalWithMinimum = swannAlgorithm.getInterval(1.25d);
        } catch (MathException e) {
            throw new RuntimeException(e);
        }
        assertArrayEquals(new double[]{ 1.25d, 1.55d}, intervalWithMinimum);
        try {
            intervalWithMinimum = swannAlgorithm.getInterval(0.25d);
        } catch (MathException e) {
            throw new RuntimeException(e);
        }
        assertArrayEquals(new double[]{ .75d, 3.15d}, intervalWithMinimum, 1e-15);

        try {
            intervalWithMinimum = swannAlgorithm.getInterval(5.25d);
        } catch (MathException e) {
            throw new RuntimeException(e);
        }
        assertArrayEquals(new double[]{ -4.45d, 5.15d}, intervalWithMinimum, 1e-15);
    }
}
