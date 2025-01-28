/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.equation.exception;

import java.io.Serial;

/**
 * @author Wjatscheslaw Michailov
 */
public class LinearEquationSystemException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    public LinearEquationSystemException(String textual) {
        super(textual);
    }
}
