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
     * Count number of all possible permutations with repetitions of array indices,
     * for a cardinality given.
     * <p>
     *     Each index is repeated the prescribed number of times, individually.
     * </p>
     * <p>
     *     The given cardinality is the argument array length.
     * </p>
     *
     * @param repetitions an array that maps index on the prescribed number of repetitions.
     * @return the number of all possible permutations with individually prescribed repetitions, per element
     */
    public static int countPermutationsWithRepetitions(final int[] repetitions) {
        // TODO maybe add a validation to avoid INT overflow
        int nominator = Arrays.stream(repetitions).sum();
        int denominator = (int) Arrays.stream(repetitions)
                .mapToLong(CombinatoricsCalc::factorial)
                .reduce(0L, (a, b) -> a*b);
        return nominator / denominator;
    }

    /**
     * Count number of all possible permutations of array indices,
     * no repetitions of elements allowed, for a given cardinality.
     *
     * @param cardinality the number of distinct sequential indices, starting with 0
     * @return the number of all possible permutations, without repetitions
     */
    public static int countPermutationsNoRepetitions(final int cardinality) {
        return (int) CombinatoricsCalc.factorial(cardinality);
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
    public static void print(final CombinationGenerator generator, final OutputStream out) {
        printf(generator, out, Arrays::toString);
    }

    public static void printJavaCode(final CombinationGenerator generator, final OutputStream out) {
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
    public static void printf(final CombinationGenerator generator, final OutputStream out, final Function<int[], String> format) {
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
