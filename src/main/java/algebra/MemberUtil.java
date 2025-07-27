/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import java.util.ArrayList;
import java.util.List;

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
    private static boolean isConstant(Member m) {
        return m.getLetter().symbol().isEmpty();
    }

    /**
     * Adds two Members.
     *
     * @param m1 the first Member
     * @param m2 the second Member
     * @return a new Member representing the sum
     * @throws IllegalArgumentException if the Members are not like terms and not both constants
     */
    public static Member add(Member m1, Member m2) {
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
            throw new IllegalArgumentException("Cannot add members with different letters or powers");
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
     * The method calculates a determinant of the given matrix.
     * Only a square matrix may have a determinant.
     *
     * @param matrix - the given matrix
     * @return the determinant of the matrix
     */
    public static List<MultipliedMembers> toMultipliedMembers(final double[][] matrix) {
        if (!isSquareMatrix(matrix))
            throw new IllegalArgumentException("A non-square matrix has no determinant.");
        if (matrix.length < 2)
            return List.of(MultipliedMembers.of(List.of(Member.asVariableX(matrix[0][0]))));
        if (matrix.length == 2)
            return List.of(
                    MultipliedMembers.of(
                            List.of(Member.asVariableX(matrix[0][0]), Member.asVariableX(matrix[1][1]))),
                    MultipliedMembers.of(
                            List.of(Member.asRealConstant(-1.0d), Member.asVariableX(matrix[0][1]), Member.asVariableX(matrix[1][0]))));
        if (matrix.length == 3)
            return List.of(
                    MultipliedMembers.of(List.of(
                            Member.asVariableX(matrix[0][0]),
                            Member.asVariableX(matrix[1][1]),
                            Member.asVariableX(matrix[2][2]))),
                    MultipliedMembers.of(List.of(
                            Member.asVariableX(matrix[0][1]),
                            Member.asVariableX(matrix[1][2]),
                            Member.asVariableX(matrix[0][2]))),
                    MultipliedMembers.of(List.of(
                            Member.asVariableX(matrix[1][0]),
                            Member.asVariableX(matrix[2][1]),
                            Member.asVariableX(matrix[2][0]))),
                    MultipliedMembers.of(List.of(
                            Member.asRealConstant(-1.0d),
                            Member.asVariableX(matrix[0][2]),
                            Member.asVariableX(matrix[1][1]),
                            Member.asVariableX(matrix[2][0]))),
                    MultipliedMembers.of(List.of(
                            Member.asRealConstant(-1.0d),
                            Member.asVariableX(matrix[1][0]),
                            Member.asVariableX(matrix[0][1]),
                            Member.asVariableX(matrix[2][2]))),
                    MultipliedMembers.of(List.of(
                            Member.asRealConstant(-1.0d),
                            Member.asVariableX(matrix[2][1]),
                            Member.asVariableX(matrix[1][2]),
                            Member.asVariableX(matrix[0][0]))));
        var result = new ArrayList<MultipliedMembers>();
//        for (int col = 0; col < matrix[0].length; col++)
//            if (matrix[0][col] != 0)
//                result.addAll(matrix[0][col] * toMultipliedMembers(cofactor(matrix, 1, col + 1)));
        return result;
    }
}