/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.Objects;

/**
 * A letter of a {@link Member} of an {@link Equation}.
 *
 * @param symbol the letter to signify the member of an equation
 * @param index the index sign usually beneath the letter
 * @author Viacheslav Mikhailov
 */
public record Letter(String symbol, int index) implements Comparable<Letter> {

    /**
     * A static factory method.
     *
     * @param symbol the letter
     * @param index the index sign
     * @return the letter which is the signification for the equation member
     */
    public static Letter of(final String symbol, final int index) {
        return new Letter(symbol, index);
    }

    @Override
    public int compareTo(Letter o) {
        int result = symbol.compareTo(o.symbol);
        if (result == 0) result = Integer.compare(index, o.index);
        return result;
    }

    @Override
    public String toString() {
        return symbol + index;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Letter letter = (Letter) o;
        return index == letter.index && Objects.equals(symbol, letter.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, index);
    }

    /**
     * Copying.
     *
     * @return the copy of this instance
     */
    public Letter copy() {
        return new Letter(this.symbol, this.index);
    }
}
