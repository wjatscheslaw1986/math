/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author Viacheslav Mikhailov
 */
public class FunctionalTerm {
    private final Letter letter;
    private final DoubleUnaryOperator transformer;

    public FunctionalTerm(Letter letter, DoubleUnaryOperator transformer) {
        this.letter = letter;
        this.transformer = transformer;
    }

    public Letter getLetter() {
        return letter;
    }

    public double calculate(double independentVariableValue) {
        return this.transformer.applyAsDouble(independentVariableValue);
    }
}
