package combinatorics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A utility class for {@link combinatorics} package.
 *
 * @author Viacheslav Mikhailov
 */
public final class CombinatoricsUtil {

    private CombinatoricsUtil() {
        // static context only
    }

    /**
     * A function to print an integer array to {@link OutputStream}.
     *
     * @param o the output stream
     * @return the printing function
     */
    public static Consumer<int[]> getPrintIntArrayFunction(final OutputStream o, final Function<int[], String> format) {
        return (final int[] currentCombination) -> {
            try {
                o.write(format.apply(currentCombination).getBytes());
                o.write(("," + System.lineSeparator()).getBytes());
            } catch (IOException e) {
                System.err.print(e.getLocalizedMessage());
            }
        };
    }

    /**
     * A function to print an object array to {@link OutputStream}.
     *
     * @param o the output stream
     * @param <T> object type
     * @return the printing function
     */
    public static <T> Consumer<T[]> getPrintArrayFunction(final OutputStream o) {
        return (final T[] currentCombination) -> {
            try {
                o.write(Arrays.toString(currentCombination).getBytes());
                o.write(System.lineSeparator().getBytes());
            } catch (IOException e) {
                System.err.print(e.getLocalizedMessage());
            }
        };
    }

    /**
     * Cuts off all the elements from the given array starting with the
     * given <i>index</i>, inclusive.
     *
     * @param array given
     * @param index the index since which we throw away elements from the given array
     * @return the shortened array
     */
    public static int[] cutFrom(final int[] array, final int index) {
        final int[] subArray = new int[index];
        System.arraycopy(array, 0, subArray, 0, index);
        return subArray;
    }
}
