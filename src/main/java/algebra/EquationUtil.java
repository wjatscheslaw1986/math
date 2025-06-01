/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * A utility class for equations.
 *
 * @author Viacheslav Mikhailov
 */
public class EquationUtil {

    /**
     * Multiply two equation members of same variable.
     *
     * @param x1 first member
     * @param x2 second member
     * @return the sum
     */
    public static Member multiply(Member x1, Member x2) {
        if (x2.getCoefficient() == 0 || x1.getCoefficient() == 0)
            return Member.builder().coefficient(.0d).power(.0d).build();
        return Member.builder()
                .coefficient(x1.getCoefficient() * x2.getCoefficient())
                .power(x1.getPower() + x2.getPower())
                .build();
    }

    /**
     * Sum two equation members of same variable.
     *
     * @param x1 first member
     * @param x2 second member
     * @return the sum
     */
    public static Member sum(Member x1, Member x2) {
        if (x1.getPower() != x2.getPower())
            throw new IllegalArgumentException("An attempt to sum two variable members of different powers.");
        return Member.builder()
                .coefficient(x1.getCoefficient() + x2.getCoefficient())
                .power(x1.getPower())
                .build();
    }

    /**
     * Modifies the input list of members of a single variable equation,
     * by grouping its members by power of their variable using summation,
     * then sorts the result, then returns.
     *
     * @param input a list of members of a single variable equation with only 0 in its right part
     * @return a list of members of a single variable equation, gathered distinct by power, ordered
     */
    public static List<Member> distinct(final List<Member> input) {
        List<Member> members = input.stream()
                .collect(Collectors.groupingBy(
                        (Member m) -> {
                            if (m.getPower() != .0d)
                                return m.getLetter() + m.getPower();
                            else return m.getPower();
                            }, Collectors.toList())).values().stream()
                .map(listOfMembers -> {
                    var iterator = listOfMembers.iterator();
                    var result = iterator.next();
                    while (iterator.hasNext()) {
                        var next = iterator.next();
                        result = Member.builder()
                                .power(result.getPower())
                                .coefficient(result.getCoefficient() + next.getCoefficient())
                                .build();
                    }
                    return result;
                }).sorted(Comparator.reverseOrder()).toList();
        return members;
    }

    /**
     * Converts the two given arguments to an {@link Equation}.
     *
     * @param coefficients the both sides of the given linear single variable equation, as an array.
     * @param variableIndex the index of the {@code coefficients} array with the variable whose value we want to find
     * @return the Equation object
     */
    public static Equation toSingleVariableEquation(final double[] coefficients, final int variableIndex) {
        Deque<Member> members = new ArrayDeque<>();
        for (int i = 0; i < coefficients.length - 1; i++) {
            var builder = Member.builder()
                    .coefficient(coefficients[i])
                    .power(1.0d);
            if (i != variableIndex) {
                builder.value(1.0d);
            } else {
                builder.value(null);
            }
            members.add(builder.build());
        }
        return new Equation(members, new AtomicReference<>(coefficients[coefficients.length - 1]));
    }

    /**
     * Solves the single variable linear equation.
     * The right side of the equation is represented by the last element of the {@code coefficients} array.
     *
     * @param coefficients the both sides of the linear single variable equation, as an array.
     * @param variableIndex the index of the {@code coefficients} array with the variable
     * @return the value of the variable found
     */
    public static double solveSingleVariableLinearEquation(final double[] coefficients, final int variableIndex) {
        if (coefficients.length < 2)
            throw new IllegalArgumentException("Not an equation.");
        if (coefficients.length - 1 == variableIndex)
            throw new IllegalArgumentException("The variable shouldn't be found on the right side of the equation.");
        if (coefficients[variableIndex] == .0d)
            throw new IllegalArgumentException("The single variable of the equation is multiplied by the coefficient equal to 0.");
        double sum = .0d;
        for (int i = 0; i < coefficients.length - 1; i++) {
            if (i == variableIndex) continue;
            sum = sum + coefficients[i];
        }
        sum = coefficients[coefficients.length - 1] - sum;
        return sum / coefficients[variableIndex];
    }

    /**
     * Solves the single variable linear equation.
     *
     * @param equation the equation given
     * @return the value of the variable found
     */
    public static void solveSingleVariableLinearEquation(final Equation equation) {
        var hits = 0;
        Member variable = null;
        for (Member eqMember : equation.members()) if (Objects.isNull(eqMember.getValue())) {
            variable = eqMember;
            hits++;
        }
        if (hits != 1) throw new IllegalArgumentException("Not a single variable equation.");
        double sum = .0d;

        for (Member eqMember : equation.members()) {
            if (Objects.isNull(eqMember.getValue()))
                continue;
            sum = sum + eqMember.getCoefficient() * eqMember.getValue();
        }
        sum = equation.equalsTo().get() - sum;
        variable.setValue(sum / variable.getCoefficient());
    }

    /**
     * This method traverses the given array and changes every -0.0d to 0.0d.
     * <p>
     *     This method modifies the argument.
     * </p>
     *
     * @param array the given array
     */
    public static void cleanDoubleArrayOfNegativeZeros(final Double[] array) {
        if (Objects.isNull(array)) return;
        for (int i = 0; i < array.length; i++)
            if (!Objects.isNull(array[i]) && array[i] == -0.0d) array[i] = 0.0d;
    }
}
