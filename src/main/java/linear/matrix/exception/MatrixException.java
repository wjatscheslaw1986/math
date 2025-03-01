/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com)  © 2025.
 */
package linear.matrix.exception;

import java.io.Serial;

/**
 * @author Viacheslav Mikhailov
 */
public class MatrixException extends Exception {

	@Serial
    private static final long serialVersionUID = 12323L;

	public MatrixException(String s) {
		super(s);
	}
}