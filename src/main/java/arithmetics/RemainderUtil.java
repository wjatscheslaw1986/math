/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package arithmetics;

/**
 * Calculate division remainder correctly.
 *
 * @author
 */
public final class RemainderUtil {

    private RemainderUtil() {
        // static context only
    }

    /**
     * Calculate remainder in accordance with arithmetic definition.
     * <p>
     * Definition: a = b * r + q <br/>
     * a - dividend <br/>
     * b - divisor <br/>
     * r - partial quotient (integer) <br/>
     * q - remainder [ 0 <= q < |b| ] <br/>
     * <p>
     * By definition, the remainder is always a positive number.
     * </p><p>
     * Examples: <br/>
     * 5/3 ⟹ 5 = 3 * 1 + 2 ⟹ r = 1 ∩ q = 2 <br/>
     * 5/(-3) ⟹ 5 = (-3) * (-1) + 2 ⟹ r = -1 ∩ q = 2 <br/>
     * -5/3 ⟹ -5 = 3 * (-2) + 1 ⟹ r = -2 ∩ q = 1 <br/>
     * -5/(-3) ⟹ -5 = (-3) * 2 + 1 ⟹ r = 2 ∩ q = 1 <br/>
     *
     * @param dividend the dividend given
     * @param divisor  the divisor given
     * @return the remainder
     */
    public static int remainder(int dividend, int divisor) {
        if ((dividend > 0 && divisor > 0) || (dividend > 0 && divisor < 0))
            return dividend % divisor;
        else if (dividend < 0 && divisor < 0)
            return dividend - (dividend / divisor + 1) * divisor;
        else return dividend - (dividend / divisor - 1) * divisor;
    }
}
