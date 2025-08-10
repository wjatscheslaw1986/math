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
 * @param terms the left side of the equation
 * @param equalsTo the right side of the equation
 *
 * @author Viacheslav Mikhailov
 */
public record Equation(List<Term> terms, Term equalsTo) {

    /**
     * Factory method.
     *
     * @param terms the left side of the equation
     * @param equalsTo the right side of the equation
     * @return the equation
     */
    public static Equation of(List<Term> terms, Term equalsTo) {
        return new Equation(terms, equalsTo);
    }

    /**
     * Return the {@link Term} of the equation by its {@link Letter}.
     * Returns the first found searching from left to right.
     *
     * @param letter a letter for the term
     * @return term of the equation
     */
    public Term getTermByLetter(Letter letter) {
        Objects.requireNonNull(letter);
        Iterator<Term> iterator = terms.iterator();
        while (iterator.hasNext()) {
            Term term = iterator.next();
            if (term.getLetter().equals(letter)) return term;
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
     * Return term by its index in the equation, starting from 0,searching from left to right
     *
     * @param index index
     * @return term of the equation
     */
    public Term getTermByIndex(int index) {
        return terms.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation = (Equation) o;
        return (Arrays.equals(this.terms.toArray(),equation.terms.toArray())
                && Objects.equals(equalsTo, equation.equalsTo));
    }

    @Override
    public int hashCode() {
        return Objects.hash(terms, equalsTo);
    }
}
