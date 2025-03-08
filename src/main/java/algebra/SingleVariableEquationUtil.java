/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com)  © 2025.
 */
package algebra;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for matrices.
 *
 * @author Viacheslav Mikhailov
 */
public class SingleVariableEquationUtil {

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
    public static List<Member> distinctByPower(final List<Member> input) {
        return input.stream()
                .collect(Collectors.groupingBy(
                        Member::getPower,
                        Collectors.toList())).values().stream()
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
    }

    /**
     * Returns textual representation of the given equation member.
     *
     * @param member    the equation member
     * @param character (optional) a character you want to represent the variable with
     * @return the textual representation of the given equation member.
     */
    public static String textify(final Member member, final Character... character) {
        if (member.getPower() == .0d) {
            return String.valueOf(member.getCoefficient());
        }
        var sb = new StringBuilder()
                .append(member.getCoefficient())
                .append(character.length > 0 ? character[0] : 'x');
        if (member.getPower() != 1.0d) {
            sb.append("^").append(member.getPower());
        }
        return sb.toString();
    }
}
