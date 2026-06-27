/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package linear.matrix.exception;

public class MatrixException extends Exception {

    final String message;

    public MatrixException(String message) {
        this.message = message;
    }
}
