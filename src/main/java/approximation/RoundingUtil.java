/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package approximation;

import java.util.Objects;

/**
 * Rounding functions.
 *
 * @author Viacheslav Mikhailov
 */
public final class RoundingUtil {

    private RoundingUtil() {
        // static context only
    }

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

    /**
     * This method traverses the given array and changes every -0.0d to 0.0d in the given array in place.
     *
     * @param array the given array
     */
    public static void cleanDoubleArrayOfNegativeZeros(final Double[] array) {
        if (Objects.isNull(array)) return;
        for (int i = 0; i < array.length; i++)
            if (!Objects.isNull(array[i]) && array[i] == -0.0d) array[i] = 0.0d;
    }

    /**
     * This method traverses the given array and changes every -0.0d to 0.0d in the given array in place.
     *
     * @param array the given array
     */
    public static void cleanDoubleArrayOfNegativeZeros(final double[] array) {
        if (Objects.isNull(array)) return;
        for (int i = 0; i < array.length; i++)
            if (array[i] == -0.0d) array[i] = 0.0d;
    }

    /**
     * Is the value passed as an argument is approximately zero?
     *
     * @param value the given value
     * @return true if the given value is approximately zero, false otherwise
     */
    public static boolean isEffectivelyZero(final double value) {
        return Math.abs(value) < 1e-10;
    }
}
