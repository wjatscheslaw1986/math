/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package functional;

import algebra.Letter;
import algebra.Term;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Utilities to calculate math functions.
 */
public final class FunctionUtil {

    private FunctionUtil() {
    }

    /**
     * Calculates the value of a single-variable mathematical function for a given
     * value of the independent variable.
     * <p>
     * The function is represented as a list of {@code Term} objects. All terms must
     * use the same variable letter; otherwise, an {@link IllegalArgumentException}
     * is thrown.
     * </p>
     * <p>
     * Each term is evaluated using the following rule:
     * </p>
     * <ul>
     *     <li>If the term power is {@code 0.0}, only the coefficient is added.</li>
     *     <li>Otherwise, the term is evaluated as:
     *     {@code coefficient * x^power}.</li>
     * </ul>
     *
     * @param terms                    the list of terms representing the function expression;
     *                                 may be empty
     * @param independentVariableValue the value of the independent variable
     *                                 at which the function should be evaluated
     * @return the calculated function value at the specified independent variable value;
     * returns {@code 0.0} if the term list is empty
     * @throws IllegalArgumentException if the terms contain different variable letters
     */
    public static double calculateSingleVariableFunctionValueAtGivenX(List<Term> terms, List<DoubleUnaryOperator> transformers, double independentVariableValue) {
        if (terms.isEmpty())
            return .0d;
        if (terms.size() != transformers.size())
            throw new IllegalArgumentException("Number of terms and transformers don't match");
        Letter firstTermLetter = null;
        for (Term term : terms) {
            if (firstTermLetter == null) {
                firstTermLetter = term.getLetter();
                continue;
            }
            Letter secondTermLetter = term.getLetter();
            if (!firstTermLetter.equals(secondTermLetter))
                throw new IllegalArgumentException("Variable letters must be the same");
        }
        double sum = 0.0;
        for (int i = 0; i < transformers.size(); i++) {
            sum += (terms.get(i).getPower() == .0d
                    ? terms.get(i).getCoefficient()
                    : (terms.get(i).getCoefficient() * Math.pow(transformers.get(i).applyAsDouble(independentVariableValue), terms.get(i).getPower())));
        }
        return sum;
    }
}
