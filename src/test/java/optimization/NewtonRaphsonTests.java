/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import exception.MathException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class NewtonRaphsonTests {

    private static final double DELTA = 1e-8;

    // ==================== Main Method Tests ====================

    @Test
    @DisplayName("Should find root of x² - 2 = 0")
    void testSquareRoot() throws MathException {
        DoubleUnaryOperator f = x -> x * x - 2;
        DoubleUnaryOperator df = x -> 2 * x;

        double[] interval = {1.0, 3.0};
        double root = NewtonRaphson.findEquationRoot(interval, f, df);

        assertEquals(Math.sqrt(2), root, DELTA);
    }

    @Test
    @DisplayName("Should find root of x³ - x - 2 = 0")
    void testCubicRoot() throws MathException {
        DoubleUnaryOperator f = x -> x * x * x - x - 2;
        DoubleUnaryOperator df = x -> 3 * x * x - 1;

        double[] interval = {1.0, 2.0};
        double root = NewtonRaphson.findEquationRoot(interval, f, df, 1e-10, 50);

        assertTrue(Math.abs(f.applyAsDouble(root)) < 1e-8);
    }

    @Test
    @DisplayName("Should find root of sin(x) = 0 near π")
    void testSineRoot() throws MathException {
        DoubleUnaryOperator f = Math::sin;
        DoubleUnaryOperator df = Math::cos;

        double[] interval = {2.0, 4.0};
        double root = NewtonRaphson.findEquationRoot(interval, f, df);

        assertEquals(Math.PI, root, DELTA);
    }

    // ==================== Exception Tests ====================

    @Test
    @DisplayName("Should throw on invalid interval")
    void testInvalidInterval() {
        DoubleUnaryOperator f = x -> x * x - 4;
        DoubleUnaryOperator df = x -> 2 * x;

        assertThrows(IllegalArgumentException.class,
                () -> NewtonRaphson.findEquationRoot(null, f, df));
        assertThrows(IllegalArgumentException.class,
                () -> NewtonRaphson.findEquationRoot(new double[]{1.0}, f, df));
    }

    @Test
    @DisplayName("Should throw when derivative is near zero")
    void testDerivativeNearZero() {
        DoubleUnaryOperator f = x -> x * x - 4;
        DoubleUnaryOperator df = x -> 1e-15;

        double[] interval = {1.9, 2.1};

        MathException ex = assertThrows(MathException.class,
                () -> NewtonRaphson.findEquationRoot(interval, f, df));

        assertTrue(ex.getMessage().contains("Derivative near zero"));
    }

    @Test
    @DisplayName("Should throw on non-convergence (iteration limit reached)")
    void testNonConvergence() {
        // This function + starting interval causes slow/oscillatory behavior
        // due to the combination of the function shape and step-size limiting.
        DoubleUnaryOperator f = x -> x * x * x * x * x - x - 1;  // High odd power + linear term
        DoubleUnaryOperator df = x -> 5 * x * x * x * x - 1;

        double[] interval = {-0.5, 0.5};   // Midpoint = 0.0, where derivative is -1 (safe)

        MathException ex = assertThrows(MathException.class,
                () -> NewtonRaphson.findEquationRoot(interval, f, df, 1e-12, 100));

        assertTrue(ex.getMessage().contains("failed to converge"),
                "Should mention convergence failure");
        assertTrue(ex.getMessage().contains("iterations"),
                "Message should reference the iteration count");
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    @DisplayName("Should validate parameters")
    void testParameterValidation(double[] interval, DoubleUnaryOperator f,
                                 DoubleUnaryOperator df, double acc, int maxIt) {
        assertThrows(Exception.class,
                () -> NewtonRaphson.findEquationRoot(interval, f, df, acc, maxIt));
    }

    private static Stream<Arguments> invalidParameters() {
        DoubleUnaryOperator valid = x -> x;
        return Stream.of(
                Arguments.of(new double[]{0, 1}, null, valid, 1e-8, 50),
                Arguments.of(new double[]{0, 1}, valid, null, 1e-8, 50),
                Arguments.of(new double[]{0, 1}, valid, valid, -1e-8, 50),
                Arguments.of(new double[]{0, 1}, valid, valid, 1e-8, 0)
        );
    }
}
