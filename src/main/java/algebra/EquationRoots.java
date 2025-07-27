/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.List;

/**
 * A wrapper class for returning equation roots found.
 *
 * @author Viacheslav Mikhailov
 */
public record EquationRoots<T>(List<T> roots, double discriminant) {

    public static <Q> EquationRoots<Q> of(List<Q> roots, double discriminant) {
        return new EquationRoots<>(roots, discriminant);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        EquationRoots<?> that = (EquationRoots<?>) o;
        return Double.compare(discriminant, that.discriminant) == 0 && roots.equals(that.roots);
    }

    @Override
    public int hashCode() {
        int result = roots.hashCode();
        result = 31 * result + Double.hashCode(discriminant);
        return result;
    }
}
