/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.matrix.exception;

import java.io.Serial;

/**
 * @author Wjatscheslaw Michailov
 */
public class MatrixException extends Exception {

	@Serial
    private static final long serialVersionUID = 12323L;

	public MatrixException(String s) {
		super(s);
	}
}