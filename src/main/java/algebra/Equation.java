/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * An equation.
 *
 * @param members the left side of the equation
 * @param equalsTo the right side of the equation
 *
 * @author Viacheslav Mikhailov
 */
public record Equation(List<Member> members, Member equalsTo) {

    /**
     * Factory method.
     *
     * @param members the left side of the equation
     * @param equalsTo the right side of the equation
     * @return the equation
     */
    public static Equation of(List<Member> members, Member equalsTo) {
        return new Equation(members, equalsTo);
    }

    /**
     * Return the {@link Member} of the equation by its {@link Letter}.
     * Returns the first found searching from left to right.
     *
     * @param letter a letter for the member
     * @return member of the equation
     */
    public Member getMemberByLetter(Letter letter) {
        Objects.requireNonNull(letter);
        Iterator<Member> iterator = members.iterator();
        while (iterator.hasNext()) {
            Member member = iterator.next();
            if (member.getLetter().equals(letter)) return member;
        }
        return null;
    }

    /**
     * Update the real constant coefficient at the right of the equation.
     *
     * @param coefficient the new value for the right part of the equation
     */
    public void setEqualsTo(final double coefficient) {
        this.equalsTo.setCoefficient(coefficient);
    }

    /**
     * Return member by its index in the equation, starting from 0,searching from left to right
     *
     * @param index index
     * @return member of the equation
     */
    public Member getMemberByIndex(int index) {
        return members.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation = (Equation) o;
        return (Arrays.equals(this.members.toArray(),equation.members.toArray())
                && Objects.equals(equalsTo, equation.equalsTo));
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, equalsTo);
    }
}
