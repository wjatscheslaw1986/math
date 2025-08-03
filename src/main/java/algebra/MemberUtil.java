/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import series.SeriesPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class providing arithmetic operations for Member objects.
 *
 * @author Viacheslav Mikhailov
 */
public final class MemberUtil {

    private MemberUtil() {
        // static context only
    }

    /**
     * Checks if a Member is a constant (has an empty letter name).
     *
     * @param m the Member to check
     * @return true if the Member is a constant, false otherwise
     */
    static boolean isConstant(SeriesPart m) {
        return m.getSeriesParts().stream().allMatch(element ->
                ((Member) element).getPower() == .0d);
    }

    /**
     * Sums two Members.
     *
     * @param m1 the first Member
     * @param m2 the second Member
     * @return a new Member representing the sum
     * @throws IllegalArgumentException if the Members are not like terms and not both constants
     */
    public static Member sum(Member m1, Member m2) {
        if (isConstant(m1) && isConstant(m2)) {
            return Member.asRealConstant(m1.getCoefficient() + m2.getCoefficient());
        } else if (!isConstant(m1) && !isConstant(m2)
                && m1.getLetter().equals(m2.getLetter())
                && m1.getPower() == m2.getPower()) {
            return Member.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower())
                    .coefficient(m1.getCoefficient() + m2.getCoefficient())
                    .build();
        } else {
            throw new IllegalArgumentException("Cannot sum members with different letters or powers");
        }
    }

    /**
     * Subtracts the second Member from the first.
     *
     * @param m1 the first Member (minuend)
     * @param m2 the second Member (subtrahend)
     * @return a new Member representing the difference
     * @throws IllegalArgumentException if the Members are not like terms and not both constants
     */
    public static Member subtract(Member m1, Member m2) {
        if (isConstant(m1) && isConstant(m2)) {
            return Member.asRealConstant(m1.getCoefficient() - m2.getCoefficient());
        } else if (!isConstant(m1) && !isConstant(m2)
                && m1.getLetter().equals(m2.getLetter())
                && m1.getPower() == m2.getPower()) {
            return Member.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower())
                    .coefficient(m1.getCoefficient() - m2.getCoefficient())
                    .build();
        } else {
            throw new IllegalArgumentException("Cannot subtract members with different letters or powers");
        }
    }

    /**
     * Multiplies two Members.
     *
     * @param m1 the first Member
     * @param m2 the second Member
     * @return a new Member representing the product
     * @throws IllegalArgumentException if both are variable members with different letters
     */
    public static Member multiply(Member m1, Member m2) {
        if (isConstant(m1) && isConstant(m2))
            return Member.asRealConstant(m1.getCoefficient() * m2.getCoefficient());
        else if (isConstant(m1) && !isConstant(m2))
            return Member.builder()
                    .letter(m2.getLetter())
                    .power(m2.getPower())
                    .coefficient(m1.getCoefficient() * m2.getCoefficient())
                    .build();
        else if (!isConstant(m1) && isConstant(m2))
            return Member.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower())
                    .coefficient(m1.getCoefficient() * m2.getCoefficient())
                    .build();
        else if (!isConstant(m1) && !isConstant(m2) && m1.getLetter().equals(m2.getLetter()))
            return Member.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower() + m2.getPower())
                    .coefficient(m1.getCoefficient() * m2.getCoefficient())
                    .build();
        else throw new IllegalArgumentException("Cannot multiply members with different letters");
    }

    /**
     * Calculates a sum of multiplications, known as opening of the brackets.
     * A Cartesian product operation.
     *
     * @param <T> the member type
     * @param firstSeries the first bracket with sum of members
     * @param secondSeries the second bracket with sum of members
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
     * Divides the first Member by the second.
     *
     * @param m1 the first Member (dividend)
     * @param m2 the second Member (divisor)
     * @return a new Member representing the quotient
     * @throws IllegalArgumentException if both are variable members with different letters
     * @throws ArithmeticException if division by zero occurs
     * @throws UnsupportedOperationException if dividing a constant by a variable member
     */
    public static Member divide(Member m1, Member m2) {
        if (isConstant(m1) && isConstant(m2)) {
            if (m2.getCoefficient() == 0) throw new ArithmeticException("Division by zero");
            return Member.asRealConstant(m1.getCoefficient() / m2.getCoefficient());
        } else if (!isConstant(m1) && isConstant(m2)) {
            if (m2.getCoefficient() == 0) throw new ArithmeticException("Division by zero");
            return Member.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower())
                    .coefficient(m1.getCoefficient() / m2.getCoefficient())
                    .build();
        } else if (isConstant(m1) && !isConstant(m2)) throw new UnsupportedOperationException("Division of constant by variable not supported");
        else if (!isConstant(m1) && !isConstant(m2) && m1.getLetter().equals(m2.getLetter())) {
            if (m2.getCoefficient() == 0) throw new ArithmeticException("Division by zero");
            return Member.builder()
                    .letter(m1.getLetter())
                    .power(m1.getPower() - m2.getPower())
                    .coefficient(m1.getCoefficient() / m2.getCoefficient())
                    .build();
        } else throw new IllegalArgumentException("Cannot divide members with different letters");
    }

    /**
     *
     *
     * @param matrix
     * @return
     */
    static Members[][] doubleArrayToMembersArrayForCharacteristicPolynomial(final double[][] matrix) {
        Members[][] membersArray = new Members[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) membersArray[i][j] = Members.of(List.of(Member.asRealConstant(matrix[i][j]), Member.asVariableX(-1.0d)));
                else membersArray[i][j] = Members.of(List.of(Member.asRealConstant(matrix[i][j])));
            }
        }
        return membersArray;
    }


}