package linear.matrix;

import org.junit.jupiter.api.Test;

import static linear.matrix.RowEchelonFormUtil.isRowEchelonForm;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@linkplain linear.matrix.RowEchelonFormUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class RowEchelonFormUtilTest {

    @Test
    void given_row_echelon_form_matrix_check_if_row_echelon_form() {
        var ref = new double[][]{
                {1.0096634218430616d, -8.772286757703684d, -0.44083720521676284d, 0.8588440971684381d},
                {.0d, -6.420896947702297d, 3.5253331532074323d, 4.864061213084703d},
                {.0d, .0d, -7.092716530960487d, -4.478958333291305d},
                {.0d, .0d, .0d, 2.962818038754156d}};

        assertTrue(isRowEchelonForm(ref));
    }
}
