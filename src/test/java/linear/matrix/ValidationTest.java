package linear.matrix;

import org.junit.jupiter.api.Test;

import static linear.matrix.Validation.canMultiply;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@linkplain linear.matrix.Validation} class.
 *
 * @author Wjatscheslaw Michailov
 */
public class ValidationTest {

    @Test
    void canMultiplyTest() {
        double[][] matrix1 = {{3, -4},{2, 5}};
        double[][] matrix2 = {{-1, 5},{-2, -3}};
        assertTrue(canMultiply(matrix1, matrix2));
        double[][] matrix3 = {{-1, 5}};
        assertFalse(canMultiply(matrix1, matrix3));
    }
}
