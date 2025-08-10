/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import series.SeriesPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class providing arithmetic operations for {@link Term} objects.
 *
 * @author Viacheslav Mikhailov
 */
public final class TermUtil {

    private TermUtil() {
        // static context only
    }

    /**
     * Checks if a Term is a constant (has an empty letter name).
     *
     * @param m the Term to check
     * @return true if the Term is a constant, false otherwise
     */
    static boolean isConstant(SeriesPart m) {
        return m.getSeriesParts().stream().allMatch(element ->
                ((Term) element).getPower() == .0d);
    }

    /**
     * Sums two Terms.
     *
     * @param m1 the first Term
     * @param m2 the second Term
     * @return a new Term representing the sum
     * @throws IllegalArgumentException if the Terms are not like terms and not both constants
     */
    public static Term sum(Term m1, Term m2) {
        if (isConstant(m1) && isConstant(m2)) {
            return Term.asRealConstant(m1.getCoefficient() + m2.getCoefficient());
        } else if (!isConstant(m1) && !isConstant(m2)
                && m1.getLetter().equals(m2.getLetter())
                && m1.getPower() == m2.getPower()) {
            return Term.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower())
                    .coefficient(m1.getCoefficient() + m2.getCoefficient())
                    .build();
        } else {
            throw new IllegalArgumentException("Cannot sum terms with different letters or powers");
        }
    }

    /**
     * Subtracts the second Term from the first.
     *
     * @param m1 the first Term (minuend)
     * @param m2 the second Term (subtrahend)
     * @return a new Term representing the difference
     * @throws IllegalArgumentException if the Terms are not like terms and not both constants
     */
    public static Term subtract(Term m1, Term m2) {
        return sum(m1, negate(m2));
    }

    /**
     * Multiply the given term by -1
     *
     * @param m the given term
     * @return the negated result
     */
    public static Term negate(Term m) {
        var copy = m.copy();
        copy.setCoefficient(-copy.getCoefficient());
        return copy;
    }

    /**
     * Multiplies two Terms.
     *
     * @param m1 the first Term
     * @param m2 the second Term
     * @return a new Term representing the product
     * @throws IllegalArgumentException if both are variable terms with different letters
     */
    public static Term multiply(Term m1, Term m2) {
        if (isConstant(m1) && isConstant(m2))
            return Term.asRealConstant(m1.getCoefficient() * m2.getCoefficient());
        else if (isConstant(m1) && !isConstant(m2))
            return Term.builder()
                    .letter(m2.getLetter())
                    .power(m2.getPower())
                    .coefficient(m1.getCoefficient() * m2.getCoefficient())
                    .build();
        else if (!isConstant(m1) && isConstant(m2))
            return Term.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower())
                    .coefficient(m1.getCoefficient() * m2.getCoefficient())
                    .build();
        else if (!isConstant(m1) && !isConstant(m2) && m1.getLetter().equals(m2.getLetter()))
            return Term.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower() + m2.getPower())
                    .coefficient(m1.getCoefficient() * m2.getCoefficient())
                    .build();
        else throw new IllegalArgumentException("Cannot multiply terms with different letters");
    }

    /**
     * Divides the first Term by the second.
     *
     * @param m1 the first Term (dividend)
     * @param m2 the second Term (divisor)
     * @return a new Term representing the quotient
     * @throws IllegalArgumentException if both are variable terms with different letters
     * @throws ArithmeticException if division by zero occurs
     * @throws UnsupportedOperationException if dividing a constant by a variable term
     */
    public static Term divide(Term m1, Term m2) {
        return multiply(m1, reciprocal(m2));
    }

    private static Term reciprocal(Term m) {
        var copy = m.copy();
        copy.setCoefficient(1 / copy.getCoefficient());
        copy.setPower(-m.getPower());
        if (!Objects.isNull(copy.getValue())) copy.setValue(1 / copy.getValue());
        return copy;
    }

    /**
     * Calculates a sum of multiplications, known as opening of the brackets.
     * A Cartesian product operation.
     *
     * @param <T> the term type
     * @param firstSeries the first bracket with sum of terms
     * @param secondSeries the second bracket with sum of terms
     * @return the result of opening the brackets
     */
    public static <T extends SeriesPart> List<T> openBrackets(List<T> firstSeries, List<T> secondSeries) {
        List<T> result = new ArrayList<>();
        for (T m : firstSeries) {
            T accumulator = (T) m.copy();
            for (T s : secondSeries) {
                accumulator = (T) accumulator.multiply(s);
            }
            result.add(accumulator);
        }
        return result;
    }



    /**
     *
     *
     * @param matrix
     * @return
     */
    static Terms[][] doubleArrayToTermsArrayForCharacteristicPolynomial(final double[][] matrix) {
        Terms[][] termsArray = new Terms[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) termsArray[i][j] = Terms.of(List.of(Term.asRealConstant(matrix[i][j]), Term.asVariableX(-1.0d)));
                else termsArray[i][j] = Terms.of(List.of(Term.asRealConstant(matrix[i][j])));
            }
        }
        return termsArray;
    }


}