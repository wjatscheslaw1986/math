/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package functional;

import algebra.Letter;
import algebra.Term;

import java.util.List;

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
    public static double calculateSingleVariableFunctionValueAtGivenX(List<Term> terms, double independentVariableValue) {
        if (terms.isEmpty())
            return .0d;
        Letter firstTermLetter = terms.getFirst().getLetter();
        for (int i = 1; i < terms.size(); i++) {
            Letter secondTermLetter = terms.get(i).getLetter();
            if (!firstTermLetter.equals(secondTermLetter))
                throw new IllegalArgumentException("Variable letters must be the same");
        }
        double sum = 0.0;
        for (Term term : terms) {
            sum = sum + (term.getPower() == .0d ? term.getCoefficient() : (term.getCoefficient() * Math.pow(independentVariableValue, term.getPower())));
        }
        return sum;
    }
}
