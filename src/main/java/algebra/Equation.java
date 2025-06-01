/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
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
public record Equation(Deque<Member> members, AtomicReference<Double> equalsTo) {

    /**
     *
     * @param index
     * @return
     */
    public Member getMemberByIndex(int index) {
        if (index < 0 || index >= members().size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Iterator<Member> iterator = members.iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
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
