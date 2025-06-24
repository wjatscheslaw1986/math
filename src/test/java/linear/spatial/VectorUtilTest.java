package linear.spatial;

import linear.matrix.exception.MatrixException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link VectorUtil} class.
 *
 * @author Viacheslav Mikhailov
 */
public class VectorUtilTest {

    /**
     * Are the given elements x1 = (1,1,1), x2 = (1,2,3) and x3 = (1,4,5) of some 3D space linearly dependent?
     *
     * By definition, linear independence means a1x1 + a2x2 + a3x3 = 0 if at least one of a's is non-zero.
     *
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая Геометрия" - 2006
     *
     * @throws MatrixException if failed to solve linear equations system
     */
    @Test
    void givenBasisVectors_whechCheckIsIndependent_thenTrue() throws MatrixException {
        Vector[] vectors = new Vector[]{
                Vector.of(1,1,1),
                Vector.of(1,2,3),
                Vector.of(1,4,5)
        };
        assertTrue(VectorUtil.isBasis(vectors));
    }

    /**
     * Are the given elements x1 = (1,2,3), x2 = (3,5,1) and x3 = (5,9,7) of some 3D space linearly dependent?
     *
     * By definition, linear independence means a1x1 + a2x2 + a3x3 = 0 if at least one of a's is non-zero.
     *
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая Геометрия" - 2006
     *
     * @throws MatrixException if failed to solve linear equations system
     */
    @Test
    void givenNonBasisVectors_whechCheckIsIndependent_thenFalse() throws MatrixException {
        Vector[] vectors = new Vector[]{
                Vector.of(1,2,3),
                Vector.of(3,5,1),
                Vector.of(5,9,7)
        };
        assertFalse(VectorUtil.isBasis(vectors));
    }
}
