/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package approximation;

/**
 * Rounding functions.
 *
 * @author Viacheslav Mikhailov
 */
public final class RoundingUtil {

    /**
     * Rounding to N decimal places after the floating point.
     *
     * @param value the given value
     * @param decimals number of decimal places after the dot.
     * @return the rounded number
     */
    public static double roundToNDecimals(final double value, final int decimals) {
        final double scale = Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }
}
