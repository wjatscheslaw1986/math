/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package geometry;

import org.junit.jupiter.api.Test;

import static geometry.GeometryCalc.areCirclesIntersect;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeometryCalcTest {

    @Test
    public void testDistanceSq() {
        assertTrue(areCirclesIntersect(4, 3, 7, 5, 2, 3));
        assertFalse(areCirclesIntersect(4, 3, 9, 6, 2, 3));
        assertFalse(areCirclesIntersect(4, 3, 4, 4, 2, 3.2));
    }
}
