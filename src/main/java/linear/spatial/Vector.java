/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.spatial;

import java.util.Arrays;
import java.util.Objects;

/**
 * Linear space vector.
 *
 * @param coordinates as a variadic array
 */
public record Vector(double... coordinates) {

    /**
     * Factory method.
     *
     * @param coordinates vector coordinates
     * @return the Vector object
     */
    public static Vector of(double... coordinates) {
        return new Vector(coordinates);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.deepEquals(coordinates, vector.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }
}
