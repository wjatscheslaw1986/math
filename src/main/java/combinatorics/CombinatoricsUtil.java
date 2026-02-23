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
     * Print generated combinations to an OutputStream implementation.
     * <p>
     *     The formatting is the default <i>Arrays::toString</i>.
     * </p>
     *
     * @param generator combinations generator;
     * @param out  an implementation of OutputStream
     */
    public static void print(final IndexSequenceGenerator generator, final OutputStream out) {
        printf(generator, out, Arrays::toString);
    }

    public static void printJavaCode(final IndexSequenceGenerator generator, final OutputStream out) {
        printf(generator, out, arr -> Arrays.toString(arr).replace("[", "{").replace("]", "}"));
    }

    /**
     * Print generated combinations to an OutputStream implementation.
     * <p>
     *     The formatting is set with the <i>format</i> function argument.
     * </p>
     *
     * @param generator the combinations' generator;
     * @param out    the implementation of OutputStream
     * @param format the formatter
     */
    public static void printf(final IndexSequenceGenerator generator, final OutputStream out, final Function<int[], String> format) {
        while (generator.hasNext()) {
            getPrintIntArrayFunction(out, format).accept(generator.next());
        }
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

    public static int[] getArrayOfIndicesForSize(final int size) {
        final int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }
}
