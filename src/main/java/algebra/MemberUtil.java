/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import linear.matrix.MatrixCalc;
import series.SeriesPart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static algebra.EquationUtil.distinct;
import static algebra.EquationUtil.removeZeroMembers;
import static linear.matrix.Validation.isSquareMatrix;

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
        return m.size() == 1 && ((Member) m.getSeriesParts().getFirst()).getLetter().symbol().isEmpty();
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
     * A cartesian product operation.
     *
     * @param firstSum the first bracket with sum of members
     * @param secondSum the second bracket with sum of members
     * @return the result of opening the brackets
     */
    public static <T extends SeriesPart> List<T> openBrackets(List<T> firstSum, List<T> secondSum) {
        List<T> result = new ArrayList<>();
        for (T m : firstSum) {
            T accumulator = (T) m.copy();
            for (T s : secondSum) {
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
     * @param matrix
     * @return
     */
    private static Members[][] doubleArrayToMembersArrayForCharacteristicPolynomial(final double[][] matrix) {
        Members[][] membersArray = new Members[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) membersArray[i][j] = Members.of(List.of(Member.asRealConstant(matrix[i][j]), Member.asVariableX(-1.0d)));
                else membersArray[i][j] = Members.of(List.of(Member.asRealConstant(matrix[i][j])));
            }
        }
        return membersArray;
    }

    /**
     * The method calculates a determinant of the given matrix.
     * Only a square matrix may have a determinant.
     *
     * @param matrix - the given matrix
     * @return the determinant of the matrix
     */
    public static Equation toCharacteristicPolynomial(final double[][] matrix) {
        if (!isSquareMatrix(matrix))
            throw new IllegalArgumentException("A non-square matrix has no determinant.");
        if (matrix.length < 2)
            return removeZeroMembers(Equation.of(List.of(Member.asRealConstant(matrix[0][0]), Member.asVariableX(-1.0d)), Member.asRealConstant(.0d)));
        if (matrix.length == 2)
            return removeZeroMembers(Equation.of(distinct(Stream.concat(
                    Members.of(List.of(Member.asRealConstant(matrix[0][0]), Member.asVariableX(-1.0d)))
                            .multiply(Members.of(List.of(Member.asRealConstant(matrix[1][1]), Member.asVariableX(-1.0d)))).asList().stream(),
                    Stream.of(Member.asRealConstant(-1.0d).multiply(Member.asRealConstant(matrix[0][1]).multiply(
                            Member.asRealConstant(matrix[1][0]))))).toList()), Member.asRealConstant(.0d)));
        if (matrix.length == 3)
            return removeZeroMembers(Equation.of(distinct(Stream.of(
                            Members.of(List.of(Member.asRealConstant(matrix[0][0]), Member.asVariableX(-1.0d)))
                                    .multiply(Members.of(List.of(Member.asRealConstant(matrix[1][1]), Member.asVariableX(-1.0d))))
                                    .multiply(Members.of(List.of(Member.asRealConstant(matrix[2][2]), Member.asVariableX(-1.0d)))).asList(),
                            Members.of(List.of(Member.asRealConstant(matrix[0][1]).multiply(
                                    Member.asRealConstant(matrix[1][2])).multiply(
                                            Member.asRealConstant(matrix[2][0])))).asList(),
                            Members.of(List.of(Member.asRealConstant(matrix[1][0]).multiply(
                                    Member.asRealConstant(matrix[2][1])).multiply(
                                    Member.asRealConstant(matrix[0][2])))).asList(),
                            Members.of(List.of(Member.asRealConstant(-1.0d)
                                    .multiply(Member.asRealConstant(matrix[0][2]).multiply(
                                            Members.of(List.of(Member.asRealConstant(matrix[1][1]), Member.asVariableX(-1.0d))).multiply(
                                    Member.asRealConstant(matrix[2][0])))))).asList(),
                            Members.of(List.of(Member.asRealConstant(-1.0d)
                                    .multiply(Member.asRealConstant(matrix[1][0]).multiply(
                                            Member.asRealConstant(matrix[0][1])).multiply(
                                            Members.of(List.of(Member.asRealConstant(matrix[2][2]), Member.asVariableX(-1.0d))))))).asList(),
                            Members.of(List.of(Member.asRealConstant(-1.0d)
                                    .multiply(Members.of(List.of(Member.asRealConstant(matrix[0][0]), Member.asVariableX(-1.0d))).multiply(
                                            Member.asRealConstant(matrix[2][1])).multiply(
                                            Member.asRealConstant(matrix[1][2]))))).asList()
                            ).flatMap(List::stream).toList()), Member.asRealConstant(.0d)));
        // TODO  if (matrix.length == 4)
        throw new IllegalArgumentException("Matrices lager than 3x3 aren't supported.");
    }
}