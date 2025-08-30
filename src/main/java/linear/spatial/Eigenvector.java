/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package linear.spatial;

/**
 * This class is a wrapper that aggregates both the given eigenvector and its
 * corresponding eigenvalue.
 *
 * @param eigenvalue the corresponding eigenvalue
 * @param eigenvector the given eigenvector
 */
public record Eigenvector(double eigenvalue, Vector eigenvector) {
    public static Eigenvector of(final double eigenvalue, final Vector eigenvector) {
        return new Eigenvector(eigenvalue, eigenvector);
    }
}
