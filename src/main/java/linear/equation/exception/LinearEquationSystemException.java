/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com)  © 2025.
 */
package linear.equation.exception;

import java.io.Serial;

/**
 * @author Viacheslav Mikhailov
 */
public class LinearEquationSystemException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    public LinearEquationSystemException(String textual) {
        super(textual);
    }
}
