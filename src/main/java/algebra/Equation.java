/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An equation.
 *
 * @param members the left side of the equation
 * @param equalsTo the right side of the equation
 *
 * @author Viacheslav Mikhailov
 */
public record Equation(List<Member> members, AtomicReference<Double> equalsTo) {

    /**
     *
     * @param letter
     * @return
     */
    public Member getMemberByLetter(String letter) {
        Objects.requireNonNull(letter);
        Iterator<Member> iterator = members.iterator();
        while (iterator.hasNext()) {
            Member member = iterator.next();
            if (member.getLetter().equals(letter)) {
                return member;
            }
        }
        return null;
    }

    /**
     *
     * @param index
     * @return
     */
    public Member getMemberByIndex(int index) {
        return members.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation = (Equation) o;
        return (Arrays.equals(this.members.toArray(),equation.members.toArray())
                && Objects.equals(equalsTo.get(), equation.equalsTo.get()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, equalsTo.get());
    }
}
